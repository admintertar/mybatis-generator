# mybatis-generator

# **以知BUG**

oracle数据库生成NUMBER类型的字段的时候会映射成BigDecimal类型
即使是配置了

```
    <javaTypeResolver> 
        <property name="forceBigDecimals" value="false" />  
    </javaTypeResolver>
```

依旧会出现  自定义一个Resolver也不行，自定义的Resolver压根就没有实例化过

这似乎是最新版本的一个bug 有空换个低版本试试

#### 临时解决办法

    方法1  ）修改  mybatis-generator-core的版本为1.3.2
            这样就不能使用com.dgbiztech.generator.plugin.ServiceControllerPlugin插件
    方法2  ）手动替换类里面的BigDecimal类型
            XML文件里面的sql语句的映射类型是OK的


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
        a) 修复插件com.dgbiztech.generator.plugin.batchplugin.BatchUpdatePlugin
           生成的sql语句缺少符号问题
        b) 修复生成的if节点日期类型的字段不判断空字符串（''）
    2）新增功能
        a) 新增插件com.dgbiztech.generator.plugin.SelectByIdsPlugin
           根据主键批量查询数据
#### 2010-02-12更新
    1）新增功能
        a) 更新插件com.dgbiztech.generator.plugin.ServiceControllerPlugin支持自定义模块名
           <property name="modular" value="userqwe"></property>
        b) 更新前端model不显示udef字段
        
<div align=center><img alt="abc" width="230" height="230" class="avatar width-full avatar-before-user-status" src="https://avatars1.githubusercontent.com/u/29689362?s=460&amp;v=4"></div>        

