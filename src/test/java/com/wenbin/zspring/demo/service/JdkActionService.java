package com.wenbin.zspring.demo.service;

import com.wenbin.zspring.beans.ZComponent;

@ZComponent
public class JdkActionService implements Action, LogAction {

  @Override
  public void action() {
    System.out.println("jdkAction");
  }

  @Override
  public void actionAspectByLog() {
    System.out.println("jdkActionAspectByLog");
  }

  public void actionAspectByLogAndSecurity() {
    System.out.println("jdkActionAspectByLogAndSecurity");
  }

}
