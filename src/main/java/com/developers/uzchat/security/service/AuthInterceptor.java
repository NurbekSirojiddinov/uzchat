package com.developers.uzchat.security.service;

import com.developers.uzchat.context.UserContext;
import com.developers.uzchat.security.Authorized;
import com.developers.uzchat.security.AuthHandler;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Aspect
@Component
public class AuthInterceptor {

    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MESSAGE_REQUEST_UNAUTHORIZED = "Request is not authorized.";

    public static final String TOKEN_PREFIX = "Bearer ";
    private final HttpServletRequest request;
    private final AuthHandler authHandler;

    public AuthInterceptor(HttpServletRequest request, AuthHandler authHandler) {
        this.request = request;
        this.authHandler = authHandler;
    }

    @After("@annotation(com.developers.uzchat.security.Authorized)")
    public void cleanupAuthorizedRequests() {
        // Cleaning up local thread context before exiting the application
        UserContext.clear();
    }

    @Before("@annotation(com.developers.uzchat.security.Authorized)")
    public void interceptSecuredRequests(final JoinPoint joinPoint) throws AuthException {
        if (request == null) {
            final String errorMsg = "HTTP request content is not available";
            LOGGER.error(errorMsg);

            throw new AuthException(errorMsg);
        }

        final String bearerToken = request.getHeader(AUTHORIZATION);

        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        final Authorized authorized = methodSignature.getMethod().getAnnotation(Authorized.class);


        if (authorized.conditional()) {
            if (bearerToken == null) {
                return;
            }
        } else {
            if (bearerToken == null) {
                LOGGER.error("No bearer token specified in the request header");

                throw new AuthException(MESSAGE_REQUEST_UNAUTHORIZED);
            }
        }

        if (!bearerToken.startsWith(TOKEN_PREFIX)) {
            final String errorMsg = "Token prefix is not found in the authorization header";
            LOGGER.error(errorMsg);

            throw new AuthException(MESSAGE_REQUEST_UNAUTHORIZED);
        }

        LOGGER.trace(
                "Checking following bearer token for authorization: [{}]",
                bearerToken.replaceAll("[\r\n]", ""));

        final String jwt = bearerToken.substring(TOKEN_PREFIX.length());

        authHandler.authorize(jwt);
    }
}
