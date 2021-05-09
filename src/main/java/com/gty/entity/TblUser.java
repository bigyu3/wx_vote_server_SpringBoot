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
public class TblUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    /**
     * 微信openid
     */
    private String openid;

    private String image;

    /**
     * 1 男 2 女
     */
    private Integer gender;


}
