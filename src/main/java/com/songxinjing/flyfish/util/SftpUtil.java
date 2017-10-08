package com.songxinjing.flyfish.util;

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

public class SftpUtil {

	public static void startFTP(String url, String name) {

		StandardFileSystemManager manager = new StandardFileSystemManager();

		try {
			URL fileUrl = new URL(url);

			String serverAddress = "47.91.248.241";
			String userId = "root";
			String password = "Sxj!2345";
			String remoteDirectory = "/flyfish/images";

			// Initializes the file manager
			manager.init();

			// Setup our SFTP configuration
			FileSystemOptions opts = new FileSystemOptions();
			SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(opts, "no");
			SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(opts, true);
			SftpFileSystemConfigBuilder.getInstance().setTimeout(opts, 10000);

			String userInfo = userId + ":" + password;
			String path = remoteDirectory + "/" + name;
			URI sftpUri = new URI("sftp", userInfo, serverAddress, -1, path, null, null);
			FileObject remoteFile = manager.resolveFile(sftpUri.toString(), opts);

			// Create local file object
			FileObject localFile = manager.resolveFile(fileUrl);

			// Copy local file to sftp server
			remoteFile.copyFrom(localFile, Selectors.SELECT_SELF);
		} catch (MalformedURLException | FileSystemException | URISyntaxException e) {
			e.printStackTrace();
		}

	}

}
