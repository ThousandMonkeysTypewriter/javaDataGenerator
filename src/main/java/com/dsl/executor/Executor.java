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
package com.dsl.executor;

import com.google.gson.Gson;

import java.net.InetAddress;
import java.net.URLEncoder;
import java.util.ArrayList;

import com.dsl.data.Utils;
import com.dsl.executor.info.ExecData;
import com.dsl.executor.subprocess.SubProcess;
import com.dsl.formats.detection_log.Event;
import com.dsl.formats.detection_log.LogUtils;

public class Executor {

  //programs
  public static final String BEGIN        = "0";
  public static final String CALL_API     = "1";
  public static final String PARSE        = "2";
  public static final String DIFF         = "6";
  public static final String CHECK        = "3";
  public static final String ALARM        = "4";
  public static final String NO_ALARM     = "5";
  
  //traces
  public static final String ENVIRONMENT  = "1";
  public static final String PROGRAM      = "2";
  public static final String ARGUMENT     = "3";
  
  private SubProcess sub = new SubProcess();
  
  Gson gson = new Gson();
  
  int alarms = 0;
  int no_alarms = 0;
  
  public void query_api(ExecData d) throws Exception {
    d.next_step();
    String url = (String)d.fromEnvironment("url_api").getValue()+URLEncoder.encode((String)d.fromArgument("query").getValue(), "UTF-8");
    String json = Utils.readUrl(url);
    System.err.println(url+" - "+json.length());

    d.toEnvironment("api_response", json, false);
    d.toEnvironment("web", "1", true);
    
    d.toProgram("id", CALL_API);
    d.toProgram("program", "call_api");
  }

  public void parse_response_type(ExecData d) {
    d.next_step();
    String s = (String)d.fromEnvironment("api_response").getValue();
    
    s = s.substring(s.indexOf("\"type\":\"") + 8);
    s = s.substring(0, s.indexOf("\""));
    
    d.toEnvironment("api_response_type", s, false);
    d.toEnvironment("parse", "1", true);
    
    d.toProgram("id", PARSE);
    d.toProgram("program", "parse");
  }

  public void is_redirect(ExecData d) {
    d.next_step();

    sub.inner_check(((String)d.fromArgument("query").getValue()).contains("а"), d);
  }
  
  public void prepare_data(ExecData d) {
    // TODO Auto-generated method stub
    
  }
  
  public void compare_to_period (ExecData d) {
    d.next_step();

    double ago;
    double now = ((Integer)d.fromArgument("id").getValue())*1.0;

    if ((Integer)d.fromEnvironment("date1").getValue() == 0) {
        ago = LogUtils.countAtMinute((Event)d.fromArgument("event").getValue(), (ArrayList<Event>)d.fromEnvironment("log").getValue(), 1, null, now);
        int diff = Utils.get_percent_diff(now, ago);
        
        d.toEnvironment("date1", ago+"", true);
        d.toEnvironment("date1_diff", diff+"", true);
        
        System.err.println("#"+(Integer)d.fromEnvironment("date1_diff").getValue());
        System.err.println("#"+(Integer)d.fromEnvironment("date2_diff").getValue());
    }
    else {
        ago = LogUtils.countAtMinute((Event)d.fromArgument("event").getValue(), (ArrayList<Event>)d.fromEnvironment("log").getValue(), 5, null, now);
        int diff = Utils.get_percent_diff(now, ago);
        
        if (diff > 100) {
            System.err.println(now+", "+ago+", "+diff);
            System.exit(1);
        }
        d.toEnvironment("date2", ago+"", true);
        d.toEnvironment("date2_diff", diff+"", true);
    }
        
    //    System.err.println(min+":"+max+"^"+percent_diff);

    d.toProgram("id", DIFF);
    d.toProgram("program", "diff");
  }
  
  public void anomaly_detect (ExecData d) {
    d.next_step();
    System.err.println((Integer)d.fromEnvironment("date1_diff").getValue());
    System.err.println((Integer)d.fromEnvironment("date2_diff").getValue());
    sub.inner_check(((Integer)d.fromEnvironment("date2_diff").getValue() < 30) && ((Integer)d.fromEnvironment("date1_diff").getValue() < 30), d);   
  }

  public void validate(ExecData d) {
    d.next_step();
    
    if (((Integer)d.fromEnvironment("output").getValue()) != ((Integer)d.fromEnvironment("answer").getValue())) {
      d.toProgram("id", ALARM);
      d.toProgram("program", "alarm");
      alarms += 1;
    }
    else {
      d.toProgram("id", NO_ALARM);
      d.toProgram("program", "no_alarm");
      no_alarms += 1;
    }
    
    d.toEnvironment("terminate", "true", true);
    
    System.err.println("alarm: "+alarms+", no_alarm: "+no_alarms);
  }

  public void check_status(ExecData d) {
    // TODO Auto-generated method stub
    
  }

  public void check_time(ExecData d) {
    // TODO Auto-generated method stub
    
  }
}
