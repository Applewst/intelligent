package com.web.api.controller;

import com.web.api.pojo.Contact;
import com.web.api.pojo.Result;
import com.web.api.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 联系我们控制器
 * @author Askr-Yggdrasill
 */
@RestController
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    /**
     * 获取联系方式信息
     */
    @GetMapping("")
    public Result getContactInfo() {
        return Result.success(contactService.getContactInfo());
    }

    /**
     * 更新联系方式信息
     */
    @PutMapping("")
    public Result updateContactInfo(@RequestBody Contact contact) {
        contactService.updateContactInfo(contact);
        return Result.success();
    }
}
