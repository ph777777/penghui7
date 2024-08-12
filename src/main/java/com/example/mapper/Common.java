package com.example.mapper;

import com.example.entity.*;

import com.example.entity.Cook;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface Common {

    @Select("select count(*) from cooking")
    public int getcooknumber();

    @Select("select * from cooking limit #{startRecord},#{pageSize}")
    public List<Cook> cookinfo(@Param("startRecord") int startRecord, @Param("pageSize")int pageSize);

    @Select("select * from ph_user_info where user_name =#{username} and password=#{password}")
    public List<ph_User_Info> login(@Param("username") String username,@Param("password") String password);
}
