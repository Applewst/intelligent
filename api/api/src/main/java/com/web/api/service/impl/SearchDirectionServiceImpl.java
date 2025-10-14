package com.web.api.service.impl;

import com.web.api.mapper.SearchDirectionMapper;
import com.web.api.pojo.Result;
import com.web.api.service.SearchDirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchDirectionServiceImpl implements SearchDirectionService {

    @Autowired
    private SearchDirectionMapper searchDirectionMapper;

    @Override
    public Result searchDirection() {
        return Result.success(searchDirectionMapper.selectSearchDirection());
    }

}
