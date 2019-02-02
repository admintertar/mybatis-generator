package com.dgbiztech.generator.utils;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class SqlMapperGeneratorTool {
    /**
     * The constant INSERT.
     */
    public static final String INSERT = "insert";

    /**
     * The constant DELETE.
     */
    public static final String DELETE = "delete";

    /**
     * The constant UPDATE.
     */
    public static final String UPDATE = "update";

    /**
     * The constant SELECT.
     */
    public static final String SELECT = "select";


    /**
     * 基础XmlElement构造器.
     * @param sqlElementType 节点类型
     * @param sqlMapperId  节点id
     * @param parameterType  参数类型
     * @return
     */
    public static XmlElement baseElementGenerator(String sqlElementType, String sqlMapperId, FullyQualifiedJavaType parameterType,FullyQualifiedJavaType resultType){

        XmlElement baseElement = new XmlElement(sqlElementType);

        if (sqlMapperId != null){
            baseElement.addAttribute(new Attribute("id", sqlMapperId));
        }

        if(parameterType != null){
            baseElement.addAttribute(new Attribute("parameterType", parameterType.getFullyQualifiedName()));
        }

        if (resultType != null){
            baseElement.addAttribute(new Attribute("resultType",resultType.getFullyQualifiedName()));
        }

        return baseElement;
    }


    /**
     * 基础foreach Element构造器.
     * @param collectionName
     * @param itemName
     * @param indexName
     * @param separatorName
     * @param openName
     * @param closeName
     * @return
     */
    public static XmlElement baseForeachElementGenerator(String collectionName,String itemName,String indexName ,String separatorName,String openName,String closeName){
        XmlElement foreachElement = new XmlElement("foreach");
        if (null!=collectionName){
            foreachElement.addAttribute(new Attribute("collection", collectionName));
        }
        if (null!=itemName){
            foreachElement.addAttribute(new Attribute("item", itemName));
        }
        if (null!=indexName){
            foreachElement.addAttribute(new Attribute("index", indexName));
        }
        if (null!=separatorName){
            foreachElement.addAttribute(new Attribute("separator", separatorName));
        }
        if (null!=openName){
            foreachElement.addAttribute(new Attribute("open", openName));
        }
        if (null!=closeName){
            foreachElement.addAttribute(new Attribute("close", closeName));
        }
        return foreachElement;
    }


    /**
     * 基础IF Element构造器.
     * @param columnJavaTypeName
     * @param sql
     * @return
     */
    public static XmlElement baseIfJudgeElementGen(String columnJavaTypeName,String sql){
        String colmunJudge = columnJavaTypeName + " != null and " + columnJavaTypeName + " != ''";
        XmlElement ifElement = new XmlElement("if");
        ifElement.addAttribute(new Attribute("test", colmunJudge));
        ifElement.addElement(new TextElement(sql));
        return ifElement;
    }

    /**
     * 基础Trim Element构造器.
     * @param prefix
     * @param suffix
     * @param suffixOverrides
     * @return
     */
    public static XmlElement baseTrimElement(String prefix,String suffix,String suffixOverrides){
        XmlElement trimElement = new XmlElement("trim");
        if (null != prefix){
            trimElement.addAttribute(new Attribute("prefix", prefix));
        }
        if (null != suffix){
            trimElement.addAttribute(new Attribute("suffix", suffix));
        }
        if (null!=suffixOverrides){
            trimElement.addAttribute(new Attribute("suffixOverrides", suffixOverrides));
        }
        return trimElement;
    }

}
