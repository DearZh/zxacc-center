/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.exam.paper.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年1月3日 下午9:22:29
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PaperQuestion {
	private String id;	//对应问题主键id
	private String name;
	private Integer type;
	private Integer order = 0;
	private Double score;
}
