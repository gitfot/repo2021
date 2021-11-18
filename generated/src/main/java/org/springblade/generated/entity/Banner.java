package org.springblade.generated.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.TenantEntity;

import java.util.Date;

/**
 * (habitant_banner)表实体类
 *
 * @author makejava
 * @since 2021-05-16 02:28:23
 */
@Data
@TableName("habitant_banner")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Banner对象", description = "对象")
public class Banner extends TenantEntity {

	private static final Long serialVersionId = 1L;

	@TableId(value = "主键", type = IdType.ASSIGN_UUID)
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "bannerID")
	private String id;

	@ApiModelProperty(value = "标题")
	private String title;

	@ApiModelProperty(value = "banner类型：1 为一般类型 2为公共类型")
	private Object bannerType;

	@ApiModelProperty(value = "图片地址")
	private String imageUrl;

	@ApiModelProperty(value = "开始时间")
	private Date startTime;

	@ApiModelProperty(value = "结束时间")
	private Date endTime;

	@ApiModelProperty(value = "跳转地址")
	private String targetUrl;

	@ApiModelProperty(value = "跳转地址类型 项目内/项目外")
	private Object targetType;

	@ApiModelProperty(value = "权重，范围在0-10")
	private Integer weight;

}
