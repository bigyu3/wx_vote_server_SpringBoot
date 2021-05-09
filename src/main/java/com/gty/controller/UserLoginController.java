package com.gty.controller;


import com.alibaba.fastjson.JSONObject;
import com.gty.entity.TblUser;
import com.gty.mapper.TblUserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
public class UserLoginController {


    @Autowired
    private TblUserMapper tblUserMapper;
    /*
    * 获取投票列表
    * */
    @RequestMapping(value = "/wx_graduation_voteforyou/getVoteList")
    public JSONObject wxLogin(HttpServletRequest request, HttpServletResponse response) {

        String scene = request.getParameter("scene");
        String code = request.getParameter("code");
        String userinfo = request.getParameter("userinfo");
        List<JSONObject> voteList = tblUserMapper.getVoteList();

        JSONObject res = new JSONObject();
        res.put("items", voteList);

     /*   System.out.println("voteList = " + voteList);
        System.out.println("scene = " + scene);*/
        return res;
    }
}
