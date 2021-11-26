package com.hereWeGo.portal.service.Impl;

import com.hereWeGo.common.pojo.AdminWithBLOBs;
import com.hereWeGo.common.result.BaseResult;
import com.hereWeGo.common.utils.Md5Util;
import com.hereWeGo.common.utils.RandomUtil;
import com.hereWeGo.portal.mapper.AdminMapper;
import com.hereWeGo.portal.service.UserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private  RabbitTemplate rabbitTemplate;

    /*
    * 用户注册
    * */
    @Override
    public BaseResult saveUser(AdminWithBLOBs admin) {
        //生成言
        String salt = RandomUtil.getRandom1();
        admin.setEcSalt(salt);
        //根据salt加密解密
        String password = Md5Util.getMd5WithSalt(admin.getPassword(),salt);
        admin.setPassword(password);
        //设置注册时间
        admin.setAddTime((int) LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")));
        int result = adminMapper.insertSelective(admin);
        //使用rabbitmq做消息队列前
        //return result>0 ? BaseResult.success() : BaseResult.error();

        //使用rabbitmq后
        if(result>0){
            rabbitTemplate.convertAndSend("sameExchange","register.sms",admin.getEmail());
            System.out.println("发送的消息");
            return BaseResult.success();
        }
        return BaseResult.error();
    }
}
