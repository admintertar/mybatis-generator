package com.dgbiztech.generator.plugin;

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

public class GetPagePlugin extends PluginAdapter {

    private final static String GET_PAGE = "getPage";

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

        //model的包路径
        String baseRecordType = introspectedTable.getBaseRecordType();

        //primaryKey的JDBC名字
        String primaryKeyName = introspectedTable.getPrimaryKeyColumns().get(0).getActualColumnName();

        //primaryKey的JAVA变量
        String primaryKeyParameterClause = MyBatis3FormattingUtilities.getParameterClause(introspectedTable.getPrimaryKeyColumns().get(0), "item.");

        //primaryKey的JAVA名字
        String primaryKeyJavaName = introspectedTable.getPrimaryKeyColumns().get(0).getJavaProperty();

        //构建一个xml节点，
        XmlElement getPageXmlElement = SqlMapperGeneratorTool.baseElementGenerator(SqlMapperGeneratorTool.SELECT,
                GET_PAGE,
                new FullyQualifiedJavaType(baseRecordType),
                new FullyQualifiedJavaType("com.dgbiztech.core.dto.MapDto"));
        getPageXmlElement.addElement(new TextElement(String.format("SELECT * FROM %s ",tableName)));

        XmlElement whereElement = new XmlElement("where");
        getPageXmlElement.addElement(whereElement);

        for (int i = 0; i < columnList.size(); i++) {

            IntrospectedColumn introspectedColumn = columnList.get(i);

            String columnName = introspectedColumn.getActualColumnName();

            String columnJavaTypeName = introspectedColumn.getJavaProperty();

            String parameterClause = MyBatis3FormattingUtilities.getParameterClause(introspectedColumn);

            String ifSql = String.format("AND %s = %s ",columnName,parameterClause);
            XmlElement ifElement = SqlMapperGeneratorTool.baseIfJudgeElementGen(columnJavaTypeName, ifSql);

            whereElement.addElement(ifElement);
        }

        document.getRootElement().addElement(getPageXmlElement);
    }
}
