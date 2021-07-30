package com.slzh.utils;


import com.slzh.service.sso.util.SM2Util;
import com.slzh.service.sso.util.Util;

import java.security.MessageDigest;
import java.text.NumberFormat;

/**
 * 字符串工具类
 *
 * @author lanb
 * @date Sep 1, 2018
 */
public class CodeUtils {

   static  public String strEncode(String str) throws Exception{
       String encode = SM2Util.encrypt(Util.hexToByte(SM2Util.EXTERNAL_PUBLIC_KEY),str.getBytes());
       return encode;
   }


    static  public  String strDecode(String str) throws Exception{
       String decode = new String(SM2Util.decrypt(Util.hexToByte(SM2Util.INTERNAL_PRIVATE_KEY), Util.hexToByte(str)));

       return decode;
   }
}
