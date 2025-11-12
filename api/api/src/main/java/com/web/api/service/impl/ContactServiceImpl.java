package com.web.api.service.impl;

import com.web.api.exception.DatabaseOperationException;
import com.web.api.exception.NoFindException;
import com.web.api.mapper.ContactMapper;
import com.web.api.pojo.Contact;
import com.web.api.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 联系方式服务实现类
 * @author Askr-Yggdrasill
 */
@Slf4j
@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactMapper contactMapper;

    @Override
    public Contact getContactInfo() {
        try {
            return contactMapper.getContactInfo();
        } catch (Exception e) {
            log.error("获取联系方式信息时发生异常", e);
            throw new NoFindException();
        }
    }

    @Override
    public void updateContactInfo(Contact contact) {
        try {
            log.info("更新联系方式信息，信息：{}", contact);
            contactMapper.updateContactInfo(contact);
        } catch (Exception e) {
            log.error("更新联系方式信息时发生异常，信息：{}", contact, e);
            throw new DatabaseOperationException();
        }
    }
}
