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

package org.apache.olingo.fit.proxy.v3.primitivekeys.microsoft.test.odata.services.primitivekeysservice;

import org.apache.olingo.ext.proxy.api.AbstractEntitySet;



@org.apache.olingo.ext.proxy.api.annotations.EntitySet(name = "EdmTimeSet")
public interface EdmTimeSet 
  extends org.apache.olingo.ext.proxy.api.EntitySetQuery<org.apache.olingo.fit.proxy.v3.primitivekeys.microsoft.test.odata.services.primitivekeysservice.types.EdmTime, org.apache.olingo.fit.proxy.v3.primitivekeys.microsoft.test.odata.services.primitivekeysservice.types.EdmTimeCollection, EdmTimeSet>, AbstractEntitySet<org.apache.olingo.fit.proxy.v3.primitivekeys.microsoft.test.odata.services.primitivekeysservice.types.EdmTime, java.math.BigDecimal, org.apache.olingo.fit.proxy.v3.primitivekeys.microsoft.test.odata.services.primitivekeysservice.types.EdmTimeCollection> {

    org.apache.olingo.fit.proxy.v3.primitivekeys.microsoft.test.odata.services.primitivekeysservice.types.EdmTime newEdmTime();
    org.apache.olingo.fit.proxy.v3.primitivekeys.microsoft.test.odata.services.primitivekeysservice.types.EdmTimeCollection newEdmTimeCollection();
}
