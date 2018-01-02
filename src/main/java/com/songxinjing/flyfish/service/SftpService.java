package com.songxinjing.flyfish.service;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.songxinjing.flyfish.domain.Goods;
import com.songxinjing.flyfish.domain.GoodsImg;
import com.songxinjing.flyfish.service.base.BaseService;
import com.songxinjing.flyfish.util.ReflectionUtil;

/**
 * 配置信息服务类
 * 
 * @author songxinjing
 * 
 */
@Service
public class SftpService extends BaseService<Goods, String> {

	@Autowired
	private GoodsImgService goodsImgService;

	@Autowired
	private GoodsService goodsService;

	private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);

	private static StandardFileSystemManager manager = new StandardFileSystemManager();
	private static FileSystemOptions opts = new FileSystemOptions();

	private static String serverAddress = "47.91.248.241";
	private static String userId = "root";
	private static String password = "Sxj!2345";
	private static String remoteDirectory = "/flyfish/images";
	private static String userInfo = userId + ":" + password;

	static {
		// Initializes the file manager
		try {
			manager.init();
			// Setup our SFTP configuration
			SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(opts, "no");
			SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(opts, true);
			SftpFileSystemConfigBuilder.getInstance().setTimeout(opts, 10000);
		} catch (FileSystemException e) {
			logger.error("图片上传初始化失败！", e);
		}
	}

	private static LinkedBlockingQueue<SftpJob> sftpJobs = new LinkedBlockingQueue<SftpJob>();

	private static class SftpJob {

		private String sku;
		private String name;
		private String url;

		private SftpJob(String sku, String name, String url) {
			this.sku = sku;
			this.name = name;
			this.url = url;
		}
	}

	// 0: 未启动；1: 正在启动； 2: 已启动
	private static boolean isInit = false;

	private void init() {
		Runnable sftper = new Runnable() {
			public void run() {
				while (true) {
					try {
						SftpJob job = sftpJobs.take();
						doFTP(job);
					} catch (InterruptedException e) {
						logger.error("图片上传任务终止！", e);
					}
				}
			}
		};
		fixedThreadPool.submit(sftper);
		isInit = true;
	}

	public void startFTP(String sku, String name, String url) {
		if (!isInit) {
			logger.info("图片上传初始化……");
			init();
		}
		if (url.contains("?")) {
			url = url.substring(0, url.indexOf("?"));
		}
		SftpJob sftpJob = new SftpJob(sku, name, url);
		try {
			sftpJobs.put(sftpJob);
		} catch (InterruptedException e) {
			logger.error("图片上传任务终止！", e);
		}
	}

	public void doFTP(SftpJob sftpJob) {
		logger.info("待上传图片任务数量：" + sftpJobs.size());
		String tempSku = sftpJob.sku;
		if (sftpJob.name.equals("mainImgUrl")) {
			Goods goods = goodsService.find(sftpJob.sku);
			tempSku = goods.getParentSku();
		}
		if (tempSku.contains("*")) {
			tempSku = tempSku.replace("*", "_");
		}
		try {
			logger.info("批量导入上传图片：" + sftpJob.sku + " " + sftpJob.name + " " + sftpJob.url);
			URL fileUrl = new URL(sftpJob.url);
			String path = remoteDirectory + "/" + tempSku + "-" + sftpJob.name + ".jpg";
			URI sftpUri = new URI("sftp", userInfo, serverAddress, -1, path, null, null);
			FileObject remoteFile = manager.resolveFile(sftpUri.toString(), opts);
			// Create local file object
			FileObject localFile = manager.resolveFile(fileUrl);
			// Copy local file to sftp server
			remoteFile.copyFrom(localFile, Selectors.SELECT_SELF);
			GoodsImg img = goodsImgService.find(sftpJob.sku);
			ReflectionUtil.setFieldValue(img, sftpJob.name, tempSku + "-" + sftpJob.name + ".jpg");
			goodsImgService.update(img);
		} catch (MalformedURLException | FileSystemException | URISyntaxException e) {
			logger.error("图片上传失败！" + sftpJob.sku + " " + sftpJob.name + " " + sftpJob.url, e);
			return;
		}
	}

	public void doFTP(String name, File image) {
		logger.info("人工上传图片：" + name);
		try {
			String path = remoteDirectory + "/" + name;
			URI sftpUri = new URI("sftp", userInfo, serverAddress, -1, path, null, null);
			FileObject remoteFile = manager.resolveFile(sftpUri.toString(), opts);
			// Create local file object
			FileObject localFile = manager.toFileObject(image);
			// Copy local file to sftp server
			remoteFile.copyFrom(localFile, Selectors.SELECT_SELF);
		} catch (FileSystemException | URISyntaxException e) {
			logger.error("图片上传失败！" + name, e);
			return;
		}

	}

}
