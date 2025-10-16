package com.web.api.controller;

import com.web.api.service.impl.ResearchDirectionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 研究方向
 */
@RestController
public class ResearchDirectionController {

    @Autowired
    private ResearchDirectionServiceImpl researchDirectionServiceImpl;

}
