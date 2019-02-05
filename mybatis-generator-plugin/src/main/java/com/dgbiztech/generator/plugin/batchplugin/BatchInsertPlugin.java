package com.dgbiztech.generator.plugin.batchplugin;

import com.dgbiztech.generator.utils.SqlMapperGeneratorTool;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

import java.util.List;

public class BatchInsertPlugin extends PluginAdapter {

    private final static String BATCH_INSERT = "batchInsert";

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    /**
     * 方法对应的xml节点在这里添加
     * @param document
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        if (introspectedTable.getTargetRuntime().equals(IntrospectedTable.TargetRuntime.MYBATIS3)) {
            addSqlMapper(document, introspectedTable);
        }
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }

    public void addSqlMapper(Document document, IntrospectedTable introspectedTable) {
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
}
