package com.esmc.mcnp.infrastructure.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import com.esmc.mcnp.commons.constant.SystemConstant;
import com.esmc.mcnp.commons.util.MessageUtils;
import com.esmc.mcnp.config.thread.AsyncFactory;
import com.esmc.mcnp.config.thread.AsyncManager;

@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Override
    public void onApplicationEvent(final AuthenticationSuccessEvent e) {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            loginAttemptService.loginSucceeded(request.getRemoteAddr());
        } else {
            loginAttemptService.loginSucceeded(xfHeader.split(",")[0]);
        }
        AsyncManager.asyncManager().execute(AsyncFactory.recordLogininfor(e.getAuthentication().getName(),
				SystemConstant.SUCCESS, MessageUtils.message("message.user.login.success")));
    }
}
