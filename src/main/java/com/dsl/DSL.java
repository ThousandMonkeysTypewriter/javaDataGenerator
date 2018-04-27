package com.dsl;

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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.dsl.data.Utils;
import com.dsl.executor.Executor;
import com.dsl.executor.info.ExecData;
import com.dsl.executor.info.Step;

import java.util.ArrayList;

import com.google.gson.Gson;

public class DSL {

  public Executor exec;
  public ArrayList<ExecData> data;
  protected ArrayList<HashMap<Integer, Step>> out = new ArrayList<HashMap<Integer, Step>>();
  protected String filename;
  protected String log;
  protected String root = "/home/nayname/data/data";

  public DSL (String filename_) {
    exec = new Executor();
    data = new ArrayList<ExecData>();

    filename = root+"/"+filename_+".json";
    log = root+"/log.json";		

    try {
      File f_ = new File(filename);
      f_.createNewFile();
      new FileOutputStream(f_, false);
      
      File l_ = new File(log);
      l_.createNewFile();
      new FileOutputStream(l_, false);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } // if file already exists will do nothing  
  }

  public ArrayList<HashMap<Integer, Step>> getBuffer() {
    return out;
    //Utils.writeFile(tmp, "/root/NeuralProgramSynthesis/dsl/data/data_buffer.json", false);
  }

  public void mess(String mess) {
    try {
      ArrayList<String> tmp = new ArrayList<String>();
      tmp.add(mess);
      Utils.writeFile(tmp, log, true);
    }
    catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage(), e);
    }
  }

  public void send(String mess, String label) {
    try {
      ArrayList<String> tmp = new ArrayList<String>();
      tmp.add(mess);
      Utils.writeFile(tmp, filename, true);
    }
    catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }
}
