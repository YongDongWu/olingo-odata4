/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.olingo.commons.core.edm.primitivetype;

import org.apache.olingo.commons.api.edm.EdmPrimitiveTypeException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EdmDuration extends SingletonPrimitiveType {

  private static final Pattern PATTERN = Pattern.compile(
      "[-+]?P(?:(\\p{Digit}+)D)?(?:T(?:(\\p{Digit}+)H)?(?:(\\p{Digit}+)M)?"
          + "(?:(\\p{Digit}+(?:\\.(?:\\p{Digit}+?)0*)?)S)?)?");

  private static final EdmDuration INSTANCE = new EdmDuration();

  {
    uriPrefix = "duration'";
    uriSuffix = "'";
  }

  public static EdmDuration getInstance() {
    return INSTANCE;
  }

  @Override
  public Class<?> getDefaultType() {
    return BigDecimal.class;
  }

  @Override
  protected <T> T internalValueOfString(final String value,
      final Boolean isNullable, final Integer maxLength, final Integer precision,
      final Integer scale, final Boolean isUnicode, final Class<T> returnType) throws EdmPrimitiveTypeException {

    final Matcher matcher = PATTERN.matcher(value);
    if (!matcher.matches()
        || matcher.group(1) == null && matcher.group(2) == null && matcher.group(3) == null
        && matcher.group(4) == null) {
      throw new EdmPrimitiveTypeException(
          "EdmPrimitiveTypeException.LITERAL_ILLEGAL_CONTENT.addContent(literal)");
    }

    BigDecimal result = (matcher.group(1) == null ? BigDecimal.ZERO
        : new BigDecimal(matcher.group(1)).multiply(BigDecimal.valueOf(24 * 60 * 60))).
        add(matcher.group(2) == null ? BigDecimal.ZERO
            : new BigDecimal(matcher.group(2)).multiply(BigDecimal.valueOf(60 * 60))).
        add(matcher.group(3) == null ? BigDecimal.ZERO
            : new BigDecimal(matcher.group(3)).multiply(BigDecimal.valueOf(60))).
        add(matcher.group(4) == null ? BigDecimal.ZERO : new BigDecimal(matcher.group(4)));

    if (result.scale() <= (precision == null ? 0 : precision)) {
      result = value.charAt(0) == '-' ? result.negate() : result;
    } else {
      throw new EdmPrimitiveTypeException(
          "EdmPrimitiveTypeException.LITERAL_FACETS_NOT_MATCHED.addContent(literal, facets)");
    }

    try {
      return EdmDecimal.convertDecimal(result, returnType);
    } catch (final IllegalArgumentException e) {
      throw new EdmPrimitiveTypeException(
          "EdmPrimitiveTypeException.LITERAL_UNCONVERTIBLE_TO_VALUE_TYPE.addContent(value, returnType), e");
    } catch (final ClassCastException e) {
      throw new EdmPrimitiveTypeException(
          "EdmPrimitiveTypeException.VALUE_TYPE_NOT_SUPPORTED.addContent(returnType), e");
    }
  }

  @Override
  protected <T> String internalValueToString(final T value,
      final Boolean isNullable, final Integer maxLength, final Integer precision,
      final Integer scale, final Boolean isUnicode) throws EdmPrimitiveTypeException {

    BigDecimal valueDecimal;
    if (value instanceof BigDecimal) {
      valueDecimal = (BigDecimal) value;
    } else if (value instanceof Byte || value instanceof Short || value instanceof Integer || value instanceof Long) {
      valueDecimal = BigDecimal.valueOf(((Number) value).longValue());
    } else if (value instanceof BigInteger) {
      valueDecimal = new BigDecimal((BigInteger) value);
    } else {
      throw new EdmPrimitiveTypeException(
          "EdmPrimitiveTypeException.VALUE_TYPE_NOT_SUPPORTED.addContent(value.getClass())");
    }

    if (valueDecimal.scale() > (precision == null ? 0 : precision)) {
      throw new EdmPrimitiveTypeException(
          "EdmPrimitiveTypeException.VALUE_FACETS_NOT_MATCHED.addContent(value, facets)");
    }

    final StringBuilder result = new StringBuilder();
    if (valueDecimal.signum() == -1) {
      result.append('-');
      valueDecimal = valueDecimal.negate();
    }
    result.append('P');
    BigInteger seconds = valueDecimal.toBigInteger();
    final BigInteger days = seconds.divide(BigInteger.valueOf(24 * 60 * 60));
    if (!days.equals(BigInteger.ZERO)) {
      result.append(days.toString());
      result.append('D');
    }
    result.append('T');
    seconds = seconds.subtract(days.multiply(BigInteger.valueOf(24 * 60 * 60)));
    final BigInteger hours = seconds.divide(BigInteger.valueOf(60 * 60));
    if (!hours.equals(BigInteger.ZERO)) {
      result.append(hours.toString());
      result.append('H');
    }
    seconds = seconds.subtract(hours.multiply(BigInteger.valueOf(60 * 60)));
    final BigInteger minutes = seconds.divide(BigInteger.valueOf(60));
    if (!minutes.equals(BigInteger.ZERO)) {
      result.append(minutes.toString());
      result.append('M');
    }
    result.append(valueDecimal.remainder(BigDecimal.valueOf(60)).toPlainString());
    result.append('S');

    return result.toString();
  }
}
