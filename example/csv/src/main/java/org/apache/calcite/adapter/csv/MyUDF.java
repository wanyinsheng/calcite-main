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

import org.apache.calcite.linq4j.function.Parameter;

/**
 * 编写UDF实体逻辑
 *
 * @Author wanys
 * @Date 2022/12/6 01:06
 * @Version 1.0
 **/
public class MyUDF {
  /**
   * 截取首字母
   *
   * @param str字符串
   * @return
   */
  public String subString(String str) {
    return str.substring(0, 1);
  }

  /**
   * 需要参数的写法
   * @param s
   * @param n
   * @return
   */
  public String subString2(
      @Parameter(name = "S") String s,
      @Parameter(name = "N", optional = true) Integer n) {
    return s.substring(0, n == null ? 1 : n);
  }
}
