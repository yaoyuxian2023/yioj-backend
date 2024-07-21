package com.yupi.yioj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.yioj.model.entity.QuestionSubmit;
import com.yupi.yioj.model.entity.User;

/**
* @author Augus
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2024-07-20 07:45:20
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {

    /**
     * 答案提交
     *
     * @param questionId
     * @param loginUser
     * @return
     */
    int doQuestionSubmit(long questionId, User loginUser);

    /**
     * 题目答案提交（内部服务）
     *
     * @param userId
     * @param questionId
     * @return
     */
    int doQuestionSubmitInner(long userId, long questionId);

}
