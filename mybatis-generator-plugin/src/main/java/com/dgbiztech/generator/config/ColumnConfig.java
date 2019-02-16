package com.dgbiztech.generator.config;

import org.mybatis.generator.api.IntrospectedColumn;

/*
 * 行数据
 * @ClassName ColumnConfig
 * @author Hongping.Zhong
 * @Date 2019-02-15 15:52
 * @version 1.0.0
 */
public class ColumnConfig {

    /**
     * 行名称
     */
    public String columnName;

    /**
     * jdbc类型
     */
    public String jdbcTypeName;

    /**
     * java属性名
     */
    public String javaProperty;

    /**
     * java属性类型
     */
    public String javaTypeName;

    public ColumnConfig(IntrospectedColumn column) {
        columnName = column.getActualColumnName();
        jdbcTypeName = column.getJdbcTypeName();
        javaProperty = column.getJavaProperty();
        javaTypeName = column.getFullyQualifiedJavaType().getFullyQualifiedName();
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getJdbcTypeName() {
        return jdbcTypeName;
    }

    public void setJdbcTypeName(String jdbcTypeName) {
        this.jdbcTypeName = jdbcTypeName;
    }

    public String getJavaProperty() {
        return javaProperty;
    }

    public String getJavaProperty(String prefix) {
        if (prefix == null) {
            return javaProperty;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        sb.append(javaProperty);
        return sb.toString();
    }

    public void setJavaProperty(String javaProperty) {
        this.javaProperty = javaProperty;
    }

    public String getJavaTypeName() {
        return javaTypeName;
    }

    public void setJavaTypeName(String javaTypeName) {
        this.javaTypeName = javaTypeName;
    }
}
