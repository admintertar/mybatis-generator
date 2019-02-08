package com.dgbiztech.generator.config;

public class TemplateConfig {

    /**
     * 模板文件名
     */
    private String template;

    /**
     * 目标文件夹
     */
    private String destDir;

    /**
     * 目标包
     */
    private String destPackage;

    /**
     * 目标文件名
     */
    private String destFileName;

    /**
     * 是否外部配置
     */
    private boolean isFile = false;

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getDestDir() {
        return destDir;
    }

    public void setDestDir(String destDir) {
        this.destDir = destDir;
    }

    public String getDestPackage() {
        return destPackage;
    }

    public void setDestPackage(String destPackage) {
        this.destPackage = destPackage;
    }

    public String getDestFileName() {
        return destFileName;
    }

    public void setDestFileName(String destFileName) {
        this.destFileName = destFileName;
    }

    public boolean isFile() {
        return isFile;
    }

    public void setFile(boolean file) {
        isFile = file;
    }
}
