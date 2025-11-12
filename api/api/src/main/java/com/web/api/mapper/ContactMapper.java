package com.web.api.mapper;

import com.web.api.pojo.Contact;
import org.apache.ibatis.annotations.Mapper;

/**
 * 联系方式数据交互
 * @author Askr-Yggdrasill
 */
@Mapper
public interface ContactMapper {

    Contact getContactInfo();

    void updateContactInfo(Contact contact);
}
