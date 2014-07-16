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
package org.apache.olingo.client.core.communication.request.invoke.v4;

import org.apache.olingo.client.api.communication.request.invoke.ODataInvokeRequest;
import org.apache.olingo.client.api.http.HttpMethod;
import org.apache.olingo.client.api.v4.EdmEnabledODataClient;
import org.apache.olingo.client.core.communication.request.invoke.AbstractEdmEnabledInvokeRequestFactory;
import org.apache.olingo.commons.api.domain.ODataInvokeResult;
import org.apache.olingo.commons.api.domain.ODataValue;

import java.net.URI;
import java.util.Map;

public class EdmEnabledInvokeRequestFactoryImpl extends AbstractEdmEnabledInvokeRequestFactory {

  private static final long serialVersionUID = 5854571629835831697L;

  private final EdmEnabledODataClient edmClient;

  public EdmEnabledInvokeRequestFactoryImpl(final EdmEnabledODataClient client) {
    super(client);
    this.edmClient = client;
  }

  @Override
  public <RES extends ODataInvokeResult> ODataInvokeRequest<RES> getInvokeRequest(
          final HttpMethod method, final URI uri, final Class<RES> resultRef,
          final Map<String, ODataValue> parameters) {

    final ODataInvokeRequest<RES> request = new ODataInvokeRequestImpl<RES>(edmClient, resultRef, method, uri);
    if (parameters != null) {
      request.setParameters(parameters);
    }

    return request;
  }
}
