package com.bupt317.study.weeklydemo.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig{

    private String hashAlgorithmName = "md5";// 加密方式
    private int hashIterations = 2;// 散列次数

//  ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 设置安全管理器 - SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 添加Shiro内置过滤器
        /*
        * anon：无需认证访问
        * authc：认证才可以访问
        * user：如果使用rememberMe可以访问
        * perms：得到资源权限可以访问
        * role：得到角色权限可以访问
        * */
        Map<String, String> filerMap = new LinkedHashMap<>();
        // （资源路径，配置）
//        filerMap.put("/add", "authc");
//        filerMap.put("/update", "authc");
        // 主页面test.html
        filerMap.put("/test", "anon");
        // 登录页面login.html
        filerMap.put("/login", "anon");
        // 授权过滤器
        filerMap.put("/adminManager", "perms[admin:manager]");
        // 退出登录按钮
        filerMap.put("/logout", "logout");
        // 拦截其他页面
        filerMap.put("/**", "authc");

        // 设置未授权的页面
        shiroFilterFactoryBean.setUnauthorizedUrl("noAuthor");

        // 拦截后，跳转到该页面
        shiroFilterFactoryBean.setLoginUrl("/toLogin");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filerMap);

        return shiroFilterFactoryBean;
    }


//	DefaultWebSecurityManager
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 关联realm
        securityManager.setRealm(userRealm);
        return securityManager;
    }


//	Realm --- 自定义类extends AuthorizingRealm
    @Bean(name = "userRealm")
    public UserRealm getRealm(@Qualifier("credentialsMatcher")CredentialsMatcher credentialsMatcher){
        UserRealm userRealm = new UserRealm();
        // 注入凭证匹配器
        userRealm.setCredentialsMatcher(credentialsMatcher);
        return userRealm;
    }

//  声明凭证匹配器
    @Bean("credentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        // 设置加密方法和迭代次数
        matcher.setHashAlgorithmName(hashAlgorithmName);
        matcher.setHashIterations(hashIterations);
        return matcher;
    }


//    配置ShiroDialect，用于thymeleaf和shiro标签的使用
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }
}
