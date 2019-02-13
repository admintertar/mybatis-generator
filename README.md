# mybatis-generator

#### 2010-02-13更新
    1) 修复插件com.dgbiztech.generator.plugin.batchplugin.BatchUpdatePlugin
       生成的sql语句缺少符号问题
    2) 修复生成的if节点日期类型的字段不判断空字符串（''）

#### 2010-02-12更新
    1）更新插件com.dgbiztech.generator.plugin.ServiceControllerPlugin
       支持自定义模块名
       <property name="modular" value="userqwe"></property>
    
    2）更新前端model不显示udef字段