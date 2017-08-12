/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.core.util;

import java.util.UUID;

/**
 *
 * @author Lenovo
 */
public class A_TEST {
    public static void main(String[] args){//20
        String uuid=UUID.randomUUID().toString().replaceAll("-", "").substring(0, 11);
        System.out.println(DateTimeUtil.strCurrentDateOnPatternDate("yyyyMMddHHmmss")+uuid);
    }
}
