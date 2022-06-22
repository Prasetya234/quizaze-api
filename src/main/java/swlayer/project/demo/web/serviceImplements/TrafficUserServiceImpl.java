package swlayer.project.demo.web.serviceImplements;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swlayer.project.demo.enggine.data.AuthenticationFacade;
import swlayer.project.demo.enggine.data.UserDetailsServiceImpl;
import swlayer.project.demo.enggine.exception.BussinesException;
import swlayer.project.demo.enggine.exception.NotFoundException;
import swlayer.project.demo.enggine.jwt.IAuthenticationFacade;
import swlayer.project.demo.enggine.jwt.JwtProvider;
import swlayer.project.demo.web.model.TrafficRekap;
import swlayer.project.demo.web.model.TrafficUser;
import swlayer.project.demo.web.model.User;
import swlayer.project.demo.web.repository.TrafficRecapRepository;
import swlayer.project.demo.web.repository.TrafficUserRepository;
import swlayer.project.demo.web.repository.UserRepository;
import swlayer.project.demo.web.service.TrafficUserService;
import swlayer.project.demo.webrequest.util.Auth;
import swlayer.project.demo.webrequest.util.HttpReqRespUtils;

import java.util.Optional;

@Slf4j
@Service
public class TrafficUserServiceImpl extends HttpReqRespUtils implements TrafficUserService, Auth {

    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private AuthenticationFacade authenticationFacade;
    private JwtProvider jwtProvider;
    private UserDetailsServiceImpl userDetailsService;

    private TrafficRecapRepository trafficRecapRepository;
    private UserRepository userRepository;
    private TrafficUserRepository trafficUserRepository;

    @Autowired
    public TrafficUserServiceImpl(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, AuthenticationFacade authenticationFacade, JwtProvider jwtProvider, UserDetailsServiceImpl userDetailsService, UserRepository userRepository, TrafficUserRepository trafficUserRepository, TrafficRecapRepository trafficRecapRepository) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationFacade = authenticationFacade;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.trafficUserRepository = trafficUserRepository;
        this.trafficRecapRepository = trafficRecapRepository;
    }


    @Transactional(readOnly = true)
    @Override
    public Page<TrafficUser> showTrafficUserAdmin(int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        return trafficUserRepository.findAll(paging);
    }

    @Transactional
    @Override
    public TrafficUser testingConnection(String id) {
        String device = getClientIpAddressIfServletRequestExist();
        if (id == null || id.equals("")) {
            return updateUserDevice(device);
        }
        Optional<User> userValid = userRepository.findById(id);
        if (userValid.isPresent()) {
            User user = updateDevice(userValid.get(), device);
            var valid = trafficUserRepository.findByUserId(userValid.get().getId());
            if (valid.isEmpty()) {
                return createTraffic(user, checkingAdmin(user.getRole().split(",")));
            }
            TrafficUser traffic = trafficUserRepository.findByUserId(user.getId()).get();
            traffic.setVisitors(traffic.getVisitors() + 1);
            traffic.setJwtToken(authories(user, checkingAdmin(user.getRole().split(","))));
            return trafficUserRepository.save(traffic);
        }
        return updateUserDevice(device);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<TrafficRekap> showTrafficRecap(int page, int size) {
        try {
            Pageable paging = PageRequest.of(page, size);
            var user = userRepository.findByUsernameAndBlockedIsFalse(authenticationFacade.getAuthentication().getName()).orElseThrow(() -> new BussinesException("Data recap cannot show in this user!"));
            return trafficRecapRepository.findBySchoolId(user.getSchool().getId(), paging);
        } catch(RuntimeException e) {
            throw new BussinesException("Traffic List Empty");
        }
    }


    private TrafficUser updateUserDevice(String device) {
//        Optional<User> test = userRepository.findByDevice(device);
//        if (test.isPresent()) {
//            User user = updateDevice(test.get(), device);
//            TrafficUser traffic = trafficUserRepository.findByUserId(user.getId()).orElseThrow(() -> new NotFoundException("User id not found"));
//            traffic.setVisitors(traffic.getVisitors() + 1);
//            traffic.setJwtToken(authories(user, checkingAdmin(user.getRole().split(","))));
//            return trafficUserRepository.save(traffic);
//        }
        return createUser(device);
    }

    private boolean checkingAdmin(String[] roles) {
        var adm = false;
        for (String role : roles) {
            if (role.equals(roleSwlayer.ADMIN_SCHOOL) || role.equals(roleSwlayer.ADMIN)) adm = true;
        }
        return adm;
    }

    private TrafficUser createUser(String device) {
        User user = new User();
        user.setGuest(true);
        user.setBlocked(false);
        user.setDevice(device);
        user.setRole(roleSwlayer.USER);
        user.setPassword(passwordEncoder.encode(validtokenSwlayer.PASS));
        User responseUser = userRepository.save(user);
        responseUser.setUsername(validtokenSwlayer.USER + responseUser.getId().split("-")[0]);
        return createTraffic(userRepository.save(responseUser), false);
    }

    private TrafficUser createTraffic(User u, boolean admin) {
        TrafficUser traffic = new TrafficUser();
        traffic.setVisitors(1);
        traffic.setUser(u);
        traffic.setJwtToken(authories(traffic.getUser(), admin));
        return trafficUserRepository.save(traffic);
    }

    private User updateDevice(User user, String device) {
        recapUser(user);
        user.setDevice(device);
        return userRepository.save(user);
    }

    private void recapUser(User user) {
        try {
            if (user.getSchool() != null)  {
                var rekap = trafficRecapRepository.findByTrafficSchool(user.getSchool().getId()).orElse(null);
                rekap.setVisitors(rekap.getVisitors() +1);
                trafficRecapRepository.save(rekap);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    public String authories(User user, boolean admin) {
        if (!admin) {
            User data = userRepository.findById(user.getId()).orElseThrow(() -> new NotFoundException("User id not found"));
            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(data.getUsername(), validtokenSwlayer.PASS));
            } catch (BadCredentialsException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        return jwtProvider.generateJwtToken(userDetails);
    }
}
