package com.chenlong.user.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//表示这个类是配置类
@Configuration
public class DruidConfig {

    //交给容器管理
    @Bean
    //容器按照配置文件中spring.datasource的属性实例化这个对象
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource getDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        return dataSource;
    }

    @Bean
    public ServletRegistrationBean showViewServlet(){
        //这里是创建一个连接池页面  和  访问路径
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        //设置可以访问这个页面的属性:
        Map map = new HashMap();
        map.put("loginUsername", "admin");  //用户名
        map.put("loginPassword", "123123"); //密码
        map.put("allow", "");               //默认是允许所有的id访问
        //map.put("deny", "192.168.188.133");                //禁止对应的ip访问
        bean.setInitParameters(map);
        return bean;
    }

    @Bean
    //配置登录页面  过滤器
    public FilterRegistrationBean showFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        Map<String, String> map = new HashMap();
        map.put("exclusions","*.js,*.css,/druid/*"); //不拦截的数据
        bean.setInitParameters(map);
        bean.setUrlPatterns(Arrays.asList("/*")); //拦截所有路径
        return bean;

    }

}
