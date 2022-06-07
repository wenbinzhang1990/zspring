package com.wenbin.zspring.beans;

/**
 *   @Description
 *
 *   @Author wenbin
 */
public  class BeanNameGenerator {
    public static String generateBeanName(String beanName)
    {
        char[] chars=beanName.toCharArray();
        if(chars[0]>='A'&&chars[0]<='Z')
        {
            chars[0]+=32;
        }

        return String.valueOf(chars);
    }
}
