package com.hyx.rmiService;

import com.hyx.pojo.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("rmiStudentService")
public interface RmiStudentService {
    List<Student> selectAll();
}
