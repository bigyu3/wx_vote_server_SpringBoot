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
public class TblSelectdetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 投票id
     */
    private Integer voteid;

    /**
     * 投票用户id
     */
    private Integer userid;

    /**
     * 用户选择的选项的id
     */
    private Integer optionid;


}
