package com.developers.uzchat.context;


import com.nimbusds.jose.proc.SecurityContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserContext implements SecurityContext {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final ThreadLocal<UserPrincipal> CONTEXT = new ThreadLocal<>();

    private UserContext() {}

    public static void clear() {
        LOGGER.debug("Cleaning up '{}' from the user context", getUsername());

        CONTEXT.remove();
    }

    public static String getEmail() {
        final UserPrincipal loggedOnUser = CONTEXT.get();
        return loggedOnUser != null ? loggedOnUser.getEmail() : null;
    }

    public static String getFullName() {
        final UserPrincipal loggedOnUser = CONTEXT.get();
        return loggedOnUser != null ? loggedOnUser.getFullName() : null;
    }

    public static String getUsername() {
        final UserPrincipal loggedOnUser = CONTEXT.get();
        return loggedOnUser != null ? loggedOnUser.getUsername() : null;
    }

    public static UserPrincipal getUser() {
        return CONTEXT.get();
    }

    public static void setUser(final UserPrincipal user) {
        LOGGER.debug("Loading '{}' into user context", user);

        CONTEXT.set(user);
    }
}

