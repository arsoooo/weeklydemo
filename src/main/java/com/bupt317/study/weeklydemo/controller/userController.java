package com.bupt317.study.weeklydemo.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.bupt317.study.weeklydemo.config.StaticParams;
import com.bupt317.study.weeklydemo.pojo.User;
import com.bupt317.study.weeklydemo.service.UserService;
import com.bupt317.study.weeklydemo.util.ImgUtil;
import com.bupt317.study.weeklydemo.util.UserUtil;
import com.bupt317.study.weeklydemo.util.WordUtil;
import com.bupt317.study.weeklydemo.vo.DataVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
public class userController {

    @Autowired
    private UserService userService;

    //////////////////////// 管理员界面 ////////////////////////

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
            @RequestParam("perm") String perm,
            HttpServletRequest request
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
        WordUtil.writeUserDoc(dbUser, request);
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

    //////////////////////// 用户界面 ////////////////////////

    /**
     * 修改头像
     * 没搞懂为啥要强调file才收得到MultipartFile
     * 而且Put不行
     */
    @PostMapping("/editImg")
    public DataVO editUserImg(@RequestParam("file") MultipartFile image, HttpServletRequest request){
        if(image==null){ return DataVO.fail("请选择一张图片"); }
        User user = userService.getLoginDBUser();
        ImgUtil.saveUserImg(user, image, request);
        return DataVO.success();
    }

    /**
     * 修改用户名 + 简介
     */
    @PutMapping("/names")
    public DataVO editUserName(User user, HttpServletRequest request){
        // 判断用户名是否存在
        if(userService.isNameExist(user.getName())){
            return DataVO.fail("用户名已存在");
        }
        // 获得dbuser确保最新
        User dbUser = userService.getLoginDBUser();
        BeanUtil.copyProperties(user, dbUser,
                true, CopyOptions.create().setIgnoreNullValue(true));
        // 更新信息
        userService.updateUser(dbUser);
        // 更新用户信息表
        WordUtil.writeUserDoc(dbUser, request);
        return DataVO.success();
    }

    /**
     * 修改密码
     */
    @PutMapping("/passwords")
    public DataVO editUserPwd(User user,HttpServletRequest request){
        // 密码加密，并换掉
        User dbUser = userService.getLoginDBUser();  // 获得dbuser确保最新
        String encodedPwd = UserUtil.getEncodedPwd(user.getPassword(), dbUser.getSalt());
        user.setPassword(encodedPwd);
        // copy过去
        BeanUtil.copyProperties(user, dbUser,
                true, CopyOptions.create().setIgnoreNullValue(true));
        // update
        userService.updateUser(dbUser);
        // 更新用户信息表
        WordUtil.writeUserDoc(dbUser, request);
        return DataVO.success();
    }

    /**
     * 修改电话
     */
    @PutMapping("/phones")
    public DataVO editUserPhone(User user, HttpServletRequest request){
        User dbUser = userService.getLoginDBUser();  // 获得dbuser确保最新
        // copy过去
        BeanUtil.copyProperties(user, dbUser,
                true, CopyOptions.create().setIgnoreNullValue(true));
        // update
        userService.updateUser(dbUser);
        // 更新用户信息表
        WordUtil.writeUserDoc(dbUser, request);
        return DataVO.success();
    }

    /**
     * 修改邮箱
     */
    @PutMapping("/emails")
    public DataVO editUserEmail(User user, HttpServletRequest request){
        User dbUser = userService.getLoginDBUser();  // 获得dbuser确保最新
        // copy过去
        BeanUtil.copyProperties(user, dbUser,
                true, CopyOptions.create().setIgnoreNullValue(true));
        // update
        userService.updateUser(dbUser);
        // 更新用户信息表
        WordUtil.writeUserDoc(dbUser, request);
        return DataVO.success();
    }

    /**
     * 注册用户上传的头像 -> tmp.jpg
     */
    @PostMapping("/images")
    public DataVO uploadUserImg(@RequestParam("file") MultipartFile image, HttpServletRequest request){
        if(image==null){ return DataVO.fail("请选择一张图片"); }
        ImgUtil.saveUserImg(null, image, request);  // user给null说明保存为tmp.jpg
        DataVO dataVO = new DataVO(0, image.getOriginalFilename());
        return dataVO;  // 把文件名给过去，前端显示
    }

    /**
     * 用户注册
     */
    @PostMapping("/users")
    public DataVO register(User user, HttpServletRequest request){
        System.out.println("异步获取注册内容");
        // 判断用户名是否存在
        if(userService.isNameExist(user.getName())){
            return DataVO.fail("用户名已存在");
        }
        // 设置权限
        user.setPerms(StaticParams.USER_PERMS);
        // 设置salt、密码加密
        user.setSalt(UserUtil.getNewSalt());
        String inputPwd = user.getPassword();
        user.setPassword(UserUtil.getEncodedPwd(inputPwd, user.getSalt()));
        // 添加到database
        userService.addUser(user);
        // 通过user的uid -> 转换tmp.jpg
        ImgUtil.tmpImg2uidImg(user, request);
        // 新建用户信息表
        WordUtil.writeUserDoc(user, request);

        return DataVO.success();
    }

}
