package com.hyx.util;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MemcachedUtil {
    private static MemCachedClient mcc =new MemCachedClient();
    private MemcachedUtil(){

    }

   static{
       String[] servers = {"120.25.222.150:11211"};
       Integer[] weights = {1};

       SockIOPool pool = SockIOPool.getInstance();
       pool.setServers(servers);
       pool.setWeights(weights);
       pool.setInitConn(5);//设置开始时每个cache服务器的可用连接数
       pool.setMinConn(5);//设置每个服务器最少可用连接数
       pool.setMaxConn(250);//设置每个服务器最大可用连接数
       pool.setMaxIdle(1000*60*60*6);//设置可用连接池的最长等待时间
       //设置连接池维护线程的睡眠时间 ,设置为0,维护线程不启动。维护线程主要通过log输出
       pool.setMaintSleep(30);
       //设置是否使用Nagle算法,因为通讯数据量通常都比较大(相对TCP控制数据)而且要求响应及时,
       // 因此该值需要设置为false(默认是true)
       pool.setNagle(false);
       pool.setSocketConnectTO(0);//设置socket的读取等待超时值
       pool.setSocketTO(3000);
       pool.setHashingAlg(3);
       // 设置hash算法
        //  alg=0 使用String.hashCode()获得hash code,该方法依赖JDK,可能和其他客户端不兼容,建议不使用
        //  alg=1 使用original 兼容hash算法,兼容其他客户端
        //  alg=2 使用CRC32兼容hash算法,兼容其他客户端,性能优于original算法
        //  alg=3 使用MD5 hash算法
        //  采用前三种hash算法的时候,查找cache服务器使用余数方法。采用最后一种hash算法查找cache
//       pool.initialize();// 设置完pool参数后最后调用该方法,启动pool。
   }
   public static MemCachedClient getMemCachedClient(){
        return mcc;
   }

    /**
     * set缓存数据
     * @param key 取值的键
     * @param value 缓存的字符串类型
     */
   public static void set(String key,String value){
    mcc.set(key,value);
   }
    /**
     * set缓存数据
     * @param key
     * @param obj  缓存对象类型
     */
   public static void set(String key,Object obj){
       mcc.set(key,obj);
   }

    /**
     * replace替换存在key的值，key不存在则替换失败
     * @param key
     * @param obj
     */
    public static void replace(String key,Object obj){
        mcc.replace(key, obj);
    }

    /**
     * append在已有的key后追加数据
     * @param key
     * @param obj
     */
    public static void append(String key,Object obj){
        mcc.append(key,obj);
    }

    /**
     * get 获取单个数据
     * @param key
     * @return
     */
    public static Object get(String key){
        return mcc.get(key);
    }

    /**
     * get获取多个值
     * @param keys
     * @return 返回map集合
     */
    public static Map<String,Object> get(Collection<String> keys){
        Map<String,Object> map = new HashMap<>();
        for(String s:keys){

            map.put(s,mcc.get(s));
        }
            return map;
    }
}
