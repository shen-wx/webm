package com.plat.webm.news.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author swx
 * @since 2020-08-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Successfulcase对象", description="")
@TableName("successfulcase")
public class Successfulcase implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键 UUID")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String memo;

    @ApiModelProperty(value = "成功案例类型")
    private String successfulcaseType;

    @ApiModelProperty(value = "图片")
    private String img;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private String createtime;

}
