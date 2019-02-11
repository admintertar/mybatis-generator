package com.dgbiztech.generator.utils;

import com.dgbiztech.generator.entity.Table;
import org.mybatis.generator.api.IntrospectedColumn;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

public class VelocityInfoUtils {

    //日期格式处理
    public static void data(Properties properties, Map map){
        String dateFormat = properties.getProperty("dateFormat", "yyyy-MM-dd");
        SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat);
        map.put("date",dateFormatter.format(new Date()));
    }

    //主键处理
    public static void primaryKey(IntrospectedColumn introspectedColumn, Table table) {
        table.put("primaryKey",introspectedColumn.getJavaProperty());
    }
}
