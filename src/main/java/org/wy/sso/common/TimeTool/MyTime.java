package org.wy.sso.common.TimeTool;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component(value = "MyTime")
public class MyTime {

    /**
     * 返回指定日期格式的当前系统时间
     * @return
     */
    public String currentDate(String pattern){
        SimpleDateFormat df = new SimpleDateFormat(pattern);//设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
    }

    /**
     * 返回当前时间的时间戳（秒）
     * @return
     */
    public long currentTime(){
        return new Date().getTime() / 1000;
    }
}
