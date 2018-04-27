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
package com.dsl.executor.subprocess;

import com.dsl.executor.Executor;
import com.dsl.executor.info.ExecData;

public class SubProcess {
  public static void inner_check(boolean condition, ExecData d) {
    if (((Integer)d.fromEnvironment("output").getValue()) == 0) {
      if (condition)
        d.toEnvironment("output", "2", true);
      else
        d.toEnvironment("output", "1", true);
    }
    
    d.toProgram("id", Executor.CHECK);
    d.toProgram("program", "check");
  }
}
