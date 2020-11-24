package Util;

import Entity.Config;

import javax.servlet.http.Cookie;

/**
 * @auther Skay
 * @date 2020/10/20 18:55
 * @description
 */
public class TokenUtils {

    public static Cookie creat_token(String session){
        String random = RandomUtil.getStringRandom(6);
        String step1 = AESUtils.encodes(random,Config.getAESKEY());
        String step2 = AESUtils.encodes(session,Config.getAESKEY());
        String token = AESUtils.encodes(step1+step2,Config.getAESKEY());
        Cookie cookie = new Cookie("token",random+token);
        cookie.setMaxAge(60*60*24*3);
        return cookie;
    }

    public static boolean verifica_token(String token,String session){
        String random = token.substring(0,6);
        if(token != "" && session != ""){
            String step1 = AESUtils.encodes(random,Config.getAESKEY());
            String step2 = AESUtils.encodes(session,Config.getAESKEY());
            if(AESUtils.decodes(token.substring(6),Config.getAESKEY()).equals(step1+step2)){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }

    }
}
