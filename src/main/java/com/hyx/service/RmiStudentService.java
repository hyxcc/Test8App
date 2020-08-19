package com.hyx.service;

import com.hyx.pojo.Student;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface RmiStudentService {
    List<Student> selectAll();
}
