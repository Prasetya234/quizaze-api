package swlayer.project.demo.web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import swlayer.project.demo.enggine.response.CommonResponse;
import swlayer.project.demo.enggine.response.ResponseHelper;
import swlayer.project.demo.web.model.TrafficRekap;
import swlayer.project.demo.web.model.TrafficUser;
import swlayer.project.demo.web.service.TrafficUserService;
import swlayer.project.demo.webrequest.dto.TrafficResponse;
import swlayer.project.demo.webrequest.util.Constants;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@RestController
@RequestMapping(Constants.SWLAYEREndPoint.TRAFFIC)
public class TrafficController {
    private ModelMapper modelMapper;
    private TrafficUserService trafficUserService;

    @Autowired
    public TrafficController(ModelMapper modelMapper, TrafficUserService trafficUserService) {
        this.modelMapper = modelMapper;
        this.trafficUserService = trafficUserService;
    }

    @GetMapping("/trafic-global-user/connect")
    public CommonResponse<TrafficResponse> traffic(@RequestParam(name = "userId", required = false) String id) {
        return ResponseHelper.successResponse(modelMapper.map(trafficUserService.testingConnection(id), TrafficResponse.class));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/traffic-user/list")
    public CommonResponse<Page<TrafficUser>> showTrafficUserAdmin(@RequestParam(defaultValue = "0") Integer page,
                                                                      @RequestParam(defaultValue = "10") Integer size) {
        return ResponseHelper.successResponse(trafficUserService.showTrafficUserAdmin(page, size));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'ADMIN_SCHOOL')")
    @GetMapping("/admin/traffic-user/list/perday")
    public CommonResponse<Page<TrafficRekap>> showTrafficUserRecap(@RequestParam(defaultValue = "0") Integer page,
                                                                   @RequestParam(defaultValue = "10") Integer size) {
        return ResponseHelper.successResponse(trafficUserService.showTrafficRecap(page, size));
    }

}
