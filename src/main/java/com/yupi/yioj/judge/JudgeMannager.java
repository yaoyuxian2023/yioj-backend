package com.yupi.yioj.judge;

import com.yupi.yioj.judge.strategy.JudgeContext;
import com.yupi.yioj.model.dto.questionsubmit.JudgeInfo;
import com.yupi.yioj.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * 判题管理（简化调用）
 */
@Service
public class JudgeMannager {

    JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();

        return null;

    }
}
