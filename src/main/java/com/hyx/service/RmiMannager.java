package com.hyx.service;

import org.springframework.stereotype.Service;

@Service("rmiMannager")
public interface RmiMannager {
    RmiUserService chooseUserService();
    RmiStudentService chooseStudentService();
}
