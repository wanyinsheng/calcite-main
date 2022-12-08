/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.calcite.adapter.csv;


import org.apache.calcite.adapter.java.AbstractQueryableTable;
import org.apache.calcite.linq4j.BaseQueryable;
import org.apache.calcite.linq4j.Enumerator;
import org.apache.calcite.linq4j.QueryProvider;
import org.apache.calcite.linq4j.Queryable;
import org.apache.calcite.linq4j.tree.Types;

import org.apache.calcite.rel.type.RelDataType;

import org.apache.calcite.rel.type.RelDataTypeFactory;

import org.apache.calcite.schema.QueryableTable;
import org.apache.calcite.schema.SchemaPlus;

import org.checkerframework.checker.units.qual.A;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 自定义UDTF
 * @Author wanys
 * @Date 2022/12/6 01:20
 * @Version 1.0
 **/
public class MyUDTF {
  /**
   * 定义函数名称
   */
  public static
  final Method UDTF_METHOD = Types.lookupMethod(MyUDTF.class
      , "explode", String.class, String.class);

  /**
   * 具体的函数执行逻辑
   */
  public static QueryableTable explode(final String str, final String regex) {
    // 返回可迭代的表信息
    return new AbstractQueryableTable(String.class) {
      // 返回函数执行结果的每一列元数据信息
      @Override
      public RelDataType getRowType(RelDataTypeFactory typeFactory) {
        Arrays.asList(typeFactory.createJavaType(String.class));
        Arrays.asList("c");
        return typeFactory.createStructType(
            Arrays.asList(typeFactory.createJavaType(String.class)),
            Arrays.asList("c"));
      }

      // 将执行结果封装成一个迭代器并返回
      @Override
      public <T> Queryable<T> asQueryable(QueryProvider queryProvider, SchemaPlus schema, String tableName) {
        BaseQueryable<String> queryable = new BaseQueryable<String>(null, String.class, null) {
          @Override
          public Enumerator<String> enumerator() {
            return new Enumerator<String>() {
              int i = -1;
              String[] res = null;

              // 获取当前元素
              @Override
              public String current() {
                return res[i];
              }

              // 索引下移
              @Override
              public boolean moveNext() {
                if (i == -1) {
                  res = str.split(regex);
                }
                if (i < res.length - 1) {
                  i++;
                  return true;
                } else {
                  return false;
                }
              }

              @Override
              public void reset() {

              }

              @Override
              public void close() {

              }
            };
          }

        };
        return (Queryable<T>) queryable;
      }

    };
  }
}
