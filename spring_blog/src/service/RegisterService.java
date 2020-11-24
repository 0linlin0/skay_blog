package service;

import entity.User;
import org.springframework.stereotype.Service;

/**
 * @auther Skay
 * @date 2020/11/13 16:39
 * @description
 */
public interface RegisterService {
    boolean register(User user);
}
