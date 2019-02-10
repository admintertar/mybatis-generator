package com.dgbiztech.generator.entity;

import com.dgbiztech.generator.config.TemplateConfig;

import java.util.List;

public class ConfigWrapper {
    private List<TemplateConfig> templateConfig;

    public List<TemplateConfig> getTemplateConfig() {
        return templateConfig;
    }

    public void setTemplateConfig(List<TemplateConfig> templateConfig) {
        this.templateConfig = templateConfig;
    }
}
