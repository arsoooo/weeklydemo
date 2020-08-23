package com.bupt317.study.weeklydemo.controller;

import com.bupt317.study.weeklydemo.service.UserService;
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
            // 正确跳转test控制器
            return DataVO.success();
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


}
