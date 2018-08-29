/**
 * @author liuifengyi
 *  下午2:42:42
 * @version 1.0
 * 文件描述
 */
package com.jd.jr.sd;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
  * @author liuifengyi
 *  下午2:42:42
 * @version 1.0
 * 类描述
 *  
 */
public class MockHttpURLConnection extends HttpURLConnection {

	private Logger logger = Logger.getLogger(this.getClass().getName());

	private String content;

	private Map<String, List<String>> headers = new HashMap<String, List<String>>();

	/**
	 * @param url
	 */
	public MockHttpURLConnection(URL url, String content) {
		super(url);
		this.content = content;
		if (content != null) {
			this.addHeader("content-length", content.length() + "");
		}
	}

	public void setResponseCode(String code) {
		if (code != null) {
			try {
				int code_ = Integer.parseInt(code);
				this.responseCode = code_;
			} catch (Exception e) {
				logger.log(Level.SEVERE, "设置code异常", e);
			}
		}

	}

	public void setResponseMessage(String message) {
		this.responseMessage = message;
	}


	public void setContentType(String contentType) {
		this.addHeader("content-type", contentType);
	}

	public void addHeader(String key, String value) {
		List<String> values = headers.get(key);
		if (values == null) {
			values = new ArrayList<String>();
		}
		values.add(value);
		headers.put(key, values);
	}

	@Override
	public String getHeaderField(String name) {
		List<String> values = headers.get(name);
		if (values != null) {
			StringBuilder builder = new StringBuilder();
			Iterator<String> it = values.iterator();
			while (it.hasNext()) {
				builder.append(it.next() + ",");
			}
			return builder.substring(0, builder.length() - 1);
		}

		return null;
	}

	@Override
	public Map<String, List<String>> getHeaderFields() {
		return headers;
	}

	@Override
	public long getContentLengthLong() {
		return getHeaderFieldLong("content-length", -1);
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.URLConnection#connect()
	 */
	@Override
	public void connect() throws IOException {
		logger.info("MockURLConnection connected");
	}

	@Override
	public InputStream getInputStream() {
		return new ByteArrayInputStream(content.getBytes());
	}

	@Override
	public Object getContent() throws IOException {
		return "getContent" + content;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.HttpURLConnection#disconnect()
	 */
	@Override
	public void disconnect() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.HttpURLConnection#usingProxy()
	 */
	@Override
	public boolean usingProxy() {
		// TODO Auto-generated method stub
		return false;
	}

}
