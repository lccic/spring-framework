/*
 * Copyright 2002-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.http.client;

import java.io.IOException;
import java.net.URI;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.Request;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpMethod;
import org.springframework.util.Assert;

/**
 * {@link ClientHttpRequestFactory} implementation based on Jetty's {@link HttpClient}.
 *
 * @author Arjen Poutsma
 * @since 6.1
 * @see <a href="https://www.eclipse.org/jetty/documentation/jetty-11/programming-guide/index.html#pg-client-http">Jetty HttpClient</a>
 */
public class JettyClientHttpRequestFactory implements ClientHttpRequestFactory, InitializingBean, DisposableBean {

	private final HttpClient httpClient;

	private final boolean defaultClient;

	private int readTimeout = 1000;


	/**
	 * Default constructor that creates a new instance of {@link HttpClient}.
	 */
	public JettyClientHttpRequestFactory() {
		this(new HttpClient(), true);
	}

	/**
	 * Constructor that takes a customized {@code HttpClient} instance.
	 * @param httpClient the
	 */
	public JettyClientHttpRequestFactory(HttpClient httpClient) {
		this(httpClient, false);
	}

	private JettyClientHttpRequestFactory(HttpClient httpClient, boolean defaultClient) {
		this.httpClient = httpClient;
		this.defaultClient = defaultClient;
	}


	/**
	 * Set the underlying connect timeout in milliseconds.
	 * A value of 0 specifies an infinite timeout.
	 */
	public void setConnectTimeout(int connectTimeout) {
		Assert.isTrue(connectTimeout >= 0, "Timeout must be a non-negative value");
		this.httpClient.setConnectTimeout(connectTimeout);
	}

	/**
	 * Set the underlying read timeout in milliseconds.
	 */
	public void setReadTimeout(int readTimeout) {
		Assert.isTrue(readTimeout > 0, "Timeout must be a positive value");
		this.readTimeout = readTimeout;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		startHttpClient();
	}

	private void startHttpClient() throws Exception {
		if (!this.httpClient.isStarted()) {
			this.httpClient.start();
		}
	}

	@Override
	public void destroy() throws Exception {
		if (this.defaultClient) {
			if (!this.httpClient.isStopped()) {
				this.httpClient.stop();
			}
		}
	}

	@Override
	public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod) throws IOException {
		try {
			startHttpClient();
		}
		catch (Exception ex) {
			throw new IOException("Could not start HttpClient: " + ex.getMessage(), ex);
		}

		Request request = this.httpClient.newRequest(uri).method(httpMethod.name());
		return new JettyClientHttpRequest(request, this.readTimeout);
	}
}
