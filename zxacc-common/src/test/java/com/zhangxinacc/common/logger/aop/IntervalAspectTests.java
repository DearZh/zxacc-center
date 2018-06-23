/*
 * Power by www.xiaoi.com
 */
package com.zhangxinacc.common.logger.aop;

import org.springframework.stereotype.Service;

import com.zhengxinacc.common.logger.annotation.Interval;


/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月21日 下午6:35:19
 * @version 1.0
 */
@Service
public class IntervalAspectTests {

	@Interval
	public void read(){
		try {
			Thread.currentThread().sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}