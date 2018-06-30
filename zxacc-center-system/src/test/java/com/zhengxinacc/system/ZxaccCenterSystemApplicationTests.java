package com.zhengxinacc.system;

import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZxaccCenterSystemApplicationTests {

	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext context;

	@Before
	public void setup(){
		//MockMvcBuilders使用构建MockMvc对象   （项目拦截器有效）
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();  
	}

	@Test
	public void contextLoads() {
		try {
			String responseStr = mockMvc.perform(
					post("/user/loadList.do").param("page", "1")
					.param("limit", "10")
					.param("keyword", "")
					).andDo(print())
					.andExpect(status().isOk()).andReturn()
					.getResponse().getContentAsString();
			System.out.println(responseStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
