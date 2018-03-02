package com.ctoedu.common.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ftp工具类.
 *
 * Date:2016年11月4日 下午4:47:29
 * Version:1.0
 */
public class FtpUtils {
	private FTPClient ftpClient = new FTPClient();
	private Logger logger = LoggerFactory.getLogger(FtpUtils.class);

	public FtpUtils() {
		ftpClient.setConnectTimeout(20000); //设置连接超时为10秒钟
		ftpClient.addProtocolCommandListener(new PrintCommandListener(
				new PrintWriter(System.out)));
	}
	
	/**
	 * 得到当前ftp目录下的文件列表
	 * 
	 * @param tempDir
	 * @return
	 * @throws IOException
	 */
	public FTPFile[] listFiles(String tempDir){
		FTPFile[] ff = null;
		try {
			ff = ftpClient.listFiles(tempDir);
		} catch (IOException e) {
			return null;
		}
		return ff;
	}
	/**
	 * FTP 连接 登陆
	 * */
	public boolean connect(String hostname, int port, String username,
			String password) throws IOException {
		ftpClient.connect(hostname, port);
		logger.info("FTP 远程连接成功");
		if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
			if (ftpClient.login(username, password)) {
				logger.info("FTP 远程登陆成功");
				return true;
			}
		}
		logger.info("FTP 远程连接成功");
		return false;
	}

	public boolean download(String remote, OutputStream out) {
		logger.info("FTP 远程连接，文件开始下载... ...");
		ftpClient.enterLocalPassiveMode();
		boolean result = false;
		try {
			result = ftpClient.retrieveFile(remote, out);
			out.close();
			disconnect();
		} catch (IOException e) {
			result = false;
		}
		
		return result;
	}

	public void disconnect() throws IOException {
		if (ftpClient.isConnected()) {
			ftpClient.disconnect();
		}
	}
}
