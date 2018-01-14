package com.songxinjing.flyfish.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HTTP 代理请求类
 * 
 */
public class HttpUtil {

	protected static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

	/**
	 * 请求超时时间设置
	 */
	private static final int TIMEOUT = 30000;
	public static final String ENCODING = "UTF-8";

	/**
	 * 代理GET请求
	 * 
	 * @param address
	 * @param headerParameters
	 * @param body
	 * @return
	 * @throws Exception
	 */
	public static String get(String url) {
		return proxyHttpRequest(url, "GET", null, "");
	}

	/**
	 * 代理POST请求
	 * 
	 * @param address
	 * @param headerParameters
	 * @param body
	 * @return
	 * @throws Exception
	 */
	public static String post(String url, String body) {
		return proxyHttpRequest(url, "POST", null, body);
	}

	/**
	 * 代理HTTP请求
	 * 
	 * @param address
	 *            地址
	 * @param method
	 *            方法
	 * @param headerParameters
	 *            头信息
	 * @param body
	 *            请求内容
	 * @return
	 * @throws Exception
	 */
	public static String proxyHttpRequest(String url, String method, Map<String, String> headerParas, String body) {
		String result = null;
		HttpURLConnection httpConnection = null;

		logger.info("HTTP request: address: " + url + ", body: " + body);
		try {
			httpConnection = createConnection(url, method, headerParas, body);

			String encoding = ENCODING;
			if (httpConnection.getContentType() != null && httpConnection.getContentType().indexOf("charset=") >= 0) {
				encoding = httpConnection.getContentType()
						.substring(httpConnection.getContentType().indexOf("charset=") + 8);
			}

			// 读取请求响应信息
			result = inputStream2String(httpConnection.getInputStream(), encoding);
			logger.info("result: " + result);
		} catch (Exception e) {
			logger.error("系统错误", e);
		} finally {
			if (httpConnection != null) {
				httpConnection.disconnect();
			}
		}
		return result;
	}

	/**
	 * 创建HTTP连接
	 * 
	 * @param url
	 *            地址
	 * @param method
	 *            方法
	 * @param headerParameters
	 *            头信息
	 * @param body
	 *            请求内容
	 * @return
	 * @throws Exception
	 */
	private static HttpURLConnection createConnection(String url, String method, Map<String, String> headerParas,
			String body) throws Exception {
		URL Url = new URL(validateUrl(url));

		// 跳过HTTPS 证书验证
		trustAllHttpsCertificates();
		HttpURLConnection httpConnection = (HttpURLConnection) Url.openConnection();
		// 设置请求时间
		httpConnection.setReadTimeout(TIMEOUT);
		httpConnection.setConnectTimeout(TIMEOUT);

		// 设置 header
		if (headerParas != null) {
			for (Map.Entry<String, String> entry : headerParas.entrySet()) {
				httpConnection.setRequestProperty(entry.getKey(), entry.getValue());
			}
		}

		// 请求方式使用a=xx&b=xx传参
		httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + ENCODING);

		// 设置请求方法
		httpConnection.setRequestMethod(method);
		httpConnection.setDoOutput(true);
		httpConnection.setDoInput(true);
		// 写query数据流
		if (!(body == null || body.trim().equals(""))) {
			OutputStream writer = httpConnection.getOutputStream();
			try {
				writer.write(body.getBytes(ENCODING));
			} finally {
				if (writer != null) {
					writer.flush();
					writer.close();
				}
			}
		}

		// 请求结果
		int responseCode = httpConnection.getResponseCode();
		if (responseCode != 200) {
			throw new Exception(responseCode + ":" + inputStream2String(httpConnection.getErrorStream(), ENCODING));
		}

		return httpConnection;
	}

	/**
	 * 读取inputStream 到 string
	 * 
	 * @param input
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	private static String inputStream2String(InputStream input, String encoding) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(input, encoding));
		StringBuilder result = new StringBuilder();

		String temp = null;
		while ((temp = reader.readLine()) != null) {
			result.append(temp);
		}

		return result.toString();
	}

	/**
	 * 将参数化为 body
	 * 
	 * @param params
	 * @return
	 */
	public static String getRequestBody(Map<String, String> params) {
		StringBuilder body = new StringBuilder();

		if (params != null) {
			for (Map.Entry<String, String> param : params.entrySet()) {
				try {
					body.append(param.getKey() + "=" + URLEncoder.encode(param.getValue(), ENCODING) + "&");
				} catch (UnsupportedEncodingException e) {
					logger.error("", e);
				}
			}
		}

		if (body.length() > 0) {
			return body.substring(0, body.length() - 1);
		}
		return null;
	}

	/**
	 * 设置 https 请求
	 * 
	 * @throws Exception
	 */
	private static void trustAllHttpsCertificates() throws Exception {
		javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
		javax.net.ssl.TrustManager tm = new miTM();
		trustAllCerts[0] = tm;
		javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, null);
		javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	}

	/**
	 * 验证URL 链接是否有效，如正确则返回
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	private static String validateUrl(String url) throws Exception {
		if (!url.startsWith("http")) {

			throw new Exception("Url is not http or https");
		}

		return url;
	}

	/**
	 * 设置 https 请求证书
	 * 
	 */
	static class miTM implements javax.net.ssl.TrustManager, javax.net.ssl.X509TrustManager {
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public boolean isServerTrusted(java.security.cert.X509Certificate[] certs) {
			return true;
		}

		public boolean isClientTrusted(java.security.cert.X509Certificate[] certs) {
			return true;
		}

		public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType)
				throws java.security.cert.CertificateException {
			return;
		}

		public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType)
				throws java.security.cert.CertificateException {
			return;
		}
	}
}
