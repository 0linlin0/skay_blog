package service;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public interface LoginService {
    public Boolean login(String username, String password);
}
