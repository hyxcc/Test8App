package com.hyx.service;

import com.hyx.pojo.User;
import org.springframework.stereotype.Service;

@Service
public interface RmiUserService {
    User findOne(User user);
    int insert (User user);
}