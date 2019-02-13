# mybatis-generator

#### 2010-02-13更新
    1）修复BUG
        a) 修复插件com.dgbiztech.generator.plugin.batchplugin.BatchUpdatePlugin
           生成的sql语句缺少符号问题
        b) 修复生成的if节点日期类型的字段不判断空字符串（''）
    2）新增功能
        a）新增插件com.dgbiztech.generator.plugin.batchplugin.SelectByIdsPlugin
           根据主键批量查询数据
#### 2010-02-12更新
    1）新增功能
        a) 更新插件com.dgbiztech.generator.plugin.ServiceControllerPlugin支持自定义模块名
           <property name="modular" value="userqwe"></property>
        b) 更新前端model不显示udef字段