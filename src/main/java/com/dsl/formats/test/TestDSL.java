package com.dsl.formats.test;

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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.dsl.executor.info.ExecData;

import com.dsl.DSL;


public class TestDSL extends DSL {

  private ExecData data;
  private ArrayList<String> args = new ArrayList<String>();
  private int label;
  private static volatile TestDSL instance;

  public static TestDSL getInstance() {
    return instance;
  }

  public TestDSL(String filename) {
    super(filename);
    instance = this;
    data = new ExecData();
    label = 0;

    //    for (EventAtMinute e : inputs) {
    //      ExecData d = new ExecData();
    //
    //      d.toEnvironment("answer", 2, true);
    //      d.toEnvironment("date1", 0, true);
    //      d.toEnvironment("date2", 0, true);
    //      d.toEnvironment("date1_diff", 0, true);
    //      d.toEnvironment("date2_diff", 0, true);
    //      d.toEnvironment("output", 0, true);
    //      d.toEnvironment("terminate", false, true);
    //      d.toEnvironment("client_id", client_id, true);
    //      d.toEnvironment("client", client, false);
    //      d.toEnvironment("log", inputs, false);
    //    
    //      d.toProgram("id", Executor.BEGIN);
    //      d.toProgram("program", "begin");
    //      
    //      d.toArgument("id", e.getOut(command));
    //      d.toArgument("event", e);
    //    
    //      if (command.equals("query")) 
    //        d.toEnvironment("anomaly_type", QUERY, false);
    //      else if (command.equals("status")) 
    //        d.toEnvironment("anomaly_type", STATUS, false);
    //      else if (command.equals("sla")) 
    //        d.toEnvironment("anomaly_type", SLA, false);
    //      
    //      data.add(d); 
    //    }
  }

  public void execute(String command, Integer id, String arg, HashMap<String, String> envs) {
    if (arg != null && !arg.isEmpty())
      args.add(arg);
    execute(command, id, envs);
  }

  public void execute(String command, Integer id, HashMap<String, String> envs) {
    data.toProgram("id", id+"");
    data.toProgram("program", "asses"+command);
    mess(envs+" - "+args+" - "+command);

    if (command.toLowerCase().equals("terminate")) {
      data.toEnvironment("terminate", "true", true);
      data.flush_buffer(out);
      data.clear();
    } else {
      int count = 0;
      for ( String arg : args) {
        count ++;
        data.toArgument("id"+count, arg);
      }

      if (envs != null)
        for ( Entry<String, String> env : envs.entrySet() )
          data.toEnvironment(env.getKey(), env.getValue(), true);
    }
    data.next_step();
  }

  public static HashMap<String, String> serializeObject(Class<?> c, Object obj) {
    Field[] fields = c.getDeclaredFields();
    HashMap<String, String> out = new HashMap<String, String>();
    try {
      for (Field f : fields) {
        f.setAccessible(true);
        System.err.println(f+" "+f.getName()+" "+f.get(obj));
        if (f.get(obj) != null) {
          String name = f.getName();
          String value = f.get(obj).toString();
          out.put(name, value);
        }
      }
      out.put("string", obj.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return out;
  }
}


