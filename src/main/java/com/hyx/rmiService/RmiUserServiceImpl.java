package com.hyx.rmiService;

import com.hyx.dao.UserMapper;
import com.hyx.pojo.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class RmiUserServiceImpl implements RmiUserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public User findOne(User user) {
        return userMapper.findOne(user);
    }

    @Override
    public int insert(User user) {
        return userMapper.insert(user);
    }
}
