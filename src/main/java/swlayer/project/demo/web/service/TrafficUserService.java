package swlayer.project.demo.web.service;


import org.springframework.data.domain.Page;
import swlayer.project.demo.web.model.TrafficRekap;
import swlayer.project.demo.web.model.TrafficUser;

import javax.servlet.http.HttpServletRequest;

public interface TrafficUserService {

    TrafficUser testingConnection(String id);

    Page<TrafficRekap> showTrafficRecap(int page, int size);
    Page<TrafficUser> showTrafficUserAdmin(int page, int size);
}
