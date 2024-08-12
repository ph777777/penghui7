package com.example.Controller;

import com.example.entity.Cook;
import com.example.service.commonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CookingController {

    @Autowired
    public commonService commonService;

    @GetMapping(value = "/cookinfolist")
    @ResponseBody
    public Map getCookingList(HttpServletRequest request){
        int page=Integer.parseInt(request.getParameter("page"));
        int pageSize=Integer.parseInt(request.getParameter("row"));
        int startRecord=(page-1)*pageSize+1;
        int total= commonService.getcooknumber();

//        后台测试数据
//        int page = 1;
//        int pageSize = 5;
//        int startRecord = 0;
//        int total = 3;
        List<Cook> cookinfolist=commonService.cookinfo(startRecord,pageSize);
        Map resultMap=new HashMap();
        resultMap.put("total",total-1);
        resultMap.put("row",cookinfolist);
        return resultMap;
    }

    @PostMapping(value = "/login")
    @ResponseBody
    public Map login(@RequestParam String username,@RequestParam String password){

//        String user_name = request.getParameter("username");
//        String password  = request.getParameter("password");

        Map<String, String> login = commonService.login(username, password);

        return login;

    }

//    @GetMapping(value = "/")
//    @ResponseBody
//    public String init(HttpServletRequest request){
//
//        return "/login";
//
//    }


}
