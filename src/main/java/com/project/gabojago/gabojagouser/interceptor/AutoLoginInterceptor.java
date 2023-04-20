package com.project.gabojago.gabojagouser.interceptor;

import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.lib.AESEncryption;
import com.project.gabojago.gabojagouser.service.user.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@AllArgsConstructor
@Component
public class AutoLoginInterceptor implements HandlerInterceptor {

  private UserService userService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    HttpSession session=request.getSession();
    Object loginUser=session.getAttribute("loginUser");
    if(loginUser!=null) {
      return true;
    }
    Cookie[] cookies=request.getCookies();
    if(cookies==null) {
      return true;
    }
      Cookie loginUId=null;
      Cookie loginPw=null;
      for(Cookie cookie:cookies) {
        if(cookie.getName().equals("loginId")) {
          loginUId=cookie;
        } else if(cookie.getName().equals("loginPw")) {
          loginPw=cookie;
        }
      }
    if(loginUId!=null && loginPw!=null) {
      UserDto autoLoginUser=null;
      try {
        UserDto user=new UserDto();
        user.setUId(AESEncryption.decryptValue(loginUId.getValue()));
        user.setPw(AESEncryption.decryptValue(loginPw.getValue()));
        autoLoginUser=userService.login(user);
      } catch (Exception e) {
        e.printStackTrace();
      }
      if(autoLoginUser!=null) {
        session.setAttribute("loginUser", autoLoginUser);
        return true;
      } else {
        loginUId.setMaxAge(0);
        loginPw.setMaxAge(0);
        loginUId.setPath("/");
        loginPw.setPath("/");
        response.addCookie(loginUId);
        response.addCookie(loginPw);
        response.sendRedirect("/user/login.do");
        return false;
      }
    }
    return true;
  }
}