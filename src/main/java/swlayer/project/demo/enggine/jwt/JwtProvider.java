package swlayer.project.demo.enggine.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import swlayer.project.demo.enggine.exception.NotFoundException;
import swlayer.project.demo.web.model.TokenUser;
import swlayer.project.demo.web.model.User;
import swlayer.project.demo.web.repository.TokenUserRepository;
import swlayer.project.demo.web.repository.UserRepository;

import java.util.Date;

@Component
public class JwtProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);


    private TokenUserRepository tokenUserRepository;
    private UserRepository userRepository;

    @Autowired
    public JwtProvider(TokenUserRepository tokenUserRepository, UserRepository userRepository) {
        this.tokenUserRepository = tokenUserRepository;
        this.userRepository = userRepository;
    }

    @Value("bootcamp_")
    private String jwtSecret;

    @Value("900000")  // 15 minute
    private int jwtExpiration;

    public String generateJwtToken(UserDetails userDetails) {
        String jwt = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        User user = userRepository.findByUsernameAndBlockedIsFalse(userDetails.getUsername()).orElseThrow(() -> new NotFoundException("userNot found"));
        TokenUser valid = tokenUserRepository.findByUser(user.getId());
        if (valid != null) {
            tokenUserRepository.deleteById(valid.getId());
        }
        TokenUser token = new TokenUser();
        token.setUser(user);
        token.setToken(jwt);
        token.setExpiredDate(new Date(System.currentTimeMillis() + 1800000));
        tokenUserRepository.save(token);
        return jwt;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature -> Message: {} ", e);
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token -> Message: {}", e);
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token -> Message: {}", e);
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token -> Message: {}", e);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty -> Message: {}", e);
        }

        return false;
    }
}
