package com.dgbiztech.generator.config;

import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.ArrayList;
import java.util.List;

/*
 * 子节点的配置
 * @ClassName ChildNodeElementConfig
 * @author Hongping.Zhong
 * @Date 2019-02-15 16:58
 * @version 1.0.0
 */
public class ChildNodeElementConfig {

    private String nodeType = "ELEMENT";

    private XmlElement xmlElement;

    private TextElement textElement;

    private List<ChildNodeElementConfig> childNodeElementConfigs = new ArrayList<>();

    public void xx(){
        if (!"TEXT".equals(nodeType) && childNodeElementConfigs != null && childNodeElementConfigs.size()>0){
            for (ChildNodeElementConfig childNode : childNodeElementConfigs) {
                childNode.xx();
                if (childNode.getNodeType().equals("TEXT")){
                    xmlElement.addElement(childNode.getTextElement());
                }else{
                    xmlElement.addElement(childNode.getXmlElement());
                }
            }
        }
    }

    public List<ChildNodeElementConfig> getChildNodeElementConfigs() {
        return childNodeElementConfigs;
    }

    public void setChildNodeElementConfigs(List<ChildNodeElementConfig> childNodeElementConfigs) {
        this.childNodeElementConfigs = childNodeElementConfigs;
    }

    public TextElement getTextElement() {
        return textElement;
    }

    public void setTextElement(TextElement textElement) {
        this.textElement = textElement;
    }

    public XmlElement getXmlElement() {
        return xmlElement;
    }

    public void setXmlElement(XmlElement xmlElement) {
        this.xmlElement = xmlElement;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }
}
