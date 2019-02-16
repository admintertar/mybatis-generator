package com.dgbiztech.generator.config;

import com.dgbiztech.generator.utils.SqlMapperGeneratorTool;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.ArrayList;
import java.util.List;

/*
 *
 * @ClassName XmlElementConfig
 * @author Hongping.Zhong
 * @Date 2019-02-15 16:15
 * @version 1.0.0
 */
public class XmlElementConfig {

    /**
     * 节点
     */
    private final XmlElement xmlElement;

    /**
     * 子节点
     */
    private List<ChildNodeElementConfig> childNodeElementConfigs = new ArrayList<>();

    public XmlElementConfig(String sqlElementType, String sqlMapperId, FullyQualifiedJavaType parameterType,FullyQualifiedJavaType resultType) {
        xmlElement = SqlMapperGeneratorTool.baseElementGenerator(sqlElementType,sqlMapperId,parameterType,resultType);
    }

    public void xin(){
        for (ChildNodeElementConfig childNode : childNodeElementConfigs) {
            childNode.xx();
            if ("TEXT".equals(childNode.getNodeType())){
                xmlElement.addElement(childNode.getTextElement());
            }else{
                xmlElement.addElement(childNode.getXmlElement());
            }
        }
    }

    public XmlElement getXmlElement() {
        return xmlElement;
    }

    public List<ChildNodeElementConfig> getChildNodeElementConfigs() {
        return childNodeElementConfigs;
    }

    public void setChildNodeElementConfigs(List<ChildNodeElementConfig> childNodeElementConfigs) {
        this.childNodeElementConfigs = childNodeElementConfigs;
    }
}
