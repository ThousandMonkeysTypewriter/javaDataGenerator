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
package com.dsl.formats.detection_log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LogUtils {

  public static double countAtMinute(Event e, ArrayList<Event> ents, int ago, String c, double sla) {
    double count = 0.0;
    double green_zone = sla - ((sla/100) * 10);
    for (Event ent : ents) {
        if (e.year==ent.year && e.month==ent.month && e.day==ent.day && e.hour==ent.hour && ent.minute==(e.minute-ago)) {
          if (c.equals("query"))
            count += 1;
//          else if (c.equals("sla")) 
        }
    }
    return count;
  }
}
