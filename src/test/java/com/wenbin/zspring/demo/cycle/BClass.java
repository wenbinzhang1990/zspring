package com.wenbin.zspring.demo.cycle;

import com.wenbin.zspring.beans.ZAutowired;
import com.wenbin.zspring.beans.ZComponent;

@ZComponent
public class BClass {

  @ZAutowired
  public AClass aClass;
}
