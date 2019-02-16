package com.dgbiztech.generator.utils;

import com.dgbiztech.generator.config.ColumnConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {

    private static final Logger log = LoggerFactory.getLogger(Utils.class);

    public static String showDisplay(String s){
        switch (s){
            case "java.lang.String":
                return "sn";
            case "java.util.Date":
                return "datetime";
        }
        return "sn";
    }

    public static String getParameterClause(
            ColumnConfig column, String prefix) {
        StringBuilder sb = new StringBuilder();

        sb.append("#{");
        sb.append(column.getJavaProperty(prefix));
        sb.append(",jdbcType=");
        sb.append(column.getJdbcTypeName());
        sb.append('}');

        return sb.toString();
    }

}
