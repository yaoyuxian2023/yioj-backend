package com.yupi.yioj.judge.strategy;


import com.yupi.yioj.judge.codesandbox.model.JudgeInfo;

/**
 * 判题策略
 * @author Augus
 */
public interface JudgeStrategy {

    /**
     * 执行判题
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext);
}
