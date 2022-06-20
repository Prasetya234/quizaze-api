package swlayer.project.demo.enggine.jwt;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import swlayer.project.demo.enggine.data.UserDetailsServiceImpl;
import swlayer.project.demo.web.model.TokenUser;
import swlayer.project.demo.web.model.TrafficRekap;
import swlayer.project.demo.web.repository.TokenUserRepository;
import swlayer.project.demo.web.repository.TrafficRecapRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

public class JwtAuthTokenFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthTokenFilter.class);
    @Autowired
    private JwtProvider tokenProvider;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @Autowired
    private TokenUserRepository tokenUserRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwt(request);
            if (jwt != null) {
                Optional<TokenUser> token = tokenUserRepository.findByToken(jwt, new Date());
                if (token.isPresent()) {
                    String username = refreshToken(token.get().getToken()).getUser().getUsername();
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication
                            = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                }
            }
        } catch (Exception e) {
            logger.error("Can NOT set user authentication -> Message: {}", e);
        }
        filterChain.doFilter(request, response);
    }
    private String getJwt(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.replace("Bearer ", "");
        }
        return null;
    }

    private TokenUser refreshToken(String jwt) {
        TokenUser refreshToken = tokenUserRepository.findByToken(jwt);
        refreshToken.setExpiredDate(new Date(System.currentTimeMillis() + 900000));
        return tokenUserRepository.save(refreshToken);
    }


}
