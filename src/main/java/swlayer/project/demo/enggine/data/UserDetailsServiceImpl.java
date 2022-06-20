package swlayer.project.demo.enggine.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import swlayer.project.demo.web.model.User;
import swlayer.project.demo.web.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameAndBlockedIsFalse(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User Not Found with -> username: " + username)
                );
        return UserPrinciple.build(user);
    }
}
