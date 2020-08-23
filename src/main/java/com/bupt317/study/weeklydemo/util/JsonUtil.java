package com.bupt317.study.weeklydemo.util;

import com.alibaba.fastjson.JSON;
import com.bupt317.study.weeklydemo.vo.UserVO;

import java.util.ArrayList;
import java.util.List;

public class JsonUtil {

    /**
     * 前端JSON str —> userVOs
     * 目的是把选中的所有user获得，赋给项目
     * @param jsonStr
     * @return List<UserVO>
     */
    public static List<UserVO> jsonStr2Users(String jsonStr){
        List<UserVO> userVOList = JSON.parseArray(jsonStr, UserVO.class);
        return userVOList;
    }

}
