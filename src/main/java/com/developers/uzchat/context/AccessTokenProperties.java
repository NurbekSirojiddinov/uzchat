package com.developers.uzchat.context;


import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("application.access-token")
public class AccessTokenProperties {

    @NotBlank
    private String issuer;
    @NotBlank private String secret;

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(final String issuer) {
        this.issuer = issuer;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(final String secret) {
        this.secret = secret;
    }

    @Override
    public String toString() {
        return "AccessTokenProperties{"
                + "issuer='"
                + issuer
                + '\''
                + ", secret='"
                + secret
                + '\''
                + '}';
    }
}

