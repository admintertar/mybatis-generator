package com.dgbiztech.generator.utils;

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

}
