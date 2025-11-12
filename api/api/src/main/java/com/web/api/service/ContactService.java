package com.web.api.service;

import com.web.api.pojo.Contact;

/**
 * 联系方式服务接口
 * @author Askr-Yggdrasill
 */
public interface ContactService {

    /**
     * 获取联系方式信息
     * @return 联系方式信息
     */
    Contact getContactInfo();

    /**
     * 更新联系方式信息
     * @param contact 联系方式信息
     */
    void updateContactInfo(Contact contact);
}
