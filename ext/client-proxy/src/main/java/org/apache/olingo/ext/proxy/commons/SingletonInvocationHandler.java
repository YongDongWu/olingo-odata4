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
package org.apache.olingo.ext.proxy.commons;

import org.apache.olingo.ext.proxy.api.AbstractEntityCollection;
import org.apache.olingo.ext.proxy.api.AbstractSingleton;

import java.io.Serializable;
import java.lang.reflect.Method;

public class SingletonInvocationHandler<
        T extends Serializable, KEY extends Serializable, EC extends AbstractEntityCollection<T>>
        extends AbstractInvocationHandler
        implements AbstractSingleton<T, KEY, EC> {

  private static final long serialVersionUID = 2450269053734776228L;

  @SuppressWarnings({"rawtypes", "unchecked"})
  static SingletonInvocationHandler getInstance(
          final Class<?> ref, final EntityContainerInvocationHandler containerHandler, final String singletonName) {

    return new SingletonInvocationHandler(ref, containerHandler, singletonName);
  }
  private final EntitySetInvocationHandler<?, ?, ?> entitySetHandler;

  @SuppressWarnings({"rawtypes", "unchecked"})
  private SingletonInvocationHandler(
          final Class<?> ref, final EntityContainerInvocationHandler containerHandler, final String singletonName) {

    super(containerHandler);
    this.entitySetHandler = EntitySetInvocationHandler.getInstance(ref, containerHandler, singletonName);
  }

  @Override
  public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
    if (isSelfMethod(method, args)) {
      return invokeSelfMethod(method, args);
    } else {
      throw new NoSuchMethodException(method.getName());
    }
  }

  @SuppressWarnings("unchecked")
  public T load() {
    return (T) this.entitySetHandler.execute().iterator().next();
  }
}
