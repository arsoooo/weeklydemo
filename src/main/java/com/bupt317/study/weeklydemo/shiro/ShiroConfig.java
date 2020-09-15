package com.bupt317.study.weeklydemo.shiro;

import com.bupt317.study.weeklydemo.config.StaticParams;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    //  ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
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
        //静态资源
//        filerMap.put("/../webapp/**", "anon");
        filerMap.put("/css/**", "anon");  // 注：每个都设置也可以
        filerMap.put("/img/**", "anon");
        filerMap.put("/js/**", "anon");
        filerMap.put("/layer/**", "anon");
        filerMap.put("/layui/**", "anon");

         // 主页面
//        filerMap.put("/home", "anon");
        // 登录页面(管理员和普通用户都跳转至此)
        filerMap.put("/teacherLogin", "anon");  // 异步验证账户密码
        filerMap.put("/studentLogin", "anon");  // 异步验证账户密码
        filerMap.put("/toRegister", "anon");  // 跳注册界面
        filerMap.put("/images", "anon");  // 上传注册头像
        filerMap.put("/register", "anon"); // 异步上传注册信息

        // 授权过滤器(到底是文件夹还是方法名？ - 可能是都拦截)
        // 授权过滤器 - 换成了包含admin的所有控制器跳转
        filerMap.put("/admin*", "perms[" + StaticParams.ADMIN_PERMS + "]"); // 管理员的网页跳转
        filerMap.put("/admin/**", "perms[" + StaticParams.ADMIN_PERMS + "]"); // 管理员的网页异步获取json
        filerMap.put("/student*", "perms[" + StaticParams.USER_PERMS + "]"); // 学生的网页跳转
        // 学生页面异步获取json没有对管理员做封锁
        // 退出登录按钮
        filerMap.put("/logout", "logout");  // 跳登出界面
        filerMap.put("/**", "authc");// 拦截其他页面（需要认证）

        // 临时
//        filerMap.put("/**", "anon");
//        filerMap.put("/**", "authc");

        // 认证后但是未授权跳转的页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/noAuthor");  // 跳无权限页面
        // 无认证，拦截后，跳转到该页面
        shiroFilterFactoryBean.setLoginUrl("/toLogin");  // 跳登录界面

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filerMap);
        return shiroFilterFactoryBean;
    }


    //	DefaultWebSecurityManager
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 关联realm
        securityManager.setRealm(userRealm);
        return securityManager;
    }


    //	Realm --- 自定义类extends AuthorizingRealm
    @Bean(name = "userRealm")
    public UserRealm getRealm(@Qualifier("credentialsMatcher") CredentialsMatcher credentialsMatcher) {
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
        matcher.setHashAlgorithmName(StaticParams.HASH_ALGORITHM_NAME);
        matcher.setHashIterations(StaticParams.HASH_ITERATIONS);
        return matcher;
    }


//    配置ShiroDialect，用于thymeleaf和shiro标签的使用
//    @Bean
//    public ShiroDialect getShiroDialect(){
//        return new ShiroDialect();
//    }
}
