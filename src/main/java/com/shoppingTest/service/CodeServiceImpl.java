package com.shoppingTest.service;

import com.shoppingTest.domain.CodeDetail;
import com.shoppingTest.domain.CodeGroup;
import com.shoppingTest.domain.CodeLabelValue;
import com.shoppingTest.repository.CodeDetailRepository;
import com.shoppingTest.repository.CodeGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CodeServiceImpl implements CodeService{
    private final CodeGroupRepository repository;

    private final CodeDetailRepository codeDetailRepository;

    @Override
    public List<CodeLabelValue> getGroupCodeList() throws Exception {
        List<CodeGroup> codeGroups = repository.findAll(Sort.by(Sort.Direction.ASC,"groupCode"));
        List<CodeLabelValue> codeGroupList = new ArrayList<CodeLabelValue>();

        for(CodeGroup codeGroup : codeGroups){
            codeGroupList.add(new CodeLabelValue(codeGroup.getGroupCode(), codeGroup.getGroupName()));
        }

        return codeGroupList;
    }

    @Override
    public List<CodeLabelValue> getCodeList(String groupCode) throws Exception{
        List<CodeDetail> codeDetails = codeDetailRepository.getCodeList(groupCode);

        List<CodeLabelValue> codeList = new ArrayList<CodeLabelValue>();

        for(CodeDetail codeDetail : codeDetails){
            codeList.add(new CodeLabelValue(codeDetail.getCodeValue(), codeDetail.getCodeName()));
        }

        return codeList;
    }
}
