package com.dgbiztech.generator.plugin;

import com.dgbiztech.generator.utils.SqlMapperGeneratorTool;
import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BatchGroupPlugin extends PluginAdapter {

    private final static String BATCH_DELETE = "batchDelete";

    private final static String BATCH_INSERT = "batchInsert";

    private final static String BATCH_UPDATE = "batchUpdate";

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        String isGenericDao = properties.getProperty("GenericDao", "false");
        if (isGenericDao.equals("true")){
            String modelName = introspectedTable.getBaseRecordType();
            FullyQualifiedJavaType genericDao = new FullyQualifiedJavaType("GenericDao<"+modelName+",String>");
            interfaze.addSuperInterface(genericDao);
            interfaze.addImportedType(new FullyQualifiedJavaType("com.dgbiztech.core.mybatis.GenericDao"));
        }
        return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
    }

//    @Override
//    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
//        JavaFormatter javaFormatter = context.getJavaFormatter();
//
//        List<GeneratedJavaFile> mapperJavaFiles = new ArrayList<GeneratedJavaFile>();
//
//        Interface mapperInterface = new Interface("com.dgbiztech.erp.service.IAbcService");
//
//        mapperInterface.setVisibility(JavaVisibility.PUBLIC);
//        mapperInterface.addJavaDocLine("/**");
//        mapperInterface.addJavaDocLine(" * 由MyBatis Generator工具自动生成，请不要手动修改");
//        mapperInterface.addJavaDocLine(" */");
//
//        FullyQualifiedJavaType daoSuperType = new FullyQualifiedJavaType("com.dgbiztech.erp.service");
//        // 添加泛型支持
////        daoSuperType.addTypeArgument(baseModelJavaType);
////        mapperInterface.addImportedType(baseModelJavaType);
//        mapperInterface.addImportedType(daoSuperType);
//        mapperInterface.addSuperInterface(daoSuperType);
//
//        GeneratedJavaFile mapperJavafile = new GeneratedJavaFile(mapperInterface, "com.dgbiztech.erp.service", javaFormatter);
//        mapperJavaFiles.add(mapperJavafile);
//
//        return mapperJavaFiles;
//    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        if (introspectedTable.getTargetRuntime().equals(IntrospectedTable.TargetRuntime.MYBATIS3)) {
            this.addBatchDeleteSqlMapper(document, introspectedTable);
            this.addBatchInsertSqlMapper(document, introspectedTable);
            this.addBatchUpdateSqlMapper(document, introspectedTable);
        }
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }

    /**
     * 批量非空更新
     * @param document
     * @param introspectedTable
     */
    public void addBatchUpdateSqlMapper(Document document, IntrospectedTable introspectedTable) {
        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();

        List<IntrospectedColumn> columnList = introspectedTable.getAllColumns();
        //primaryKey的JDBC名字
        String primaryKeyName = introspectedTable.getPrimaryKeyColumns().get(0).getActualColumnName();

        //primaryKey的JAVA变量
        String primaryKeyParameterClause = MyBatis3FormattingUtilities.getParameterClause(introspectedTable.getPrimaryKeyColumns().get(0), "item.");

        //primaryKey的JAVA名字
        String primaryKeyJavaName = introspectedTable.getPrimaryKeyColumns().get(0).getJavaProperty();

        //构建一个xml节点，
        XmlElement updateXmlElement = SqlMapperGeneratorTool.baseElementGenerator(SqlMapperGeneratorTool.UPDATE,
                BATCH_UPDATE,
                FullyQualifiedJavaType.getNewListInstance(),
                null);

        XmlElement foreachElement = SqlMapperGeneratorTool.baseForeachElementGenerator("list", "item", "index", ";","begin",";end;");
        foreachElement.addElement(new TextElement(String.format("UPDATE %s",tableName)));
        updateXmlElement.addElement(foreachElement);

        XmlElement setElement = new XmlElement("set");
        foreachElement.addElement(setElement);

        for (int i = 0; i < columnList.size(); i++) {

            IntrospectedColumn introspectedColumn = columnList.get(i);

            String columnName = introspectedColumn.getActualColumnName();

            String columnJavaTypeName = introspectedColumn.getJavaProperty("item.");

            String parameterClause = MyBatis3FormattingUtilities.getParameterClause(introspectedColumn, "item.");

            //如果是主键
            if(primaryKeyParameterClause.equals(parameterClause)){
                continue;
            }

            if (introspectedColumn.isIdentity()) {
                continue;
            }

            String ifSql = String.format(" %s = %s ",columnName,parameterClause);
            XmlElement ifElement = SqlMapperGeneratorTool.baseIfJudgeElementGen(columnJavaTypeName, ifSql);

            setElement.addElement(ifElement);
        }

        foreachElement.addElement(new TextElement(String.format("WHERE %s = %s",primaryKeyName,primaryKeyParameterClause)));
        document.getRootElement().addElement(updateXmlElement);
    }

    /**
     * 批量非空插入
     * @param document
     * @param introspectedTable
     */
    public void addBatchInsertSqlMapper(Document document, IntrospectedTable introspectedTable) {
        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
        List<IntrospectedColumn> columnList = introspectedTable.getAllColumns();
        //primaryKey的JDBC名字
        String primaryKeyName = introspectedTable.getPrimaryKeyColumns().get(0).getActualColumnName();

        //primaryKey的JAVA变量
        String primaryKeyParameterClause = MyBatis3FormattingUtilities.getParameterClause(introspectedTable.getPrimaryKeyColumns().get(0), "item.");

        //primaryKey的JAVA名字
        String primaryKeyJavaName = introspectedTable.getPrimaryKeyColumns().get(0).getJavaProperty();

        //构建一个xml节点，
        XmlElement insertXmlElement = SqlMapperGeneratorTool.baseElementGenerator(SqlMapperGeneratorTool.INSERT,
                BATCH_INSERT,
                FullyQualifiedJavaType.getNewListInstance(),
                null);

        XmlElement foreachElement = SqlMapperGeneratorTool.baseForeachElementGenerator("list", "item", "index", ";","begin",";end;");
        foreachElement.addElement(new TextElement(String.format("INSERT INTO %s",tableName)));
        insertXmlElement.addElement(foreachElement);

        XmlElement trimColumnElement = SqlMapperGeneratorTool.baseTrimElement("(",")",",");
        XmlElement trimBeanElement = SqlMapperGeneratorTool.baseTrimElement("(",")",",");

        foreachElement.addElement(trimColumnElement);
        foreachElement.addElement(trimBeanElement);

        for (int i = 0; i < columnList.size(); i++) {

            IntrospectedColumn introspectedColumn = columnList.get(i);

            String columnName = introspectedColumn.getActualColumnName();

            String columnJavaTypeName = introspectedColumn.getJavaProperty("item.");

            String parameterClause = MyBatis3FormattingUtilities.getParameterClause(introspectedColumn, "item.");

            if (introspectedColumn.isIdentity()) {
                continue;
            }

            String ifColumnSql = String.format(" %s,",columnName);
            XmlElement ifElement = SqlMapperGeneratorTool.baseIfJudgeElementGen(columnJavaTypeName, ifColumnSql);
            String ifBeanSql = String.format(" %s,",parameterClause);
            XmlElement ifBeanElement = SqlMapperGeneratorTool.baseIfJudgeElementGen(columnJavaTypeName, ifBeanSql);

            trimColumnElement.addElement(ifElement);
            trimBeanElement.addElement(ifBeanElement);
        }

        document.getRootElement().addElement(insertXmlElement);
    }

    /**
     * 批量删除
     * @param document
     * @param introspectedTable
     */
    public void addBatchDeleteSqlMapper(Document document, IntrospectedTable introspectedTable) {
        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
        List<IntrospectedColumn> columnList = introspectedTable.getAllColumns();
        //primaryKey的JDBC名字
        String primaryKeyName = introspectedTable.getPrimaryKeyColumns().get(0).getActualColumnName();

        //primaryKey的JAVA变量
        String primaryKeyParameterClause = MyBatis3FormattingUtilities.getParameterClause(introspectedTable.getPrimaryKeyColumns().get(0), "item.");

        //primaryKey的JAVA名字
        String primaryKeyJavaName = introspectedTable.getPrimaryKeyColumns().get(0).getJavaProperty();

        //构建一个xml节点，
        XmlElement deleteXmlElement = SqlMapperGeneratorTool.baseElementGenerator(SqlMapperGeneratorTool.DELETE,
                BATCH_DELETE,
                FullyQualifiedJavaType.getNewListInstance(),
                null);
        deleteXmlElement.addElement(new TextElement(String.format("DELETE FROM %s WHERE %s IN",tableName,primaryKeyName)));

        XmlElement foreachElement = SqlMapperGeneratorTool.baseForeachElementGenerator("list", "item", "index", ",","(",")");
        deleteXmlElement.addElement(foreachElement);

        foreachElement.addElement(new TextElement(String.format("%s",primaryKeyParameterClause)));

        document.getRootElement().addElement(deleteXmlElement);
    }

}
