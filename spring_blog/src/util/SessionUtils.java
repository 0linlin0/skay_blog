package util;

import entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @auther Skay
 * @date 2020/10/20 19:22
 * @description
 */
public class SessionUtils {
    public static HttpSession creat_session(User user, HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("username",user);
        session.setMaxInactiveInterval(10*60*10);
        return session;
    }

    public static boolean verifica_session(HttpSession session){
        if(session == null){
            return false;
        }else {
            return true;
        }

    }
}
