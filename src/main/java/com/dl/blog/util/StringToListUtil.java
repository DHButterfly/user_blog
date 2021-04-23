package com.dl.blog.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringToListUtil {

    public static List<String> convertToList(String string){
        List<String> list=new ArrayList<>();
        String[] strings=string.split(",");
        for(String str:strings){
            list.add(new String(str));
        }
        return list;
    }
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
    public static boolean isNumeric0(String str){//通过ASCII判断
        for(int i=str.length();--i>=0;){
            int chr=str.charAt(i);
            if(chr<48 || chr>57)
                return false;
        }
        return true;
    }
}
