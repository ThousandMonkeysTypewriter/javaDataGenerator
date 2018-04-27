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
package com.dsl.executor.info;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Step {
  public HashMap<String, Element> supervised_env = new HashMap<String, Element>();
  public HashMap<String, Element> unsupervised_env = new HashMap<String, Element>();
  public HashMap<String, Element> program = new HashMap<String, Element>();
  public HashMap<String, Element> argument = new HashMap<String, Element>();
  
  public void fill_defaults(Step st) {
    copy_from(supervised_env, st.supervised_env);
    copy_from(program, st.program);
    copy_from(argument, st.argument);
  }

  private void copy_from(HashMap<String, Element> map1,
      HashMap<String, Element> map2) {
    for (Entry<String, Element> e : map2.entrySet()) 
      map1.put(e.getKey(), e.getValue());
  } 

  public String toString () {
    return "environment "+supervised_env+", program"+program+", argument"+argument;
  }

  public void clear() {
    for(Iterator<Entry<String, Element>> it = supervised_env.entrySet().iterator(); it.hasNext(); ) {
      Entry<String, Element> entry = it.next();
      if(!entry.getValue().isVisible()) {
        it.remove();
      }
    }
    
    for(Iterator<Entry<String, Element>> it = program.entrySet().iterator(); it.hasNext(); ) {
      Entry<String, Element> entry = it.next();
      if(!entry.getValue().isVisible()) {
        it.remove();
      }
    }
    
    for(Iterator<Entry<String, Element>> it = argument.entrySet().iterator(); it.hasNext(); ) {
      Entry<String, Element> entry = it.next();
      if(!entry.getValue().isVisible()) {
        it.remove();
      }
    }
  }
}
