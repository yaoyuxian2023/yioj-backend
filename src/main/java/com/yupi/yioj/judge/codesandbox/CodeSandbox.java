package com.yupi.yioj.judge.codesandbox;

import com.yupi.yioj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yupi.yioj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 代码沙箱接口定义
 */
public interface CodeSandbox {

    /**
     * 执行代码
     *
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
