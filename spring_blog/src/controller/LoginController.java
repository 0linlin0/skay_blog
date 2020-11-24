package controller;

import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import service.LoginService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @auther Skay
 * @date 2020/11/13 16:28
 * @description
 */
@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Resource
    private Validator validator;

    @RequestMapping(value = "/do_login")
    public String login(Model model, User user, BindingResult result){
//        验证输入是否为空
        this.validator.validate(user,result);

        String username = user.getUsername();
        String password = user.getPassword();

        if(loginService.login(username,password)){
            return "forward:index";
        }else {
            return "forward:login";
        }
    }

    @RequestMapping(value = "/login")
    public String login(){
        return "login";
    }
}
