package com.ldap.model.util;

public class StrToJson {
   public static String convert(int status,String message){
	   return "{\"status\":\""+status+"\",\"message\":\""+message+"\"}";
   }
}
