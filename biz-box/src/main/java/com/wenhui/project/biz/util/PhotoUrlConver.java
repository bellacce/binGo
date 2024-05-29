package com.wenhui.project.biz.util;/*
 @author 天赋吉运-bms
 @DESCRIPTION 图片地址转换为json格式
 @create 2023/3/19
*/

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wenhui.project.web.vo.ImageBaseVo;

public class PhotoUrlConver {

    public static String getPhotoUrlJson(String url){
        JSONArray objects = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name","封面");
        jsonObject.put("url",url);
        objects.add(jsonObject);
        return objects.toJSONString();
    }

    public static String getPhotoUrl(String url){

        ImageBaseVo imageBaseVo = JSONArray.parseArray(url, ImageBaseVo.class).get(0);
        return imageBaseVo.getUrl();
    }
}
