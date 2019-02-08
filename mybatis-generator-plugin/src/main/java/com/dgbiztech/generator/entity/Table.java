package com.dgbiztech.generator.entity;

import com.dgbiztech.generator.utils.Utils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.config.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created By spawpaw@hotmail.com  2018-03-22
 *
 * @author BenBenShang spawpaw@hotmail.com
 */
public class Table extends ConfigMatcher {
    public final String actualName;
    public final String entityPackage;
    public final String entityName;
    public final String entityLowerCamel;
    public final String exampleName;
    public final String exampleLowerCamel;
    public final String mapperPackage;
    public final String mapperName;
    public final String mapperLowerCamel;
    public final String modelPackge;
    public String interfacServicePackge;
    public List<Column> columns = new ArrayList<>();
    Logger log = LoggerFactory.getLogger(Table.class);


    public Table(Context context, IntrospectedTable introspectedTable, Map<String, String> parent) {
        super(introspectedTable.getRemarks(), parent);

        actualName = introspectedTable.getFullyQualifiedTable().getIntrospectedTableName();

        modelPackge = context.getJavaModelGeneratorConfiguration().getTargetPackage();

        entityPackage = introspectedTable.getIbatis2SqlMapPackage();
        entityName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        entityLowerCamel = Utils.getLowerCamelCase(entityName);

        exampleName = introspectedTable.getExampleType();
        exampleLowerCamel = Utils.getLowerCamelCase(exampleName);

        mapperPackage = introspectedTable.getIbatis2SqlMapPackage();
        mapperName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        mapperLowerCamel = Utils.getLowerCamelCase(mapperName);

        for (IntrospectedColumn introspectedColumn : introspectedTable.getAllColumns()) {
            columns.add(new Column(context, introspectedTable, introspectedColumn, this));
        }
    }

    public String getModelPackge() {
        return modelPackge;
    }

    public Logger getLog() {
        return log;
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

    public String getEntityLowerCamel() {
        return entityLowerCamel;
    }

    public String getExampleName() {
        return exampleName;
    }

    public String getExampleLowerCamel() {
        return exampleLowerCamel;
    }

    public String getMapperPackage() {
        return mapperPackage;
    }

    public String getMapperName() {
        return mapperName;
    }

    public String getMapperLowerCamel() {
        return mapperLowerCamel;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public void setLog(Logger log) {
        this.log = log;
    }

    public String getInterfacServicePackge() {
        return interfacServicePackge;
    }

    public void setInterfacServicePackge(String interfacServicePackge) {
        this.interfacServicePackge = interfacServicePackge;
    }

    @Override
    public String toString() {
        return "Table{" +
                "actualName='" + actualName + '\'' +
                ", entityPackage='" + entityPackage + '\'' +
                ", entityName='" + entityName + '\'' +
                ", entityLowerCamel='" + entityLowerCamel + '\'' +
                ", exampleName='" + exampleName + '\'' +
                ", exampleLowerCamel='" + exampleLowerCamel + '\'' +
                ", mapperPackage='" + mapperPackage + '\'' +
                ", mapperName='" + mapperName + '\'' +
                ", mapperLowerCamel='" + mapperLowerCamel + '\'' +
                ", modelPackge='" + modelPackge + '\'' +
                ", columns=" + columns +
                '}';
    }
}