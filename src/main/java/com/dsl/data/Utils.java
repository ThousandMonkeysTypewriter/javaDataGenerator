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
package com.dsl.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Utils {

  public static String readFile(String filename) throws IOException{
    FileReader files = null;
    String txt = "";
    try {
      files = new FileReader(filename);
      txt += new Scanner(files).nextLine();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    finally {
      if (files!=null) 
        files.close(); 
    }

    return txt;
  }
  
  public static int get_percent_diff(double now, double ago) {
    double min;
    double max;
    
    if (now > ago) {
      min = ago;
      max = now;
    } else {
      min = now;
      max = ago;      
    }
    
    if ( (max - min) > min )
        return 100;
    else
        return (int)((max - min) / (min / 100));
  }
  
  public static void writeFile(List<String> lines, String filename, boolean append) throws IOException {
    Path file = Paths.get(filename);
    if (append)
      Files.write(file, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
    else
      Files.write(file, lines, Charset.forName("UTF-8"));
  }
  
  public static void writeFile1(String lines, String filename, boolean append) throws IOException {
    File file = new File("/root/javaDataGenerator/data/0.json");
    FileWriter fr = null;
    BufferedWriter br = null;
    String dataWithNewLine=lines+System.getProperty("line.separator");
    try{
        fr = new FileWriter(file);
        br = new BufferedWriter(fr);
        for(int i = 1; i>0; i--){
            br.write(dataWithNewLine);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }finally{
        try {
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   }

  public static String readUrl(String urlString) throws Exception {
    BufferedReader reader = null;
    try {
      URL url = new URL(urlString);
      reader = new BufferedReader(new InputStreamReader(url.openStream()));
      StringBuffer buffer = new StringBuffer();
      int read;
      char[] chars = new char[1024];
      while ((read = reader.read(chars)) != -1)
        buffer.append(chars, 0, read); 

      return buffer.toString();
    } finally {
      if (reader != null)
        reader.close();
    }
  }
}
