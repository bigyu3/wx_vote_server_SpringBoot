package com.gty.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gty.mapper.TblUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
public class PhpConvertController {


    @Autowired
    private TblUserMapper tblUserMapper;

    /*
     * 获取投票列表
     * */
    @RequestMapping(value = "/wx_graduation_voteforyou")
    public JSONObject phpAllIn(HttpServletRequest request, HttpServletResponse response) {

        String $req = request.getParameter("scene");
        String $id = request.getParameter("id");

        JSONObject resFinal = new JSONObject();
        if ("get_vote_list".equals($req)) {//获取投票列表
            List<JSONObject> voteList = tblUserMapper.getVoteList();
            resFinal.put("items", voteList);
        } else if ("get_vote_list_new".equals($req)) {//获取最新投票列表
            List<JSONObject> voteList = tblUserMapper.getVoteListNew();
            resFinal.put("items", voteList);

        } else if ("endvote".equals($req)) {
            //获取投票列表的最终事件 vote表 end 为1 就改为2
            int i = tblUserMapper.updateVoteEnd($id);
            if (i > 0) {
                System.out.println("获取成功");
            }
        } else if ("get_optiondetail".equals($req)) {

            System.out.println("$id = " + $id);
            //先把基于此投票的选项查出来 //ty_5-9_0:27
            List<JSONObject> $options = tblUserMapper.get_optiondetail($id);
            System.out.println("$options = " + $options);
            //遍历查出每个选项+选择用户
            List<JSONObject> optiondetailResult = new ArrayList<>();
            for (JSONObject opt : $options) {
//                optiondetailResult.add(opt);

                String _id = opt.getString("id");
//                List<JSONObject> selectDeatilUser = tblUserMapper.get_optiondetail_Select2(_id);
                List<String> selectDeatilUser = tblUserMapper.get_optiondetail_Select2(_id);

                opt.put("name", selectDeatilUser);
//                optiondetailResult.addAll(selectDeatilUser);

             /*   for (JSONObject jsonObject : optiondetail_select2) {
                }*/

                optiondetailResult.add(opt);
            }


            System.out.println("optiondetailResult = " + optiondetailResult);
            resFinal.put("optiondetailResult", optiondetailResult);
            
          /*  $result = array();
            foreach($options as $opt){
                $res = array();
                $res[] = $opt;
                $sql = "select u.name from tbl_user u left join tbl_selectdetail s on u.id = s.userid where s.optionid = " . $opt['id'];
                //echo $sql;
                $res[] = $mysql->execute_sql($sql);

                $result[] = $res;
            }
            echo json_encode($result);*/
        } else if ("aduitvote".equals($req)) {
            int aduitvoteRes = tblUserMapper.aduitvote($id);
            if (aduitvoteRes > 0) {
                System.out.println("aduitvoteRes 更新成功了!");
            }

            System.out.println("aduitvote_ok!!!!");


        } else if ("deletevote".equals($req)) {

            int deletevoteRes = tblUserMapper.deletevote($id);
            if (deletevoteRes > 0) {
                System.out.println("deletevote_ok!!!");
            }

        } else if ("get_vote_detail".equals($req)) {
            //flag2
            Map<String, List<JSONObject>> vote_detail_commonMap = get_vote_detail_common($id);
         /*   List<JSONObject> subjectInfo = vote_detail_commonMap.get("subjectInfo");
            List<JSONObject> optionInfo = vote_detail_commonMap.get("optionInfo");
            List<JSONObject> votedUserInfo = vote_detail_commonMap.get("votedUserInfo");
*/
//            resFinal.put("vote_detail_common",vote_detail_commonMap);
            resFinal.put("vote_detail_common", vote_detail_commonMap);

        } else if ("addoption".equals($req)) { //没有修改完成
            //拿到3个参数
            String option = request.getParameter("option");
            String $userid = request.getParameter("userid");
            String $voteid = request.getParameter("voteid");
            //json解码 这里option是一个数组 转为json 卡了半天这里
            JSONArray $option = JSONObject.parseArray(option);
            //把jsonArray转为list
            List<String> $options = $option.toJavaList(String.class);

            for (String $option_ : $options) {
                int addoptionRes = tblUserMapper.addoption($voteid, $userid, $option_);
                if (addoptionRes > 0) {
                    System.out.println("成功!!!");
                }
            }
            //数据跟新
            //flagTy 前端需要返回值还没解决   flag1
            Map<String, List<JSONObject>> vote_detail_common = get_vote_detail_common($voteid);
            resFinal.put("vote_detail_common", vote_detail_common);


        } else if ("wx_vote_save".equals($req)) {
            //获取投票信息
            String votepack = request.getParameter("votepack");
            //flagTy2
            //获取这个JSON里的值
            JSONObject jsonObject = JSONObject.parseObject(votepack);
            // 获取到key为shoppingCartItemList的值
            String $title = jsonObject.getString("title");
            String $votedesc = jsonObject.getString("text");
            String $endtime = jsonObject.getString("endTime");
            String $votetype = jsonObject.getString("voteOptionCount");
            String $isanonymous = jsonObject.getString("anonymous");
            String $image = jsonObject.getString("image");
            Date date = new Date();
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String $date = sdf.format(date);

            System.out.println("$date = " + $date);
            System.out.println("$endtime = " + $endtime);

            //获取用户id  必须是int
            Integer userid = Integer.valueOf(request.getParameter("userid"));


            //插入vote表
            int i = tblUserMapper.wx_vote_save($title, $votedesc, $votetype, $endtime, $isanonymous, userid, $date, $image);
            if (i > 0) {
                System.out.println("插入成功!");
            }


            //查询id返回  id应该返回一个int
            int backId = tblUserMapper.wx_vote_save_FindId($date);
            System.out.println("backId = " + backId);
    /*
            $sql = " select id from tbl_vote where starttime = '".$date. "'";
            $id = $mysql -> execute_sql2($sql);
            //echo $id;*/

            //插入选项  votepack里还有个options?
            String options = jsonObject.getString("options");
            /*
             * options  =  " ["222","23333"]"
             * */


            //拿到jsonObject里的值
            JSONArray optionsValues = jsonObject.getJSONArray("options");
            //JSONArray 转为List
            List<String> opValues = optionsValues.toJavaList(String.class);
            for (String opValue : opValues) {

                int i1 = tblUserMapper.wx_vote_save_Option(opValue, backId);
                if (i1 > 0) {
                    System.out.println("成功插入option");
                }

            }


        } else if ("get_vote_list_own".equals($req)) {
            //ty_5-8_23:19  获取投票列表_我发起的
            List<JSONObject> vote_list_own = tblUserMapper.get_vote_list_own($id);
            System.out.println("vote_list_own = " + vote_list_own);
            resFinal.put("vote_list_own", vote_list_own);


        } else if ("get_vote_list_join".equals($req)) {

            //ty获取投票列表_自己参与
            List<JSONObject> vote_list_join = tblUserMapper.get_vote_list_join($id);
            System.out.println("vote_list_join = " + vote_list_join);
            resFinal.put("vote_list_join", vote_list_join);

        } else if ("get_vote_list_audit".equals($req)) {

            //ty获取投票列表_审核
            List<JSONObject> get_vote_list_audit = tblUserMapper.get_vote_list_audit();
            System.out.println("get_vote_list_audit = " + get_vote_list_audit);
            resFinal.put("get_vote_list_audit", get_vote_list_audit);

        }


        return resFinal;
    }


    public Map<String, List<JSONObject>> get_vote_detail_common(String $id) {
        //合并三个List
        Map<String, List<JSONObject>> resMap = new HashMap<>();


        //获取投票的主题信息
        List<JSONObject> subjectInfo = tblUserMapper.getSubjectInfo($id);
        //获取选项信息,被选择多少次
        List<JSONObject> optionInfo = tblUserMapper.getOptionInfo($id);
        //查询做过投票的用户信息
        List<JSONObject> votedUserInfo = tblUserMapper.getVotedUser($id);
        resMap.put("subjectInfo", subjectInfo);
        resMap.put("optionInfo", optionInfo);
        resMap.put("votedUserInfo", votedUserInfo);

//        resListAll.addAll(subjectInfo);
//        resListAll.addAll(optionInfo);
//        resListAll.addAll(votedUserInfo);

        return resMap;
    }
}
