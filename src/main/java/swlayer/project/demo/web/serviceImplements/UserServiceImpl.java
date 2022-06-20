package swlayer.project.demo.web.serviceImplements;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swlayer.project.demo.enggine.data.UserDetailsServiceImpl;
import swlayer.project.demo.enggine.exception.BussinesException;
import swlayer.project.demo.enggine.exception.NotFoundException;
import swlayer.project.demo.enggine.jwt.JwtProvider;
import swlayer.project.demo.web.model.User;
import swlayer.project.demo.web.repository.SchoolRepository;
import swlayer.project.demo.web.repository.UserRepository;
import swlayer.project.demo.web.service.UserService;
import swlayer.project.demo.webrequest.dto.UpdateUser;
import swlayer.project.demo.webrequest.dto.UserAuthenticate;
import swlayer.project.demo.webrequest.dto.UserAuthenticateResponse;
import swlayer.project.demo.webrequest.dto.UserResponse;
import swlayer.project.demo.webrequest.util.Auth;
import swlayer.project.demo.webrequest.util.HttpReqRespUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl extends HttpReqRespUtils implements UserService, Auth {

    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private JwtProvider jwtProvider;
    private UserDetailsServiceImpl userDetailsService;
    private UserRepository userRepository;
    private SchoolRepository schoolRepository;
    private ModelMapper modelMapper;


    @Autowired
    public UserServiceImpl(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtProvider jwtProvider, UserDetailsServiceImpl userDetailsService, UserRepository userRepository, SchoolRepository schoolRepository, ModelMapper modelMapper) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.schoolRepository = schoolRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public UserResponse updateUser(String id, UpdateUser user) {
        User userUpdate = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User id " + id + " Not found"));
        userUpdate.setEmail(user.getEmail());
        userUpdate.setAvatar(user.getAvatar());
        userUpdate.setUsername(user.getUsername());
        return modelMapper.map(userRepository.save(userUpdate), UserResponse.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserResponse> showGuest(int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        return userRepository.findByGuestIsTrueAndBlockedIsFalse(paging).stream().map(data -> {
            UserResponse res = modelMapper.map(data, UserResponse.class);
            return res;
        }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public UserResponse getById(String id) {
        return modelMapper.map(userRepository.findById(id).orElseThrow(() -> new NotFoundException("User id " + id + " Not found")), UserResponse.class);
    }

    @Transactional
    @Override
    public UserResponse updateSchool(String userId, String schoolId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("user id not found"));
        user.setSchool(schoolRepository.findById(schoolId).orElseThrow(() -> new NotFoundException("school id not found")));
        return modelMapper.map(userRepository.save(user), UserResponse.class);
    }

    @Transactional
    @Override
    public UserAuthenticateResponse authenticate(UserAuthenticate user) {
        boolean admin = false;
        String jwt = authories(user);
        User users = userRepository.findByUsernameAndBlockedIsFalse(user.getUsername()).get();
        for(String type: users.getRole().split(",")) {
            if (type.equals(roleSwlayer.ADMIN_SCHOOL) || type.equals(roleSwlayer.ADMIN)){
                admin = true;
            }
        }
        if (!admin) {
            throw new NotFoundException("Your role is not admin");
        }
        users.setDevice(getClientIpAddressIfServletRequestExist());
        userRepository.save(users);
        UserAuthenticateResponse res = new UserAuthenticateResponse();
        res.setId(users.getId());
        res.setJwt(jwt);
        res.setUser(modelMapper.map(users, UserResponse.class));
        return res;
    }

    @Transactional
    @Override
    public User createAccountAdminSchool(String schoolId, User user) {
        if (userRepository.findByUsernameAndBlockedIsFalse(user.getUsername()).isPresent()) throw new BussinesException("Username already axist");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleSwlayer.ADMIN_SCHOOL);
        user.setGuest(false);
        user.setBlocked(false);
        user.setDevice(getClientIpAddressIfServletRequestExist());
        user.setSchool(schoolRepository.findById(schoolId).orElseThrow(() -> new NotFoundException("School id not found")));
        return userRepository.save(user);
    }

    @Override
    public User createAccountAdmin(User user) {
        if (userRepository.findByUsernameAndBlockedIsFalse(user.getUsername()).isPresent()) throw new BussinesException("Username already axist");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleSwlayer.ADMIN + "," + roleSwlayer.ADMIN_SCHOOL + "," + roleSwlayer.ADMIN);
        user.setGuest(false);
        user.setBlocked(false);
        user.setDevice(getClientIpAddressIfServletRequestExist());
        return userRepository.save(user);
    }

    public String authories(UserAuthenticate user) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        } catch (BadCredentialsException e) {
            throw new RuntimeException(e.getMessage());
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        return jwtProvider.generateJwtToken(userDetails);
    }


}
