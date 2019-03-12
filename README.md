# mybatis-generator

# **以知BUG**

无

# **历史BUG**
~~1.oracle数据库生成NUMBER类型的字段的时候会映射成BigDecimal类型~~

            

#### 2010-03-12更新
    1）修复BUG
        a) 修复插件com.dgbiztech.generator.plugin.batchplugin.BatchInsertPlugin生成的sql语句错误
        b) 启用JavaTypeResolver，解决oracle数据库生成NUMBER类型的字段会映射成BigDecimal类型
        <javaTypeResolver type="com.dgbiztech.generator.resolver.BigDecimalJavaTypeResolver">
            <property name ="forceBigDecimals" value ="true"/>
        </javaTypeResolver>

#### 2010-02-25更新
    1）修改功能
        a) com.dgbiztech.generator.plugin.GetPagePlugin插件生成除去udef字段
        b) com.dgbiztech.generator.plugin.GetPagePlugin插件如果有CREATED_DATE字段，自动添加 ORDER BY CREATED_DATE DESC 排序
    2）新增功能
        a）增加模板文件Filter.vm index.vm Table.vm Tools.vm
    
#### 2010-02-19更新   
    1）新增功能
        a) 新增前端部分模板文件add  detail  edit  index
        b) 注解生成添加到get set方法之上,方便写代码的时候查看
    

#### 2010-02-14更新
    1）新增功能
        a) 新增插件com.dgbiztech.generator.plugin.GetPagePlugin是否启用模糊查询
        <property name="likequery" value="true"></property>


#### 2010-02-13更新
    1）修复BUG
        a) 修复插件com.dgbiztech.generator.plugin.batchplugin.BatchUpdatePlugin生成的sql语句缺少符号问题
        b) 修复生成的if节点日期类型的字段不判断空字符串（''）
    2）新增功能
        a) 新增插件com.dgbiztech.generator.plugin.SelectByIdsPlugin根据主键批量查询数据
#### 2010-02-12更新
    1）新增功能
        a) 更新插件com.dgbiztech.generator.plugin.ServiceControllerPlugin支持自定义模块名
           <property name="modular" value="userqwe"></property>
        b) 更新前端model不显示udef字段
        
<div align=center><img alt="abc" width="230" height="230" class="avatar width-full avatar-before-user-status" src="https://avatars1.githubusercontent.com/u/29689362?s=460&amp;v=4"></div>        

