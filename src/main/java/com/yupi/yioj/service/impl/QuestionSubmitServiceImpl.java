package com.yupi.yioj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.yioj.common.ErrorCode;
import com.yupi.yioj.exception.BusinessException;
import com.yupi.yioj.model.entity.Post;
import com.yupi.yioj.model.entity.QuestionSubmit;
import com.yupi.yioj.model.entity.QuestionSubmit;
import com.yupi.yioj.mapper.QuestionSubmitMapper;
import com.yupi.yioj.model.entity.User;
import com.yupi.yioj.service.PostService;
import com.yupi.yioj.service.QuestionSubmitService;
import com.yupi.yioj.service.QuestionSubmitService;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author Augus
* @description 针对表【question_submit(题目提交)】的数据库操作Service实现
* @createDate 2024-07-20 07:45:20
*/
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
    implements QuestionSubmitService{

    @Resource
    private PostService postService;

    /**
     * 点赞
     *
     * @param questionId
     * @param loginUser
     * @return
     */
    @Override
    public int doQuestionSubmit(long questionId, User loginUser) {
        // 判断实体是否存在，根据类别获取实体
        Post post = postService.getById(questionId);
        if (post == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 是否已点赞
        long userId = loginUser.getId();
        // 每个用户串行点赞
        // 锁必须要包裹住事务方法
        QuestionSubmitService postThumbService = (QuestionSubmitService) AopContext.currentProxy();
        synchronized (String.valueOf(userId).intern()) {
            return postThumbService.doQuestionSubmitInner(userId, questionId);
        }
    }

    /**
     * 封装了事务的方法
     *
     * @param userId
     * @param questionId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int doQuestionSubmitInner(long userId, long questionId) {
        QuestionSubmit postThumb = new QuestionSubmit();
        postThumb.setUserId(userId);
        postThumb.setQuestionId(questionId);
        QueryWrapper<QuestionSubmit> thumbQueryWrapper = new QueryWrapper<>(postThumb);
        QuestionSubmit oldQuestionSubmit = this.getOne(thumbQueryWrapper);
        boolean result;
        // 已点赞
        if (oldQuestionSubmit != null) {
            result = this.remove(thumbQueryWrapper);
            if (result) {
                // 点赞数 - 1
                result = postService.update()
                        .eq("id", questionId)
                        .gt("thumbNum", 0)
                        .setSql("thumbNum = thumbNum - 1")
                        .update();
                return result ? -1 : 0;
            } else {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        } else {
            // 未点赞
            result = this.save(postThumb);
            if (result) {
                // 点赞数 + 1
                result = postService.update()
                        .eq("id", questionId)
                        .setSql("thumbNum = thumbNum + 1")
                        .update();
                return result ? 1 : 0;
            } else {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        }
    }

}




