package com.dgbiztech.generator.plugin.batchplugin;

import com.dgbiztech.generator.utils.SqlMapperGeneratorTool;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

import java.util.List;

public class SelectByIdsPlugin extends PluginAdapter {

    private final static String SELECT_BY_IDS = "selectByIds";

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    /**
     * 接口方法在这里添加
     * @param interfaze
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (introspectedTable.getTargetRuntime().equals(IntrospectedTable.TargetRuntime.MYBATIS3)) {
            addSqlInterface(interfaze, introspectedTable);
        }
        return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
    }

    private void addSqlInterface(Interface interfaze, IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.addJavaDocLine("/***请勿删除 父类没有此方法***/");
        method.setName(SELECT_BY_IDS);
        method.setReturnType(new FullyQualifiedJavaType("List<"+introspectedTable.getFullyQualifiedTable().getDomainObjectName()+">"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("List<String>"),"ids"));

        interfaze.addMethod(method);
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
        XmlElement selectXmlElement = SqlMapperGeneratorTool.baseElementGenerator(SqlMapperGeneratorTool.SELECT,
                SELECT_BY_IDS,
                FullyQualifiedJavaType.getNewListInstance(),
                new FullyQualifiedJavaType(context.getJavaModelGeneratorConfiguration().getTargetPackage()
                        +"."+introspectedTable.getFullyQualifiedTable().getDomainObjectName()));

        selectXmlElement.addElement(new TextElement(String.format("SELECT * FROM %s WHERE %s IN",tableName,primaryKeyName)));

        XmlElement foreachElement = SqlMapperGeneratorTool.baseForeachElementGenerator("list", "item", "index", ",","(",")");
        selectXmlElement.addElement(foreachElement);

        foreachElement.addElement(new TextElement("#{item}"));

        document.getRootElement().addElement(selectXmlElement);
    }

}
