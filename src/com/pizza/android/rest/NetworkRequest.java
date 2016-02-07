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