package controller;

/**
 * @auther Skay
 * @date 2020/11/13 9:59
 * @description
 */
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController{
    
    /** 
        * @Author  Skay
        * @Date  2020/11/13 11:41
        * @Param  [httpServletRequest, httpServletResponse] 
        * @return  org.springframework.web.servlet.ModelAndView
        * @Description  
        * @todo
    */
    @RequestMapping("/hello")
    public ModelAndView handleRequest(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse) throws Exception {
//        我的注释 配置完视图解析器以后index.jsp 可以简写为index
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("message", "Hello Spring MVC");
        return mav;
    }
}