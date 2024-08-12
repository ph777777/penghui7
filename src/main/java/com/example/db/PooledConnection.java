package com.example.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class PooledConnection {
    private boolean isBusy = false;
    private Connection connection;
    private ReentrantReadWriteLock reentrantReadWriteLock;
    public PooledConnection(Connection connection, boolean b) {
        this.connection = connection;
        reentrantReadWriteLock = new ReentrantReadWriteLock();
    }
    public boolean isBusy() {
        return isBusy;
    }
    public void setBusy(boolean busy) {
        isBusy = busy;
    }
    public Connection getConnection() {
        return connection;
    }
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    public void close() {
        this.setBusy(false);
    }
    public void shutDown() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //增加读写锁的操作
    public void writeLock() {
        this.reentrantReadWriteLock.writeLock().lock();
    }
    public void unWriteLock() {
        this.reentrantReadWriteLock.writeLock().unlock();
    }
    public void readLock() {
        this.reentrantReadWriteLock.readLock().lock();
    }
    public void unReadLock() {
        this.reentrantReadWriteLock.readLock().unlock();
    }
}
