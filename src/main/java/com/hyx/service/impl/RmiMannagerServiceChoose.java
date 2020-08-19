package com.hyx.service.impl;

import com.hyx.service.RmiMannager;
import com.hyx.service.RmiStudentService;
import com.hyx.service.RmiUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RmiMannagerServiceChoose extends ChooseService implements RmiMannager {

    @Autowired
    @Qualifier("rmiStudentClientA")
    private RmiStudentService rmiStudentClientA;
    @Autowired
    @Qualifier("rmiStudentClientB")
    private RmiStudentService rmiStudentClientB;
    @Autowired
    @Qualifier("rmiUserClientA")
    private RmiUserService rmiUserClientA;
    @Autowired
    @Qualifier("rmiUserClientB")
    private RmiUserService rmiUserClientB;

    @Override
    public RmiUserService chooseUserService() {
        return (RmiUserService)chooseService(rmiUserClientA, rmiUserClientB);
    }

    @Override
    public RmiStudentService chooseStudentService() {
        return (RmiStudentService)chooseService(rmiStudentClientA, rmiStudentClientB);
    }
}
