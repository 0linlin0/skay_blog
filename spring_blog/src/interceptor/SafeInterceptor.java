package interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.interceptor.Interceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @auther Skay
 * @date 2020/11/13 14:57
 * @description
 */

@Interceptor
public class SafeInterceptor implements HandlerInterceptor {
    private HashMap<String,Object> req_map = new HashMap<>();

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        req_map = util.initutil.parsreq(request.getParameterNames(),request,response,req_map);

        if(req_map != null){
            req_map = XSS_Safe.check(req_map);
            if(!SQL_Safe.check(req_map)){
                // 将报错信息返回
                request.setAttribute("msg", "Please stop hacking！");
                request.getRequestDispatcher("/WEB-INF/page/error.jsp").forward(request,
                        response);
            }
        }

        return true;
    }
    public void afterCompletion(HttpServletRequest arg0,
                                HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
        // TODO Auto-generated method stub
    }
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
                           Object arg2, ModelAndView arg3) throws Exception {
        // TODO Auto-generated method stub
    }
}


class SQL_Safe {
    public static boolean check(HashMap<String, Object> req_map) {
        String key;
        String value;
        for (Map.Entry<String, Object> entry : req_map.entrySet()) {
            key = entry.getKey();
            value = (String) entry.getValue();
            sql_inj(value.toLowerCase());
            sql_inj(key.toLowerCase());

        }
        return true;
    }

    public static boolean sql_inj(String str){
        //todo 预编译
        String inj_str = "'|and|exec|insert|select|delete|update| count|*|%|chr|mid|master|truncate|char|declare|;|or|-|+|,";
        String[] inj_stra=inj_str.split("\\|");
        for (int i=0 ; i < inj_stra.length ; i++ ){
            if (str.indexOf(inj_stra[i]) >= 0){
                return false;
            }
        }
        return true;

    }
}

class XSS_Safe {
    public static HashMap<String, Object> check(HashMap<String, Object> req_map) {
        HashMap<String, Object> newhaspmap = new HashMap<>();
        String key;
        String value;
        for (Map.Entry<String, Object> entry : req_map.entrySet()) {
            key = entry.getKey();
            value = (String) entry.getValue();
            newhaspmap.put(cleanXSS(key.toLowerCase()),cleanXSS(value.toLowerCase()));

        }
        return newhaspmap;
    }

    private static String cleanXSS(String value) {
        //todo 全部实体编码


        value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");

        value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");

        value = value.replaceAll("'", "& #39;");

        value = value.replaceAll("eval\\((.*)\\)", "");

        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");

        value = value.replaceAll("script", "");

        return value;

    }
}
