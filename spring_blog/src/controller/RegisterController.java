package controller;

import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import service.RegisterService;

import javax.annotation.Resource;

/**
 * @auther Skay
 * @date 2020/11/13 16:28
 * @description
 */
@Controller
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @Resource
    private Validator validator;

    @RequestMapping(value = "/do_register")
    public String register(Model model, User user, BindingResult result){
//        添加验证 是否为空
        validator.validate(user,result);

        if(registerService.register(user)){
            return "forward:login";
        }else {
            return "forward:register";
        }
    }

    @RequestMapping(value = "/register")
    public String register(){
        return "register";
    }
}
