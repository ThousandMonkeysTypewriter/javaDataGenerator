/*
 * Copyright (C) 2012 Facebook, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.dsl.formats.redirect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.dsl.DSL;
import com.dsl.executor.Executor;
import com.dsl.executor.info.ExecData;

import com.google.gson.Gson;

public class RedirectDSL extends DSL {

  HashMap<Integer, String> qoh = new HashMap<Integer, String>();

  public RedirectDSL(ArrayList<String> inputs, ArrayList<Integer> outputs,
      Map<String, Collection<RedirectData>> redirects) {
    super("");
    int count = 0;
    for (String q : inputs) {
      if (!q.isEmpty()) {
        qoh.put(count, q);
        count ++;
      }
    }

    for (Entry<Integer, String> q : qoh.entrySet()) {
      ExecData d = new ExecData();
      
      d.toEnvironment("value", q.getValue(), false);
      d.toEnvironment("redirects", redirects.toString(), false);
      d.toEnvironment("url_api", "http://yenisei.detectum.com:1919/search_default?region_id=1&q=", false);
//    d.toEnvironment("answer", outputs.get(q.getKey()), true);
      d.toEnvironment("answer", "1", true);
      d.toEnvironment("output", "0", true);
      d.toEnvironment("terminate", "false", true);

      d.toArgument("id", q.getKey());
      d.toArgument("query", q.getValue());
      
      d.toProgram("id", Executor.BEGIN);
      d.toProgram("program", "begin");
      
      data.add(d); 
    }
  }

  public void check_redirects() throws Exception {
    for (ExecData d : data) {
      //System.err.println("%%%");
    //  exec.query_api(d);
    //  exec.parse_response_type(d);
      exec.is_redirect(d);
      exec.validate(d);
      
      d.flush_buffer(out);
      d.clear();
    }
//    send(null);
  }
}
