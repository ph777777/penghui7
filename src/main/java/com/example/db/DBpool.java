package com.example.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


@Slf4j
@Component
public class DBpool {

    //数据库相关参数
    @Value("${spring.datasource.driver-class-name}")
    private  String jdbcDriver ;
    @Value("${spring.datasource.url}")
    private  String jdbcUrl;
    @Value("${spring.datasource.username}")
    private  String userName;
    @Value("${spring.datasource.password}")
    private  String password ;

    //容器参数
    @Value("${spring.datasource.initcount}")
    private  int initCount;//初始数量
    @Value("${spring.datasource.stepsize}")
    private  int stepSize ;//每次扩容的数量
    @Value("${spring.datasource.poolmaxsize}")
    private  int poolMaxSize ;//最大数量



    //全局锁
    private static Lock lock ;

    static List<PooledConnection> PoolsConnections = new ArrayList<>();

    public PooledConnection getPooledConnection() throws RuntimeException, SQLException {
        if (poolMaxSize <= 0) {
            System.out.println("创建管道对象失败，最大值参数错误");
            log.info("创建管道对象失败，最大值参数错误");
            throw new IllegalArgumentException("创建管道对象失败，最大值参数错误");
        }
        lock= new ReentrantLock();
        PooledConnection realConnection = getRealConnection();
        while (realConnection == null) {
            if (lock.tryLock()) {//尝试获取锁
                createConnections(stepSize);//获得锁之后进行扩容
                lock.unlock();
            } else {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                }
             }
            realConnection = getRealConnection();
            if (realConnection != null) {
                return realConnection;
            }
        }
        return realConnection;
    }
    private PooledConnection getRealConnection() {
        for (PooledConnection pooledConnection : PoolsConnections) {
            try {
                if (pooledConnection.isBusy())
                    continue;
 /*
 此处要保证写的时候不能被读取，不然会报ConcurrentModificationException异常
 */
                pooledConnection.writeLock();//读写互斥，写写互斥
                Connection connection = pooledConnection.getConnection();
                if (!connection.isValid(200)) {//是否有效，200ms 没有被超时
                    Connection validConnect = DriverManager.getConnection(jdbcUrl, userName, password);
                    pooledConnection.setConnection(validConnect);
                }
                pooledConnection.setBusy(true);
                pooledConnection.unWriteLock();
                return pooledConnection;
            } catch (SQLException e) {
                return null;
            }
        }
        return null;
    }
    public void createConnections(int count) throws IllegalArgumentException {
        if (poolMaxSize <= 0) {
            System.out.println("创建管道对象失败，最大值参数错误");
            throw new IllegalArgumentException("创建管道对象失败，最大值参数错误");
        }
        //判断是否有溢出
        boolean overFlow = isOverFlow(count);
        if (overFlow) {
            return;
        }
        System.out.println("扩容");
        for (int i = 0; i < count; i++) {
            try {
                overFlow = isOverFlow(count);
                if (overFlow)
                    return;
                Connection connection = DriverManager.getConnection(jdbcUrl, userName, password);
                PooledConnection pooledConnection = new PooledConnection(connection, false);
                PoolsConnections.add(pooledConnection);
            } catch (SQLException e) {
            }
        }
        System.out.println("扩容数量：" + PoolsConnections.size());
    }
    private boolean isOverFlow(int count) {
        if (PoolsConnections.size() + count >= poolMaxSize) {
            return true;
        }
        return false;
    }
}
