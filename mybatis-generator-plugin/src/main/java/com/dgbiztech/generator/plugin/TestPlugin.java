package com.dgbiztech.generator.plugin;

import com.dgbiztech.generator.config.ChildNodeElementConfig;
import com.dgbiztech.generator.config.ColumnConfig;
import com.dgbiztech.generator.config.TableConfig;
import com.dgbiztech.generator.config.XmlElementConfig;
import com.dgbiztech.generator.utils.SqlMapperGeneratorTool;
import com.dgbiztech.generator.utils.Utils;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

/*
 *
 * @ClassName TestPlugin
 * @author Hongping.Zhong
 * @Date 2019-02-15 16:40
 * @version 1.0.0
 */
public class TestPlugin extends AbsBasePlugin{


    @Override
    public XmlElementConfig xmlSql(TableConfig tableConfig) {

        XmlElementConfig xmlElementConfig = new XmlElementConfig(SqlMapperGeneratorTool.UPDATE,
                "test",
                FullyQualifiedJavaType.getNewListInstance(),
                null);
        List<ChildNodeElementConfig> childNodeElementConfigs = xmlElementConfig.getChildNodeElementConfigs();

        ChildNodeElementConfig childNodeElementConfig = new ChildNodeElementConfig();
        childNodeElementConfig.setNodeType("TEXT");
        childNodeElementConfig.setTextElement(new TextElement("UPDATE "+ tableConfig.entityName));
        childNodeElementConfigs.add(childNodeElementConfig);

        ChildNodeElementConfig forNode = new ChildNodeElementConfig();
        forNode.setXmlElement(SqlMapperGeneratorTool.baseForeachElementGenerator("list", "item", "index", ";","begin",";end;"));
        List<ChildNodeElementConfig> ifNodes = forNode.getChildNodeElementConfigs();

        for (ColumnConfig allColumn : tableConfig.getAllColumns()) {
            ChildNodeElementConfig ifNode = new ChildNodeElementConfig();
            XmlElement anIf = new XmlElement("if");
            anIf.addAttribute(new Attribute("test", Utils.getParameterClause(allColumn,"item.")+"!=null"));
            anIf.addElement(new TextElement(String.format(" %s = %s ,",allColumn.getColumnName(),Utils.getParameterClause(allColumn,"item."))));
            ifNode.setXmlElement(anIf);
            ifNodes.add(ifNode);
        }

        childNodeElementConfigs.add(forNode);

        return xmlElementConfig;
    }
}
