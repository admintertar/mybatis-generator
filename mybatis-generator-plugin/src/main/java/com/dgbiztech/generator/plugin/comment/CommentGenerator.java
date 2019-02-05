package com.dgbiztech.generator.plugin.comment;

import com.dgbiztech.generator.utils.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class CommentGenerator extends EmptyCommentGenerator {

    private Properties properties;

    public CommentGenerator() {
        properties = new Properties();
    }

    @Override
    public void addConfigurationProperties(Properties properties) {
        // 获取自定义的 properties
        this.properties.putAll(properties);
    }

    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        String author = properties.getProperty("author");
        String dateFormat = properties.getProperty("dateFormat", "yyyy-MM-dd");
        String baseModel = properties.getProperty("interface","false");
        if (baseModel.equals("true")){
            String baseClass = properties.getProperty("classpath","x");
            if (baseClass.equals("x")){
                throw new RuntimeException("classpath路径不存在!");
            }
            topLevelClass.addImportedType(baseClass);
            topLevelClass.addSuperInterface(new FullyQualifiedJavaType(baseClass.substring(baseClass.lastIndexOf(".")+1,baseClass.length())));
        }

        SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat);
        // 获取表注释
        String remarks = introspectedTable.getRemarks();
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * " + remarks);
        topLevelClass.addJavaDocLine(" * @author " + author);
        topLevelClass.addJavaDocLine(" * @date   " + dateFormatter.format(new Date()));
        topLevelClass.addJavaDocLine(" */");
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        // 获取列注释
        String remarks = introspectedColumn.getRemarks();
        field.addJavaDocLine("/**");
        field.addJavaDocLine(" * " + (StringUtils.isEmpty(remarks)?introspectedColumn.getActualColumnName():remarks));
        field.addJavaDocLine(" */");
    }
}
