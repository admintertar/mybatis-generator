package com.dgbiztech.generator.entity;

import com.dgbiztech.generator.utils.VelocityInfoUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.config.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table extends HashMap<String, String> {

    public final String actualName;
    public final String entityPackage;
    public final String entityName;
    public final String exampleName;
    public final String mapperPackage;
    public final String mapperName;
    public final String modelPackge;
    public String interfacServicePackge;
    public final String remarks;//该表注释
    public List<Column> columns = new ArrayList<>();

    public Table(Context context, IntrospectedTable introspectedTable, Map<String, String> parent) {

        actualName = introspectedTable.getFullyQualifiedTable().getIntrospectedTableName();

        modelPackge = context.getJavaModelGeneratorConfiguration().getTargetPackage();

        entityPackage = introspectedTable.getIbatis2SqlMapPackage();
        entityName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();

        exampleName = introspectedTable.getExampleType();

        mapperPackage = introspectedTable.getIbatis2SqlMapPackage();
        mapperName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();

        remarks = introspectedTable.getRemarks();
        //主键处理
        VelocityInfoUtils.primaryKey(introspectedTable.getAllColumns().get(1),this);

        for (IntrospectedColumn introspectedColumn : introspectedTable.getAllColumns()) {
            columns.add(new Column(context, introspectedTable, introspectedColumn, this));
        }
        //外部参数添加到table
        this.putAll(parent);
    }

    public String getModelPackge() {
        return modelPackge;
    }

    public String getActualName() {
        return actualName;
    }

    public String getEntityPackage() {
        return entityPackage;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getExampleName() {
        return exampleName;
    }

    public String getMapperPackage() {
        return mapperPackage;
    }

    public String getMapperName() {
        return mapperName;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public String getInterfacServicePackge() {
        return interfacServicePackge;
    }

    public void setInterfacServicePackge(String interfacServicePackge) {
        this.interfacServicePackge = interfacServicePackge;
    }

    public String getRemarks() {
        return remarks;
    }

    @Override
    public String get(Object key) {
        return super.get(key);
    }
}
