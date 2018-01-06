/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.config;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.eastrobot.log.annotation.Interval;
import com.zhengxinacc.exam.question.domain.QuestionCate;
import com.zhengxinacc.exam.question.repository.QuestionCateRepository;
import com.zhengxinacc.util.SystemKeys;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年1月3日 上午9:55:12
 * @version 1.0
 */
@Component
@Order(1)
public class StartupRunner implements CommandLineRunner {

	@Resource
	private QuestionCateRepository questionCateRepository;
	
	@Override
	@Interval
	public void run(String... arg0) throws Exception {
		//初始化数据
		//题目分类为空时，创建相应的题目分类
		List<QuestionCate> list = questionCateRepository.findAll();
		if (list.size()==0){
			QuestionCate questionCate = saveQuestionCate("初级会计实务", "ROOT");
			saveQuestionCate("单选题", questionCate.getId());
			saveQuestionCate("多选题", questionCate.getId());
			saveQuestionCate("判断题", questionCate.getId());
			
			questionCate = saveQuestionCate("经济法基础", "ROOT");
			saveQuestionCate("单选题", questionCate.getId());
			saveQuestionCate("多选题", questionCate.getId());
			saveQuestionCate("判断题", questionCate.getId());
			
		}
	}
	/**
	 * 保存 QuestionCate
	 * @author eko.zhan at 2018年1月3日 上午10:11:35
	 * @param name
	 * @param pid
	 * @return
	 */
	private QuestionCate saveQuestionCate(String name, String pid){
		QuestionCate cate = new QuestionCate();
		cate.setName(name);
		cate.setCreateUser(SystemKeys.ANONYMOUS);
		cate.setModifyUser(SystemKeys.ANONYMOUS);
		cate.setPid(pid);
		return questionCateRepository.save(cate);
	}

}
