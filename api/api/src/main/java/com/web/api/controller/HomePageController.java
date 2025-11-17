package com.web.api.controller;

import com.web.api.pojo.LoginVo;
import com.web.api.pojo.PageQueryDTO;
import com.web.api.service.impl.LoginServiceImpl;
import com.web.api.service.impl.ResearchDirectionServiceImpl;
import com.web.api.service.impl.ScientificDynamicServiceImpl;
import com.web.api.service.impl.TeamDynamicServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.web.api.pojo.Result;

/**
 * 首页内容
 * @author Askr-Yggdrasill
 */
@Slf4j
@RestController
public class HomePageController {

    @Autowired
    private TeamDynamicServiceImpl teamDynamicServiceImpl;

    @Autowired
    private ScientificDynamicServiceImpl scientificDynamicServiceImpl;

    @Autowired
    private ResearchDirectionServiceImpl researchDirectionServiceImpl;

    @Autowired
    private LoginServiceImpl loginServiceimpl;

    /**
     * 最新动态
     */
    @GetMapping("/news")
    public Result news() {
        //返回最新4条动态
        return Result.success(scientificDynamicServiceImpl.newDynamics(4));
    }

    /**
     * 研究方向
     */
    @GetMapping("/projects")
    public Result getProject() {
        PageQueryDTO pageQueryDTO = new PageQueryDTO(1, 5, null);
        return Result.success(researchDirectionServiceImpl.getAllDirections(pageQueryDTO));
    }

    /**
     * 登录
     */
    @GetMapping("/user/login")
    public Result login(LoginVo loginVo) {
        return loginServiceimpl.login(loginVo);
    }

}



















