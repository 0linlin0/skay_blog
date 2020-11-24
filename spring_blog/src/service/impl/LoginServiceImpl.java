package service.impl;

import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import repository.UserDao;
import service.LoginService;
import util.SessionUtils;
import util.TokenUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @auther Skay
 * @date 2020/11/13 16:40
 * @description
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserDao userDao;

    @Override
    public Boolean login(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpServletResponse response = servletRequestAttributes.getResponse();

        if(userDao.login_dao(user)){
            HttpSession session =  SessionUtils.creat_session(user,request);
            User session_user = (User)session.getAttribute("username");
            response.addCookie(TokenUtils.creat_token( session.getId()));
            return true;
        }else {
            return false;
        }
    }
}
