package com.yupi.yioj.judge.strategy;

import cn.hutool.json.JSONUtil;
import com.yupi.yioj.judge.codesandbox.model.JudgeInfo;
import com.yupi.yioj.model.dto.question.JudgeCase;
import com.yupi.yioj.model.dto.question.JudgeConfig;
import com.yupi.yioj.model.entity.Question;
import com.yupi.yioj.model.enums.JudgeInfoMessageEnum;

import java.util.List;

/**
 * 默认判题策略
 * @author Augus
 */
public class DefaultJudgeStrategy implements JudgeStrategy {

  /**
   * 执行判题
   * @param judgeContext
   * @return
   */
  @Override
  public JudgeInfo doJudge(JudgeContext judgeContext) {
    JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
    Long memory = judgeInfo.getMemory();
    Long time = judgeInfo.getTime();
    List<String> inputList = judgeContext.getInputList();
    List<String> outputList = judgeContext.getOutputList();
    List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
    Question question = judgeContext.getQuestion();
    JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.ACCEPTED;
    // 判题信息响应
    JudgeInfo judgeInfoResponse = new JudgeInfo();
    judgeInfoResponse.setMemory(memory);
    judgeInfoResponse.setTime(time);
    // 先判断沙箱执行的结果输出数量是否和预期输出数量相等
    if (outputList.size() != inputList.size()) {
      judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
      judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
      return judgeInfoResponse;
    }
    // 依次判断每一项输出和与其输出是否相等
    for (int i = 0; i < judgeCaseList.size(); i++) {
      JudgeCase judgeCase = judgeCaseList.get(i);
      if (!judgeCase.getOutput().equals(outputList.get(i))) {
        judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
        judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
        return judgeInfoResponse;
      }
    }
    // 判断题目限制
    String judgeConfigStr = question.getJudgeConfig();
    JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);
    Long needMemoryLimit = judgeConfig.getMemoryLimit();
    Long needTimeLimit = judgeConfig.getTimeLimit();
    if (memory > needMemoryLimit) {
      judgeInfoMessageEnum = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
      judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
    }
    if (time > needTimeLimit) {
      judgeInfoMessageEnum = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
      judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
      return judgeInfoResponse;
    }
    judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
    return judgeInfoResponse;
  }
}
