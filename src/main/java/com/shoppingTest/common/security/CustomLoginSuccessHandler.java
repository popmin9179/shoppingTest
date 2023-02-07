package com.shoppingTest.common.security;

import com.shoppingTest.common.security.domain.CustomUser;
import com.shoppingTest.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    //@Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException{
        CustomUser customUser = (CustomUser)authentication.getPrincipal();
        Member member =customUser.getMember();

        log.info("UserId = " + member.getUserId());

        super.onAuthenticationSuccess(request,response,authentication);
    }
}
