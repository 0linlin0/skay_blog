package service.impl;

import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.UserDao;
import service.RegisterService;

/**
 * @auther Skay
 * @date 2020/11/13 16:42
 * @description
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private UserDao userDao;

    @Override
    public boolean register(User user) {
        return userDao.register_dao(user);
    }
}
