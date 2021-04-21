package com.chenlong.common.utils;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    //将当前时间格式化
    public static String getFormateNow(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        return format.format(new Date());
    }

    //将用户传入的时间格式化
    public static String getFormateNow(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        return format.format(date);
    }

    //指定当前时间的格式化模式
    public static String getFormateNow(String pattern){
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(new Date());
    }

    //指定其他时间的格式化模式
    public static String getFormateNow(Data data, String pattern){
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(data);
    }


}
