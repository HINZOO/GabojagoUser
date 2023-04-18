package com.project.gabojago.gabojagouser;

import com.project.gabojago.gabojagouser.interceptor.AutoLoginInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@AllArgsConstructor
@Configuration
public class interceptorConfig implements WebMvcConfigurer {
  private AutoLoginInterceptor autoLoginInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(autoLoginInterceptor)
            .addPathPatterns("/**");
  }
}
