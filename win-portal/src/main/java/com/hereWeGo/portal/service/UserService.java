package com.hereWeGo.portal.service;

import com.hereWeGo.common.pojo.AdminWithBLOBs;
import com.hereWeGo.common.result.BaseResult;

public interface UserService {
    BaseResult saveUser(AdminWithBLOBs admin);
}
