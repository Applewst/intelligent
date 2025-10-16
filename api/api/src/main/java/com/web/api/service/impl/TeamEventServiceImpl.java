package com.web.api.service.impl;

import com.web.api.mapper.TeamEventMapper;
import com.web.api.pojo.Result;
import com.web.api.service.TeamEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamEventServiceImpl implements TeamEventService {
    @Autowired
    private TeamEventMapper teamEventMapper;

    @Override
    public Result getTeamEvent() {
        return Result.success(teamEventMapper.getTeamEvent());
    }
}
