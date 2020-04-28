package com.dgbiztech.generator.plugin;

import com.dgbiztech.generator.config.TableConfig;
import com.dgbiztech.generator.config.XmlElementConfig;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Document;

import java.util.List;

/*
 * 插件扩展的抽象类，为了让插件开发更加的简便
 * @ClassName AbsBasePlugin
 * @author Hongping.Zhong
 * @Date 2019-02-15 14:27
 * @version 1.0.0
 */
public abstract class AbsBasePlugin extends PluginAdapter {


    public abstract XmlElementConfig xmlSql(TableConfig tableConfig);

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        if (introspectedTable.getTargetRuntime().equals(IntrospectedTable.TargetRuntime.MYBATIS3)) {
            this.addSqlMapper(document, introspectedTable);
        }
        return super.sqlMapDocumentGenerated(document,introspectedTable);
    }

    private void addSqlMapper(Document document, IntrospectedTable introspectedTable) {
        XmlElementConfig xmlconfig = this.xmlSql(new TableConfig(introspectedTable));
        xmlconfig.xin();
        document.getRootElement().addElement(xmlconfig.getXmlElement());
    }

}
