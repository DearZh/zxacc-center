/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.app.remote;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONObject;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年7月8日 下午12:52:35
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskClientTests {

	@Autowired
	TaskClient taskClient;
	
	@Test
	public void testLoadList(){
		JSONObject json = taskClient.loadList("5a56dff06a16c11cc0aa3dd6");
		System.out.println(json);
	}
}
