package com.songxinjing.flyfish.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 配置信息服务类
 * 
 * @author songxinjing
 * 
 */
@Service
public class SftpUtil {

	protected static final Logger logger = LoggerFactory.getLogger(SftpUtil.class);

	private static StandardFileSystemManager manager = new StandardFileSystemManager();
	private static FileSystemOptions opts = new FileSystemOptions();

	private static String serverAddress = ConfigUtil.getValue("/config.properties", "imgServer");
	private static String userId = ConfigUtil.getValue("/config.properties", "imgUser");
	private static String password = ConfigUtil.getValue("/config.properties", "imgPwd");
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

	public static boolean doFTP(String name, String url) {
		try {
			// logger.info("上传图片：" + name + " " + url);
			URL fileUrl = new URL(url);
			String path = remoteDirectory + "/" + name;
			URI sftpUri = new URI("sftp", userInfo, serverAddress, -1, path, null, null);
			FileObject remoteFile = manager.resolveFile(sftpUri.toString(), opts);
			// Create local file object
			FileObject localFile = manager.resolveFile(fileUrl);
			// Copy local file to sftp server
			remoteFile.copyFrom(localFile, Selectors.SELECT_SELF);
			return true;
		} catch (MalformedURLException | FileSystemException | URISyntaxException e) {
			logger.error("图片自动上传失败！" + name + " " + url);
			return false;
		}
	}

	public static boolean doFTP(String name, File image) {
		logger.info("人工上传图片：" + name);
		try {
			String path = remoteDirectory + "/" + name;
			URI sftpUri = new URI("sftp", userInfo, serverAddress, -1, path, null, null);
			FileObject remoteFile = manager.resolveFile(sftpUri.toString(), opts);
			// Create local file object
			FileObject localFile = manager.toFileObject(image);
			// Copy local file to sftp server
			remoteFile.copyFrom(localFile, Selectors.SELECT_SELF);
			return true;
		} catch (FileSystemException | URISyntaxException e) {
			logger.error("图片人工上传失败！" + name);
			return false;
		}

	}

}
