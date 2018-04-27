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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RedirectUtils {
  
  public static Map<String, Collection<RedirectData>> loadRedirects(
      RedirectsJson[] fromJson) throws Exception {
    Pattern unglue = Pattern.compile("([^\\p{Space}-][\\p{IsAlphabetic},.;'\\[\\]<>:\"{}]+|[0-9]+([.,][0-9]+)*)");
    
    Map<String,Collection<RedirectData>> redirs = new HashMap<String,Collection<RedirectData>>();
    int count = 0;
    
    for ( RedirectsJson t : fromJson)
    {
      ArrayList<String> words = new ArrayList<String>();
      count++;
      
      Matcher mt = unglue.matcher(t.request);
      while ( mt.find() ) {
        String w = mt.group(1);
        
        words.add(w.toLowerCase());
      }
      
      RedirectData r = new RedirectData(words, t.url, t.type, count);
      
      for ( String w : words) {
        Collection<RedirectData> coll = redirs.get(w);

        if (coll == null) {
          coll = new ArrayList<RedirectData>();
          redirs.put(w, coll);
        }

        coll.add(r);
      }
    }
    
    return redirs;
  }

}
