package com.dgbiztech.generator.plugin;

import com.dgbiztech.generator.config.TemplateConfig;
import com.dgbiztech.generator.entity.ConfigWrapper;
import com.dgbiztech.generator.entity.Table;
import com.dgbiztech.generator.utils.FileUtil;
import com.dgbiztech.generator.utils.Utils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ServiceControllerPlugin extends PluginAdapter {

    private String projectDir = "/Users/ping/repo/repo-git/mybatis-generator/mybatis-generator";
    private String basePackage = "com.dgbiztech";

    Logger log = LoggerFactory.getLogger(ServiceControllerPlugin.class);

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        log.info(">>>> 开始生成文件...");
        Map<String, String> map = new HashMap<>();
        for (Object o : properties.keySet()) {
            map.put(o.toString(), properties.getProperty(o.toString()));
        }
        //封装表数据
        Table table = new Table(context, introspectedTable, map);
        //初始化context
        VelocityContext templateContext = new VelocityContext();
        templateContext.put("table", table);

        //读取配置文件
        InputStream resourceAsStream = this.getClass().getResourceAsStream("/config.yml");
        List<TemplateConfig> configs = new Yaml().loadAs(resourceAsStream, ConfigWrapper.class).getTemplateConfig();

        for (TemplateConfig config : configs) {
            //当前包名
            templateContext.put("destPackage",config.getDestPackage()
                    .replace("${basePackage}",basePackage)
                    .replace("${entityName}",table.entityName.toLowerCase()));

            //组装模版
            String content = renderTemplateAsString(config.getTemplate(), templateContext);
            //拼装路径
            String absPath = this.filePath(config,table);
            //写入文件
            try {
                FileUtil.writeStringToFile(absPath, content);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        log.info("<<< 生成文件ok.");
        return super.contextGenerateAdditionalJavaFiles(introspectedTable);
    }

    /**
     * 文件路径处理
     * @param config
     * @param table
     * @return
     */
    private String filePath(TemplateConfig config,Table table) {
        String destDir = config.getDestDir().replaceAll("\\.", "/");
        String destPackage = config.getDestPackage();
        String destFileName = config.getDestFileName();
        //路径处理
        String absPath = (projectDir == null || projectDir.isEmpty() ? "" : projectDir + (projectDir.endsWith("/") || projectDir.endsWith("\\") ? "" : "/"))
                + destDir
                + "/"
                + destPackage.replace(".", "/")
                + "/";
        absPath = absPath.replace("//", "/");
        absPath = absPath.replace("${entityName}", table.getEntityName());
        absPath = absPath.replace("${basePackage}", basePackage.replace(".", "/"));
        absPath = absPath.toLowerCase() + destFileName;
        absPath = absPath.replace("${entityName}", table.getEntityName());
        log.info("文件路径："+absPath);
        return absPath;
    }

    /**
     * 写入模版文件
     * @param templateFile
     * @param ctx
     * @return
     */
    public String renderTemplateAsString(String templateFile, VelocityContext ctx) {
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
        Template t = ve.getTemplate("template/" + templateFile, "UTF-8");
        StringWriter sw = new StringWriter();
        t.merge(ctx, sw);
        return sw.toString();
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

}
