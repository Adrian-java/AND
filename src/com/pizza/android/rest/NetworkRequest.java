package com.pizza.android.rest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class NetworkRequest {

	private final String url;
	private final HttpMethod method;
	private final String body;

	public NetworkRequest(String url, HttpMethod method, String body) {
		this.url = url;
		this.method = method;
		this.body = body;
	}

	public NetworkRequest(String url, HttpMethod method) {
		this(url, method, null);
	}

	public String execute() throws IOException {
		InputStream is = null;

		try {
			URL url = new URL(this.url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10000 /* milliseconds */);
			conn.setConnectTimeout(15000 /* milliseconds */);
			conn.setRequestMethod(method.getMethod());
			conn.setDoInput(true);

			if (body != null) {
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setRequestProperty("Accept", "application/json");
				conn.setDoOutput(true);
				conn.getOutputStream().write(body.getBytes());
			}

			conn.connect();
			is = conn.getInputStream();

			return readStream(is);
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

	public String login(Map<String, String> data) throws IOException {
		URL siteUrl = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) siteUrl.openConnection();
		conn.setRequestMethod(method.getMethod());
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setReadTimeout(10000 /* milliseconds */);
		conn.setConnectTimeout(150000 /* milliseconds */);
		conn.setUseCaches(true);
		conn.setDoOutput(true);
		conn.setDoInput(true);

		DataOutputStream out = new DataOutputStream(conn.getOutputStream());

		Set keys = data.keySet();
		Iterator keyIter = keys.iterator();
		String content = "";
		for (int i = 0; keyIter.hasNext(); i++) {
			Object key = keyIter.next();
			if (i != 0) {
				content += "&";
			}
			content += key + "=" + URLEncoder.encode(data.get(key), "UTF-8");
		}
		out.writeBytes(content);
		out.flush();
		out.close();
		return readStream(conn.getInputStream());
	}

	public String readStream(InputStream stream) throws IOException {
		BufferedReader r = new BufferedReader(new InputStreamReader(stream));
		StringBuilder total = new StringBuilder();
		String line;
		while ((line = r.readLine()) != null) {
			total.append(line);
		}
		return total.toString();
	}
}