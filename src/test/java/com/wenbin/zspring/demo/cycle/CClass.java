package com.wenbin.zspring.demo.cycle;

import com.wenbin.zspring.beans.ZAutowired;
import com.wenbin.zspring.beans.ZComponent;

@ZComponent
public class CClass {
  @ZAutowired
  public DClass dClass;

  @ZAutowired
  public  EClass eClass;
}
