package org.tuvarna;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotAuthorizedException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.time.Duration;

@ApplicationScoped
public class JwtService {

    @ConfigProperty(name = "smallrye.jwt.sign.key.location")
    String privateKeyLocation;

    @ConfigProperty(name ="mp.jwt.verify.issuer")
    String verifyIssuer;

    public String jwtGenerator() {
        return Jwt.issuer(verifyIssuer)
                .subject("admincreator")
                .claim("userid", -1)
                .groups("INNER")
                .expiresIn(Duration.ofSeconds(10))
                .sign(privateKeyLocation);
    }

}
