package com.yupi.yioj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.yioj.model.dto.question.QuestionQueryRequest;
import com.yupi.yioj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.yupi.yioj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.yupi.yioj.model.entity.Question;
import com.yupi.yioj.model.entity.QuestionSubmit;
import com.yupi.yioj.model.entity.User;
import com.yupi.yioj.model.vo.QuestionSubmitVO;
import com.yupi.yioj.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author Augus
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2024-07-20 07:45:20
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {

    /**
     * 答案提交
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    /**
     * 题目答案提交（内部服务）
     *
     * @param userId
     * @param questionId
     * @return
     */
    int doQuestionSubmitInner(long userId, long questionId);

    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<Question> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 获取题目封装
     *
     * @param questionSubmit
     * @param request
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, HttpServletRequest request);

    /**
     * 分页获取题目封装
     *
     * @param questionPage
     * @param request
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionPage, HttpServletRequest request);

}
