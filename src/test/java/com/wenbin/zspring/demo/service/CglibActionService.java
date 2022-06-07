package com.wenbin.zspring.demo.service;

import com.wenbin.zspring.beans.ZComponent;

@ZComponent
public class CglibActionService  {


  public void action() {
    System.out.println("cglibAction");
  }


  public void actionAspectByLog() {
    System.out.println("cglibActionAspectByLog");
  }

  public void actionAspectByLogAndSecurity() {
    System.out.println("cglibActionAspectByLogAndSecurity");
  }

}
