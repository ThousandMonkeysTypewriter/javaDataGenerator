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

public class RedirectData {
  
  public static int MATCH_PHRASE = 1;
  public static int MATCH_ALL = 2;
  public static int MATCH_EXACT = 3;
  
  private int id;
  private String url;
  private int type;
  private ArrayList<String> words = new ArrayList<String>();  
  
  public RedirectData(ArrayList<String> words_, String url_, String type_, int id_) throws Exception {
    words = words_;
    url = url_;
    type = toType(type_);
    id = id_;
  }

  private int toType(String t) throws Exception {
    if (t.equals("MATCH_PHRASE"))
      return MATCH_PHRASE;
    
    if (t.equals("MATCH_ALL"))
      return MATCH_ALL;
    
    if (t.equals("MATCH_EXACT"))
      return MATCH_EXACT;
    
    else
      throw new Exception(String.format("Unknown redirect type %s", t));
  }

  public int getId() {
    return id;
  }
  
  public String getUrl() {
    return url;
  }
  
  public int getType() {
    return type;
  }
  
  public ArrayList<String> getWords() {
    return words;
  }
}

