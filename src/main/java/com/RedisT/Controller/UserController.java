package com.RedisT.Controller;

import com.RedisT.Util.Producer;
import com.RedisT.Util.Produre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    @Autowired
    Produre producer;
    @RequestMapping({"/index.html","/"})
    public String index(Model model){
        model.addAttribute("msg","我欲成仙");
        return "index";
    }

    @RequestMapping("callmq")
    public  String index(){
        producer.sendMsg("www");
        return "index";
    }
}
