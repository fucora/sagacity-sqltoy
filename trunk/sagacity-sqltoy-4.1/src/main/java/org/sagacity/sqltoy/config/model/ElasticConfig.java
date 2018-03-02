/**
 * 
 */
package org.sagacity.sqltoy.config.model;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.sagacity.sqltoy.utils.StringUtil;

/**
 * @project sagacity-sqltoy4.0
 * @description es配置
 * @author chenrenfei <a href="mailto:zhongxuchen@gmail.com">联系作者</a>
 * @version id:ElasticConfig.java,Revision:v1.0,Date:2018年2月5日
 */
public class ElasticConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7850474153384016421L;

	private RestClient restClient;

	public ElasticConfig(String url) {
		this.url = url;
	}

	/**
	 * 请求路径(默认为根路径)
	 */
	private String path = "/";

	/**
	 * 配置名称
	 */
	private String id;

	/**
	 * url地址
	 */
	private String url;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 证书文件
	 */
	private String keyStore;

	/**
	 * 编码格式
	 */
	private String charset = "UTF-8";

	/**
	 * 请求超时30秒
	 */
	private int requestTimeout = 30000;

	/**
	 * 连接超时10秒
	 */
	private int connectTimeout = 10000;

	/**
	 * 整个连接超时时长,默认3分钟
	 */
	private int socketTimeout = 180000;

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the keyStore
	 */
	public String getKeyStore() {
		return keyStore;
	}

	/**
	 * @param keyStore
	 *            the keyStore to set
	 */
	public void setKeyStore(String keyStore) {
		this.keyStore = keyStore;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the charset
	 */
	public String getCharset() {
		return charset;
	}

	/**
	 * @param charset
	 *            the charset to set
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}

	/**
	 * @return the requestTimeout
	 */
	public int getRequestTimeout() {
		return requestTimeout;
	}

	/**
	 * @param requestTimeout
	 *            the requestTimeout to set
	 */
	public void setRequestTimeout(int requestTimeout) {
		this.requestTimeout = requestTimeout;
	}

	/**
	 * @return the connectTimeout
	 */
	public int getConnectTimeout() {
		return connectTimeout;
	}

	/**
	 * @param connectTimeout
	 *            the connectTimeout to set
	 */
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	/**
	 * @return the socketTimeout
	 */
	public int getSocketTimeout() {
		return socketTimeout;
	}

	/**
	 * @param socketTimeout
	 *            the socketTimeout to set
	 */
	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	/**
	 * @return the restClient
	 */
	public RestClient getRestClient() {
		return restClient;
	}

	/**
	 * @param restClient
	 *            the restClient to set
	 */
	public void initRestClient() {
		if (StringUtil.isBlank(this.getUrl()))
			return;
		if (restClient == null) {
			String splitSign = ";";
			if (this.getUrl().indexOf(";") == -1)
				splitSign = ",";
			String[] urls = this.getUrl().split(splitSign);
			// 当为单一地址时使用httpclient直接调用
			if (urls.length < 2)
				return;
			List<HttpHost> hosts = new ArrayList<HttpHost>();
			for (String urlStr : urls) {
				try {
					if (StringUtil.isNotBlank(urlStr)) {
						URL url = new java.net.URL(urlStr.trim());
						hosts.add(new HttpHost(url.getHost(), url.getPort(), url.getProtocol()));
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
			if (!hosts.isEmpty()) {
				RestClientBuilder builder = RestClient.builder((HttpHost[]) hosts.toArray());
				restClient = builder.build();
			}
		}
	}

	public static void main(String args[]) {
		String urlStr = "http://192.168.56.1:9200";
		try {
			URL url = new java.net.URL(urlStr);
			System.err.println("protocol=" + url.getProtocol());
			System.err.println("host=" + url.getHost());
			System.err.println("port=" + url.getPort());
			System.err.println("path=" + url.getPath());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}
