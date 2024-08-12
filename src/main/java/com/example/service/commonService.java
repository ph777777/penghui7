package com.example.service;

import com.example.db.DBpool;
import com.example.db.PooledConnection;
import com.example.entity.*;
import com.example.entity.Cook;
import com.example.mapper.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class commonService {


    @Autowired
    private Common commonmmapper;

    @Autowired
    DBpool dBpool;

    public int getcooknumber(){

        return commonmmapper.getcooknumber();
    }
    public List<Cook> cookinfo(int startRecord, int pageSize){

        return commonmmapper.cookinfo(startRecord,pageSize);
    }

    public Map<String, String> login(String username, String password){

//        List login = commonmmapper.login(username, password);
        Map<String,String> up = new HashMap<>();
//        ph_User_Info pui = (ph_User_Info)login.get(0);

        String u =null;
        String p = null;

        try {

            PooledConnection  pooledConnection = dBpool.getPooledConnection();
            Connection connection = pooledConnection.getConnection();
            pooledConnection.readLock();
            String sql = "SELECT user_name,password FROM ph_user_info a WHERE a.user_name = ? AND a.password=? ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,username);
            ps.setString(2,password);
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()){
                 u = resultSet.getNString("user_name");
                 p = resultSet.getNString("password");
            }
            pooledConnection.close();
            pooledConnection.unReadLock();
        }catch (Exception e){
            System.out.println("sql处理异常："+e.getMessage());
            e.printStackTrace();
        }

        if(u.equals(username)&&p.equals(password)){
            up.put("login","true");
        }else{
            up.put("login","false");
        }
        return up;
    }
}
