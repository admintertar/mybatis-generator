package com.dgbiztech.generator.plugin;

import com.dgbiztech.generator.config.TemplateConfig;
import com.dgbiztech.generator.entity.ConfigWrapper;
import com.dgbiztech.generator.entity.Table;
import com.dgbiztech.generator.utils.FileUtil;
import com.dgbiztech.generator.utils.VelocityInfoUtils;
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
import java.util.*;

public class ServiceControllerPlugin extends PluginAdapter {

    private String projectDir = "/Users/ping/repo/repo-git/mybatis-generator/mybatis-generator";
    private String basePackage = "com.dgbiztech";

    Logger log = LoggerFactory.getLogger(ServiceControllerPlugin.class);

    public ServiceControllerPlugin(){
        projectDir = properties.getProperty("projectDir",System.getProperty("user.dir"));
        basePackage = properties.getProperty("basePackage","com.dgbiztech");
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        log.info(">>>> 开始生成文件...");
        Map<String, String> map = new HashMap<>();
        for (Object o : properties.keySet()) {
            map.put(o.toString(), properties.getProperty(o.toString()));
        }
        //封装表数据
        Table table = new Table(context, introspectedTable, map);
        VelocityInfoUtils.data(properties, table);

        //初始化context
        VelocityContext templateContext = new VelocityContext();
        templateContext.put("table", table);
        log.info("配置文件目录："+System.getProperty("user.dir"));
        InputStream resourceAsStream = null;
        //读取配置文件,如果不存在外部配置文件,那么就使用内部默认配置文件
        try {
            resourceAsStream = new FileInputStream(System.getProperty("user.dir")+"/src/main/resources/config.yml");
        } catch (FileNotFoundException e) {
            log.info("不存在外部config.yml,将使用默认config.yml");
        }
        if (resourceAsStream == null){
            resourceAsStream = this.getClass().getResourceAsStream("/config.yml");
        }
        List<TemplateConfig> configs = new Yaml().loadAs(resourceAsStream, ConfigWrapper.class).getTemplateConfig();

        int i = 0;
        for (TemplateConfig config : configs) {
            String tempFilePath = System.getProperty("user.dir")+"/src/main/resources/template/"+config.getTemplate();
            File file = new File(tempFilePath);
            //如果存在外部模版文件，那么就使用外部模版文件
            if (file.exists()){
                config.setTemplate(tempFilePath);
                config.setFile(true);
            }else{
                log.info("不存在外部模版文件："+config.getTemplate()+",使用默认模版文件。");
            }
            String destPackage = config.getDestPackage()
                    .replace("${basePackage}",basePackage)
                    .replace("${entityName}",table.entityName.toLowerCase());
            //当前文件包名
            templateContext.put("destPackage",destPackage);
            //循环第一个配置的时候就是service接口
            if (i==0){
                table.setInterfacServicePackge(destPackage);
            }
            i++;
            //组装模版
            String content = renderTemplateAsString(config, templateContext);
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
        //针对js文件做小写处理
        if (absPath.indexOf(".js")>0){
            absPath = absPath.toLowerCase();
        }
        log.info("文件路径："+absPath);
        return absPath;
    }

    /**
     * 写入模版文件
     * @param template
     * @param ctx
     * @return
     */
    public String renderTemplateAsString(TemplateConfig template, VelocityContext ctx) {
        VelocityEngine ve = new VelocityEngine();
        Template t = null;
        if (template.isFile()){
            //文件加载方式
            ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, template.getTemplate().substring(0,template.getTemplate().lastIndexOf("/")+1));
            ve.init();
            t = ve.getTemplate(template.getTemplate().substring(template.getTemplate().lastIndexOf("/")+1,template.getTemplate().length()), "UTF-8");
        }else{
            //类路径加载
            ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
            ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            ve.init();
            t = ve.getTemplate("template/" + template.getTemplate(), "UTF-8");
        }

        StringWriter sw = new StringWriter();
        t.merge(ctx, sw);
        return sw.toString();
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

}
