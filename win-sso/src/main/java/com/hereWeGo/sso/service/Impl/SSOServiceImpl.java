package com.hereWeGo.sso.service.Impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.hereWeGo.common.pojo.Admin;
import com.hereWeGo.common.pojo.AdminExample;
import com.hereWeGo.common.utils.JsonUtil;
import com.hereWeGo.common.utils.Md5Util;
import com.hereWeGo.common.utils.UUIDUtil;
import com.hereWeGo.sso.mapper.AdminMapper;
import com.hereWeGo.sso.service.SSOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/*
* 单点登录实现类
* */
@Service(interfaceClass = SSOService.class)
@Component
public class SSOServiceImpl implements SSOService {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Value("${user.ticket}")
    private String userTicket;

    /*
     * 登录方法-返回票据
     * */
    @Override
    public String login(Admin admin) {

        //判断参数合法性-健壮性  trim()方法加上
        if (StringUtils.isEmpty(admin.getUserName().trim())){
            System.out.println("用户名为空");
            return null;
        }
        if (StringUtils.isEmpty(admin.getPassword().trim())){
            System.out.println("密码为空");
            return null;
        }

        //通过数据库验证信息，判断用户是否存在
        AdminExample example = new AdminExample();
        example.createCriteria().andUserNameEqualTo(admin.getUserName());
        List<Admin> list = adminMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list) || list.size()>1){
            System.out.println("用户名或密码错误");
        }
        Admin a = list.get(0);
        //判断密码需要进行加迷
        if (!a.getPassword().equals(Md5Util.getMd5WithSalt(admin.getPassword(),a.getEcSalt()))){
            System.out.println("用户或密码错误");
        }

        //用户密码正确-成功返回票据信息
        //生成票据存入redis，页面借助cookie存储票据ticket
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String ticket = UUIDUtil.getUUID();
        valueOperations.set(userTicket+":"+ticket, JsonUtil.object2JsonStr(a),30, TimeUnit.MINUTES);

        return ticket;
    }


    /*
     * 验证票据-返回用户信息
     * */
    @Override
    public Admin validate(String ticket) {
        if (StringUtils.isEmpty(ticket)){
            System.out.println("票据信息不存在");
            return null;
        }
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String adminJson = valueOperations.get(userTicket + ":" + ticket);
        if (StringUtils.isEmpty(adminJson)){
            System.out.println("票据对应用户信息不存在");
            return null;
        }

        return JsonUtil.jsonStr2Object(adminJson,Admin.class);
    }

    /*
    * 用户退出
    * */
    @Override
    public void logout(String ticket) {
        redisTemplate.delete(userTicket+":"+ticket);
    }
}
