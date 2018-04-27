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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Event {
  public Source _source;
  public Date date;
  public int id;
  
  public int year;
  public int month;
  public int day;
  
  public int hour;
  public int minute;
  public int second;
  
  public String getTimeStamp() {
    return _source.timestamp;
  }
  
  public Double getRequestTime() {
    return _source.request_time;
  }
  
  public Integer getStatus() {
    return _source.status;
  }

  public void setId (int id_) {
     id = id_;
  }
  
  public int getId () {
     return id;
  }
  
  public void setTimes() throws ParseException {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    date = df.parse(_source.timestamp);
    
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    year = cal.get(Calendar.YEAR);
    month = cal.get(Calendar.MONTH);
    day = cal.get(Calendar.DAY_OF_MONTH);
    hour = cal.get(Calendar.HOUR);
    minute = cal.get(Calendar.MINUTE);
    second = cal.get(Calendar.SECOND);
  }
}

class Source {
  public String input_type;
  public Integer upstream_times;
  public Integer bytes;
  public Integer status;
  public String term;
  /*"beat": {
      "hostname": "ip-172-31-29-17",
      "name": "ip-172-31-29-17",
      "version": "5.5.1"
    },*/
  public String remote_ip;
  /*"tags": [
      "balancer"
    ],*/
  public Double request_time;
  public String service;
  public String vhost;
  /*"xff": [
      "91.220.181.76"
    ],*/
  public String timestamp;
  public String source;
  /*"upstream_addr": [
      "18.195.157.148:80"
    ],
    String region_id; 
    "request": "/prefix?term=Пароварка&region_id=52",
    "offset": 271993406,
    "upstream_status": 0,
    "client": "mvideo",
    "type": "log",
    "agent": "",
    "cache": true,
    "host": "ip-172-31-29-17",
    "@version": "1",
    "method": "GET"*/
}
