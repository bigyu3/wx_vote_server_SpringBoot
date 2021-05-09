package com.gty.mapper;

import com.alibaba.fastjson.JSONObject;
import com.gty.entity.TblUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author tianyuguo
 * @since 2021-05-07
 */

@Repository
public interface TblUserMapper extends BaseMapper<TblUser> {

    /*
     * 获取投票列表
     * */
    @Select("select v.id,v.title,v.starttime,v.endtime,v.isanonymous,v.image,v.end,u.image logo,u.name,u.gender from tbl_vote v left join tbl_user u on v.userid=u.id  where v.vshow = 2 order by  v.id desc")
//    @Select("select v.id,v.title,v.starttime,v.endtime,v.isanonymous,v.image,v.end,u.image logo,u.name,u.gender from tbl_vote v left join tbl_user u on v.userid=u.id  where v.vshow = 1 order by  v.id desc")
    List<JSONObject> getVoteList();

    @Select("select v.id,v.title,v.starttime,v.endtime,v.isanonymous,v.image,v.end,u.image logo,u.name,u.gender from tbl_vote v left join tbl_user u on v.userid=u.id  where v.vshow = 1 order by  v.id desc")
    List<JSONObject> getVoteListNew();

    /*
     * 更新投票截至时间
     * */
    @Update("update tbl_vote set end = 2 where id = #{$id}")
    int updateVoteEnd(String $id);

    @Select("select id,vdesc from tbl_voteoption where voteid =#{$id}")
    List<JSONObject> get_optiondetail(String $id);

    @Select("select u.name from tbl_user u left join tbl_selectdetail s on u.id = s.userid where s.optionid = #{_id}")
    List<String> get_optiondetail_Select2(String _id);
//    List<JSONObject> get_optiondetail_Select2(String _id);

    @Update("update  tbl_vote set vshow = 2 where id =#{$id}")
    int aduitvote(String $id);

    @Delete("delete from tbl_vote where id = #{$id}")
    int deletevote(String $id);

    //3 联查1 号 获取投票的主题信息
    @Select("select v.id,v.title,v.votedesc,v.votetype,v.isanonymous,v.endtime,v.end,u.image,u.name from tbl_vote v left join tbl_user u on v.userid=u.id where v.id = #{$id}")
    List<JSONObject> getSubjectInfo(String $id);

    //3联查 2号  获取选项信息,被选择多少次
    @Select("SELECT o.id,o.vdesc,COUNT(s.id) sum FROM tbl_voteoption o LEFT JOIN tbl_selectdetail s ON o.id = s.optionid WHERE o.voteid = #{$id} GROUP BY o.id")
    List<JSONObject> getOptionInfo(String $id);

    //3联查 3号 查询做过投票的用户信息
    @Select("select  distinct  u.id,u.name,u.image from tbl_user u left join tbl_selectdetail s  on u.id = s.userid where s.voteid = #{$id}")
    List<JSONObject> getVotedUser(String $id);

    //wx_vote_save
    @Insert("insert into tbl_vote (title,votedesc,votetype,endtime,isanonymous,userid,starttime,image) values(#{$title},#{$votedesc},#{$votetype},#{$endtime},#{$isanonymous},#{userid},#{$date},#{$image})")
    int wx_vote_save(String $title, String $votedesc, String $votetype, String $endtime, String $isanonymous, Integer userid, String $date, String $image);


    //wx_vote_save_FindId
    @Select("select id from tbl_vote where starttime = #{$date}")
    int wx_vote_save_FindId(String $date);

    //wx_vote_save_Option
    @Insert("insert tbl_voteoption(vdesc,voteid) values (#{opValue},#{backId})")
    int wx_vote_save_Option(String opValue, int backId);

    //addoption 保存投票
    @Insert("insert tbl_selectdetail(voteid,userid,optionid) values (#{$voteid},#{$userid},#{$option_})")
    int addoption(String $voteid, String $userid, String $option_);

    //get_vote_list_own 获取投票列表_我发起的
    @Select("select v.id,v.title,v.starttime,v.endtime,v.isanonymous,v.image,v.end,u.image logo,u.name,u.gender from tbl_vote v left join tbl_user u on v.userid=u.id  where userid = #{$id} order by  v.id desc")
    List<JSONObject> get_vote_list_own(String $id);

    //get_vote_list_join 获取投票列表_自己参与
    @Select("select v.id,v.title,v.starttime,v.endtime,v.isanonymous,v.image,v.end,u.image logo,u.name,u.gender from tbl_vote v left join tbl_user u on v.userid=u.id  where v.id in (select distinct voteid from tbl_selectdetail where userid = #{$id} order by  v.id desc)")
    List<JSONObject> get_vote_list_join(String $id);

    //get_vote_list_audit 审核
    @Select("select v.id,v.title,v.starttime,v.endtime,v.isanonymous,v.image,v.end,v.vshow,u.image logo,u.name,u.gender from tbl_vote v left join tbl_user u on v.userid=u.id  where v.vshow = 1")
    List<JSONObject> get_vote_list_audit();
}
