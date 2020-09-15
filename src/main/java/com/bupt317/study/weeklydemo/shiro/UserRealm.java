package com.bupt317.study.weeklydemo.shiro;

import com.bupt317.study.weeklydemo.pojo.User;
import com.bupt317.study.weeklydemo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

// 自定义的Realm，设置认证和授权逻辑
public class UserRealm extends AuthorizingRealm {
    // 注入业务
    @Autowired
    private UserService userService;

    // 用户授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
        System.out.println("执行授权逻辑");

        // 对资源进行授权,注意是author不是authen
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 给特定的对象加授权字符串，要和config中一致
        // info.addStringPermission("admin:manager");
        // 到数据库查询perms
        Subject subject = SecurityUtils.getSubject();
        // principal是从认证逻辑里面传来的第一个参数，即user
        User user = (User)subject.getPrincipal();
        // 根据perms授权
        if (user.getPerms() != null)
            // 如果是对应的授权，就给，不是或者没有就进不了
            info.addStringPermission(user.getPerms());

        return info;
    }

    // 用户认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
        System.out.println("执行认证逻辑");

        // 1.判断用户名
        UsernamePasswordToken token = (UsernamePasswordToken)arg0;

        // 从数据库获得user
        User dbUser = userService.getByName(token.getUsername());
        if(null == dbUser){
            // 用户名不存在
            return null;  // UnknownAccountException
        }
        // 盐值转换
        ByteSource salt = ByteSource.Util.bytes(dbUser.getSalt());
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(dbUser, dbUser.getPassword(), salt, "");
        // 2.判断密码，参数放入数据库查到的密码
        // 由于已经在Config里配置了凭证匹配器，这里直接加数据库的pwd、加salt进去判断，自动对输入的pwd加密对比
        return authenticationInfo;

    }
}
