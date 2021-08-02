package com.esmc.mcnp.infrastructure.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import com.esmc.mcnp.commons.constant.SystemConstant;
import com.esmc.mcnp.commons.util.MessageUtils;
import com.esmc.mcnp.config.thread.AsyncFactory;
import com.esmc.mcnp.config.thread.AsyncManager;

@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Override
    public void onApplicationEvent(final AuthenticationFailureBadCredentialsEvent e) {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            loginAttemptService.loginFailed(request.getRemoteAddr());
        } else {
            loginAttemptService.loginFailed(xfHeader.split(",")[0]);
        }
        AsyncManager.asyncManager().execute(AsyncFactory.recordLogininfor(e.getAuthentication().getName(),
				SystemConstant.ERROR, MessageUtils.message("user.login.failed")));
    }
}