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

	private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);

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
			e.printStackTrace();
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

	private static boolean isInit = false;

	private void init() {
		for (int index = 0; index < 3; index++) {
			Runnable sftper = new Runnable() {
				public void run() {
					while (true) {
						try {
							SftpJob job = sftpJobs.take();
							logger.debug("上传图片：" + job.name);
							doFTP(job);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			};
			fixedThreadPool.submit(sftper);
		}
	}

	public void startFTP(String sku, String name, String url) {
		if (!isInit) {
			init();
		}
		SftpJob sftpJob = new SftpJob(sku, name, url);
		try {
			sftpJobs.put(sftpJob);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void doFTP(SftpJob sftpJob) {
		String tempSku = sftpJob.sku;
		if (tempSku.contains("*")) {
			tempSku = tempSku.replace("*", "_");
		}
		try {
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
			e.printStackTrace();
			return;
		}
	}

	public void doFTP(String name, File image) {

		try {
			String path = remoteDirectory + "/" + name;
			URI sftpUri = new URI("sftp", userInfo, serverAddress, -1, path, null, null);
			FileObject remoteFile = manager.resolveFile(sftpUri.toString(), opts);
			// Create local file object
			FileObject localFile = manager.toFileObject(image);
			// Copy local file to sftp server
			remoteFile.copyFrom(localFile, Selectors.SELECT_SELF);
		} catch (FileSystemException | URISyntaxException e) {
			e.printStackTrace();
			return;
		}

	}

}
