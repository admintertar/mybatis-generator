package com.dgbiztech.generator.config;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;

import java.util.ArrayList;
import java.util.List;

/*
 * 生成xml文件可能用到的表信息
 * @ClassName TableConfig
 * @author Hongping.Zhong
 * @Date 2019-02-15 14:33
 * @version 1.0.0
 */
public class TableConfig {

    /**
     * 数据库表名
     */
    public final String tebleName;

    /**
     * 实体类名
     */
    public final String entityName;

    /**
     * 完整包名
     */
    public final String entityPackge;

    /**
     * xml接口完整包名
     */
    public final String sqlMapNamespace;

    /**
     * xml文件包名
     */
    public final String xmlMapperPackage;

    /**
     * xml文件名
     */
    public final String xmlMapperFileName;

    /**
     * Base_Column_List
     */
    public final String baseColumnListId;

    /**
     * BaseResultMap
     */
    public final String baseResultMapId;

    /**
     * 该表注释
     */
    public final String remarks;

    /**
     * 所有行数据
     */
    public List<ColumnConfig> allColumns = new ArrayList<>();

    /**
     * 主键行数据
     */
    public List<ColumnConfig> primaryKeyColumns = new ArrayList<>();



    public TableConfig(IntrospectedTable introspectedTable) {
        tebleName = introspectedTable.getTableConfiguration().getTableName();
        entityName = introspectedTable.getTableConfiguration().getDomainObjectName();
        entityPackge = introspectedTable.getBaseRecordType();
        sqlMapNamespace = introspectedTable.getMyBatis3FallbackSqlMapNamespace();
        xmlMapperPackage = introspectedTable.getMyBatis3XmlMapperPackage();;
        xmlMapperFileName = introspectedTable.getMyBatis3XmlMapperFileName();
        baseColumnListId = introspectedTable.getBaseColumnListId();
        baseResultMapId = introspectedTable.getBaseResultMapId();
        remarks = introspectedTable.getRemarks();

        List<IntrospectedColumn> allColumns = introspectedTable.getAllColumns();
        for (IntrospectedColumn allColumn : allColumns) {
            this.allColumns.add(new ColumnConfig(allColumn));
        }
        List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
        for (IntrospectedColumn primaryKeyColumn : primaryKeyColumns) {
            this.primaryKeyColumns.add(new ColumnConfig(primaryKeyColumn));
        }

    }


    public String getTebleName() {
        return tebleName;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getEntityPackge() {
        return entityPackge;
    }

    public String getSqlMapNamespace() {
        return sqlMapNamespace;
    }

    public String getXmlMapperPackage() {
        return xmlMapperPackage;
    }

    public String getXmlMapperFileName() {
        return xmlMapperFileName;
    }

    public String getBaseColumnListId() {
        return baseColumnListId;
    }

    public String getBaseResultMapId() {
        return baseResultMapId;
    }

    public String getRemarks() {
        return remarks;
    }

    public List<ColumnConfig> getAllColumns() {
        return allColumns;
    }

    public void setAllColumns(List<ColumnConfig> allColumns) {
        this.allColumns = allColumns;
    }

    public List<ColumnConfig> getPrimaryKeyColumns() {
        return primaryKeyColumns;
    }

    public void setPrimaryKeyColumns(List<ColumnConfig> primaryKeyColumns) {
        this.primaryKeyColumns = primaryKeyColumns;
    }
}
