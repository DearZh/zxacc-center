/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.feedback.web;

import com.alibaba.fastjson.JSONObject;
import com.zhengxinacc.config.BaseController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan>/a>
 * @version 1.0
 * @since 2018-10-30 19:57
 */
@RestController
@RequestMapping("/feedback")
public class FeedbackController extends BaseController {

    /**
     * 保存反馈
     * @return
     */
    @PostMapping("/save")
    public JSONObject save(){
        //TODO save feedback, 情感分析，仅保留表扬评价用于自我陶醉，拒绝脏话
        return writeSuccess();
    }
}
