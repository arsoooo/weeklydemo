package com.bupt317.study.weeklydemo.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.bupt317.study.weeklydemo.pojo.User;
import com.bupt317.study.weeklydemo.service.UserService;
import com.bupt317.study.weeklydemo.util.UserUtil;
import com.bupt317.study.weeklydemo.util.WordUtil;
import com.bupt317.study.weeklydemo.vo.DataVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class userController {

    @Autowired
    private UserService userService;

    /**
     * 对输入的账户密码登录验证
     * */
    @PostMapping("/login")
    public Object login(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "password") String password
//            @RequestBody User user,
//            Model model
    ){
        System.out.println(name+password);
        // 1.SecurityUtils获得subject
        Subject subject = SecurityUtils.getSubject();
        // 2.UsernamePasswordToken存user进去
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);
        try {
            // 3.执行登录方法subject.login(token) -> 执行认证逻辑
            subject.login(token);
            // 正确跳转test控制器,把对象取到，id传过去
            return DataVO.success(((User)subject.getPrincipal()).getId());
        }catch(UnknownAccountException e){
//            model.addAttribute("msg", "用户名不存在！");
            // 直接跳html，有m要传
            System.out.println("用户名不存在！");
            return DataVO.fail("用户名不存在！");
        }catch (IncorrectCredentialsException e) {
//            model.addAttribute("msg", "密码不正确！");
//            model.addAttribute("name", name);
            System.out.println("密码不正确！");
            return DataVO.fail("密码不正确！");
        }
    }

    /**
     * 查询所有UserVO -> DataVO.data
     */
    @GetMapping("/users")
    public DataVO findUsers(){
        return userService.findData();
    }

    /**
     * 根据ID查询某个用户的User -> DataVO.data
     */
    @GetMapping("/users/{uid}")
    public DataVO getUserById(@PathVariable("uid") int uid){
        return DataVO.success(userService.getUserVOById(uid));
    }

    /**
     * admin编辑用户提交后 更新用户信息
     */
    @PutMapping("/users/{uid}")
    public DataVO updateUserByAdmin(
            User user,
            @PathVariable("uid") int uid,
            @RequestParam("perm") String perm
    ){
        // 分配权限
        user.setPerms(UserUtil.desc2Perm(perm));
        // dbUser是原始的user,然后把新user的内容copy一份给他<使用hutool的util>
        User dbUser = userService.getById(uid);
        BeanUtil.copyProperties(user, dbUser,
                true, CopyOptions.create().setIgnoreNullValue(true));
        // 更新用户信息
        userService.updateUser(dbUser);
        // 更新信息表
        WordUtil.writeDoc(dbUser);
        return DataVO.success();
    }

    /**
     * 获取某个项目没有参加的用户
     * 目的是生成可以增加的用户表
     */
    @GetMapping("/users/products/add/{pid}")
    public DataVO findOtherUsersByPid(@PathVariable("pid") int pid){
        return DataVO.success(userService.findOtherUsersByPid(pid));
    }


    /**
     * 获取某个项目已经参加的用户
     * 目的是生成可以删除的用户表
     */
    @GetMapping("/users/products/del/{pid}")
    public DataVO findUsersByPid(@PathVariable("pid") int pid){
        return DataVO.success(userService.findUsersByPid(pid));
    }
}
