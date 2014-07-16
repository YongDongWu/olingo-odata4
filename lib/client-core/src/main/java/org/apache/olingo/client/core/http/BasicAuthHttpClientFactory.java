/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.olingo.client.core.http;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.olingo.client.api.http.HttpMethod;

import java.net.URI;

/**
 * Implementation for working with Basic Authentication.
 */
public class BasicAuthHttpClientFactory extends DefaultHttpClientFactory {

  private static final long serialVersionUID = 7985626503125490244L;

  private final String username;

  private final String password;

  public BasicAuthHttpClientFactory(final String username, final String password) {
    this.username = username;
    this.password = password;
  }

  @Override
  public DefaultHttpClient create(final HttpMethod method, final URI uri) {
    final DefaultHttpClient httpclient = super.create(method, uri);

    httpclient.getCredentialsProvider().setCredentials(
            new AuthScope(uri.getHost(), uri.getPort()),
            new UsernamePasswordCredentials(username, password));

    return httpclient;
  }
}
