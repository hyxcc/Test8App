package com.hyx.rmiService;

import com.danga.MemCached.MemCachedClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyx.dao.StudentMapper;
import com.hyx.pojo.Student;
import com.hyx.util.MemcachedUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class RmiStudentServiceImpl implements RmiStudentService {
    @Resource
    private StudentMapper studentMapper;
    //定义一个常量key
    private static String STUKEY = "studentList";
    private static Logger logger = LoggerFactory.getLogger(RmiStudentServiceImpl.class);
    @Override
    public List<Student> selectAll() {
        MemCachedClient mcc = MemcachedUtil.getMemCachedClient();
        String key = STUKEY;
        List<Student> stuList = null;
        ObjectMapper mapper = new ObjectMapper();
        //如果缓存没有key，则查询数据库，把值放到缓存中，最后返回数据
        //否则key存在，直接从缓存中取数据
        if(!mcc.keyExists(key)){
            stuList = studentMapper.selectAll();
            MemcachedUtil.set(key,stuList);
        }else{
            stuList = castList(mcc.get(key),Student.class);
        }
        return stuList;
    }

    /**
     * 使用Class.cast做类型转换，将Object转换成List
     * @param obj
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> castList(Object obj, Class<T> clazz)
    {
        List<T> result = new ArrayList<T>();
        if(obj instanceof List<?>)
        {
            for (Object o : (List<?>) obj)
            {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return null;
    }
}
