package com.hyx.rmiService;

import com.hyx.pojo.User;
import org.springframework.stereotype.Service;

@Service("rmiUserService")
public interface RmiUserService {
    User findOne(User user);
    int insert (User user);
}
