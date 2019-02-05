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

public class BatchDeletePlugin extends PluginAdapter {

    private final static String BATCH_DELETE = "batchDelete";

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
