package com.bupt317.study.weeklydemo.util;

import com.alibaba.fastjson.JSON;
import com.bupt317.study.weeklydemo.pojo.User;
import com.bupt317.study.weeklydemo.vo.UserVO;

import java.util.ArrayList;
import java.util.List;

public class JsonUtil {

    /**
     * 前端JSON str —> userVOList
     * 只支持"[]"包含的数组形式的json
     * @param jsonStr
     * @return List<UserVO>
     */
    public static List<UserVO> jsonStr2UserVOList(String jsonStr){
        List<UserVO> userVOList = JSON.parseArray(jsonStr, UserVO.class);
        return userVOList;
    }

//    /**
//     * 前端JSON str —> userVO
//     * 注意！！！只支持"[]"包含的数组形式的json
//     * "{}"的格式请使用 JSON.parseObject(jsonStr, UserVO.class)
//     * @param jsonStr
//     * @return UserVO
//     */
//    public static UserVO jsonStr2UserVO(String jsonStr){
//        List<UserVO> userVOList = jsonStr2UserVOList(jsonStr);
//        UserVO userVO = new UserVO();
//        for (UserVO item : userVOList) { userVO = item; }
//        return userVO;
//    }


}
