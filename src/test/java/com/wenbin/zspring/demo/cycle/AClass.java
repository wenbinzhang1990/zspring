package com.wenbin.zspring.demo.cycle;

import com.wenbin.zspring.beans.ZAutowired;
import com.wenbin.zspring.beans.ZComponent;

@ZComponent
public class AClass {
  @ZAutowired
  public BClass bClass;
}
