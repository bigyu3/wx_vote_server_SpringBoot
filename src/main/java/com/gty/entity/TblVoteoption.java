package com.gty.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

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
public class TblVoteoption implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 选项id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 选项描述
     */
    private String vdesc;

    /**
     * 选项对应的投票id
     */
    private Integer voteid;


}
