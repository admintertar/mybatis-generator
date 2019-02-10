package com.dgbiztech.generator.entity;

import com.dgbiztech.generator.utils.StringUtils;
import com.dgbiztech.generator.utils.Utils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.config.Context;

public class Column {
    public final String actualName;//真实列名称
    public final String fieldName;//该字段entity中的变量名称
    public final String fieldType;//该字段的类型
    public final String remarks;//该字段的注释
    public final String display;//js显示的类型

    public Column(Context context, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Table table) {
        //如果没有注释，那么写字段名
        if (StringUtils.isEmpty(introspectedColumn.getRemarks())){
            remarks = introspectedColumn.getJavaProperty();
        }else{
            remarks = introspectedColumn.getRemarks()
                    //去除注释里面的逗号,冒号
                    .replaceAll(",","").replaceAll("，","").replaceAll(":","").replaceAll("：","");
        }
        fieldName = introspectedColumn.getJavaProperty();
        fieldType = introspectedColumn.getFullyQualifiedJavaType().getFullyQualifiedName();
        actualName = introspectedColumn.getActualColumnName();
        display = Utils.showDisplay(fieldType);
    }

    public String getActualName() {
        return actualName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public String getDisplay() {
        return display;
    }

    public String getRemarks() {
        return remarks;
    }
}
