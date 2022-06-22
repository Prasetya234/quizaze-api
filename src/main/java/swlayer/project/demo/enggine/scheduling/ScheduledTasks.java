package swlayer.project.demo.enggine.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import swlayer.project.demo.web.model.School;
import swlayer.project.demo.web.model.TrafficRekap;
import swlayer.project.demo.web.repository.SchoolRepository;
import swlayer.project.demo.web.repository.TrafficRecapRepository;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Component
public class ScheduledTasks {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private TrafficRecapRepository trafficRecapRepository;
    private SchoolRepository schoolRepository;

    @Autowired
    public ScheduledTasks(TrafficRecapRepository trafficRecapRepository, SchoolRepository schoolRepository) {
        this.trafficRecapRepository = trafficRecapRepository;
        this.schoolRepository = schoolRepository;
    }

    // at 1:01 AM every day
    @Scheduled(cron = "0 1 1 * * ?", zone = "Asia/Jakarta")
    public void getHeadValue() {
        int countSchoolTraffic = 0;
        try {
            List<School> listSchool = schoolRepository.findAll();
            for (School school: listSchool) {
                var valid = trafficRecapRepository.findSchoolDate(school.getId(), getDate(new Date()));
                if (valid.isEmpty()) {
                    TrafficRekap create = new TrafficRekap();
                    create.setVisitors(0);
                    create.setThisDate(new Date());
                    create.setSchool(school);
                    trafficRecapRepository.save(create);
                    countSchoolTraffic++;
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            log.info("Total rendering recap school: {}", countSchoolTraffic);
            log.info("Schaduling traffic recap runing: {}", new Date());
        }
    }

    private String getDate(Date date) {
        return LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(date)).toString();
    }

}
