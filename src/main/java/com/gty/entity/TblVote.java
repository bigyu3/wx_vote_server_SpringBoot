package com.gty.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author tianyuguo
 * @since 2021-05-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TblVote implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String title;

    private String votedesc;

    /**
     * 投票类型 选项数
     */
    private Integer votetype;

    private LocalDateTime starttime;

    private String endtime;

    private Integer isanonymous;

    private String image;

    /**
     * 发起投票用户的id
     */
    private Integer userid;

    /**
     * 结束 2 ||没结素 1
     */
    private Integer end;

    /**
     * 是否显示 2审核过 显示
     */
    private Integer vshow;


}
