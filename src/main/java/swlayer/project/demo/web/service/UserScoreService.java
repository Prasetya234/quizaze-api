package swlayer.project.demo.web.service;

import swlayer.project.demo.web.model.Materi;
import swlayer.project.demo.web.model.School;
import swlayer.project.demo.web.model.User;
import swlayer.project.demo.web.model.UserScore;

import java.util.List;
import java.util.Optional;

public interface UserScoreService {
    void create(School school, User user, Materi materi, int questionTotal);
    void update(String scoreId, int questionTotal);
    List<UserScore> getDataScore(String userId);
    UserScore getDataScoreMateri(String userId, String materiId);

    Optional<UserScore> getUserQUestion(String userId);
}
