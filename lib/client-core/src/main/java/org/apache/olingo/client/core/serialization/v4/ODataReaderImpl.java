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
package org.apache.olingo.client.core.serialization.v4;

import org.apache.olingo.client.api.serialization.v4.ODataReader;
import org.apache.olingo.client.api.v4.ODataClient;
import org.apache.olingo.client.core.serialization.AbstractODataReader;
import org.apache.olingo.commons.api.domain.v4.ODataEntity;
import org.apache.olingo.commons.api.domain.v4.ODataEntitySet;
import org.apache.olingo.commons.api.domain.v4.ODataProperty;
import org.apache.olingo.commons.api.format.ODataFormat;
import org.apache.olingo.commons.api.serialization.ODataDeserializerException;

import java.io.InputStream;

public class ODataReaderImpl extends AbstractODataReader implements ODataReader {

  public ODataReaderImpl(final ODataClient client) {
    super(client);
  }

  @Override
  public ODataEntitySet readEntitySet(final InputStream input, final ODataFormat format)
      throws ODataDeserializerException {
    return ((ODataClient) client).getBinder().getODataEntitySet(client.getDeserializer(format).toEntitySet(input));
  }

  @Override
  public ODataEntity readEntity(final InputStream input, final ODataFormat format)
      throws ODataDeserializerException {
    return ((ODataClient) client).getBinder().getODataEntity(client.getDeserializer(format).toEntity(input));
  }

  @Override
  public ODataProperty readProperty(final InputStream input, final ODataFormat format)
      throws ODataDeserializerException {
    return ((ODataClient) client).getBinder().getODataProperty(client.getDeserializer(format).toProperty(input));
  }
}
