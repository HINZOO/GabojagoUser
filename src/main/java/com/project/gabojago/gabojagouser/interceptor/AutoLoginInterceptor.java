package com.project.gabojago.gabojagouser.interceptor;

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
    if(loginUser!=null) return true;
    Cookie[] cookies=request.getCookies();
    if(cookies!=null) {
      String uId=null;
      String pw=null;
      for(Cookie cookie:cookies) {
        if(cookie.getName().equals("loginId")) {
          uId=cookie.getValue();
        } else if(cookie.getName().equals("loginPw")) {
          pw=cookie.getValue();
        }
      }
    }
    return HandlerInterceptor.super.preHandle(request, response, handler);
  }
}