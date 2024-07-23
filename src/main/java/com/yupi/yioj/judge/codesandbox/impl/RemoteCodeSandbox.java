package com.yupi.yioj.judge.codesandbox.impl;

import com.yupi.yioj.judge.codesandbox.CodeSandbox;
import com.yupi.yioj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yupi.yioj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 远程代码沙箱（实际调用接口的沙箱）
 */
public class RemoteCodeSandbox implements CodeSandbox {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程沙箱");
        return null;
    }
}
