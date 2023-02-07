package com.shoppingTest.service;

import com.shoppingTest.domain.CodeGroup;

import java.util.List;

public interface CodeGroupService {
    public void register(CodeGroup codeGroup) throws Exception;

    // 상세 화면
    public CodeGroup read(String groupCode) throws Exception;

    // 수정 처리
    public void modify(CodeGroup codeGroup) throws Exception;

    // 삭제 처리
    public void remove(String groupCode) throws Exception;

    // 목록 화면
    public List<CodeGroup> list() throws Exception;
}
