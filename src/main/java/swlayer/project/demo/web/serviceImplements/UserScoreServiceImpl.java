package swlayer.project.demo.web.serviceImplements;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swlayer.project.demo.enggine.exception.BussinesException;
import swlayer.project.demo.enggine.exception.NotFoundException;
import swlayer.project.demo.web.model.Materi;
import swlayer.project.demo.web.model.School;
import swlayer.project.demo.web.model.User;
import swlayer.project.demo.web.model.UserScore;
import swlayer.project.demo.web.repository.UserScoreRepository;
import swlayer.project.demo.web.service.UserScoreService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class UserScoreServiceImpl implements UserScoreService {
    private UserScoreRepository userScoreRepository;
    @Autowired
    public UserScoreServiceImpl(UserScoreRepository userScoreRepository) {
        this.userScoreRepository = userScoreRepository;
    }
    @Transactional
    @Override
    public void create(School school, User user, Materi materi, int questionTotal) {
        if (school == null) throw new BussinesException("you have not entered the school in question");
        UserScore userScore = new UserScore();
        userScore.setScore(0);
        userScore.setPoint(0);
        userScore.setTotalQuestionAnswer(0);
        userScore.setCountQuestion(questionTotal);
        userScore.setSchool(school);
        userScore.setUser(user);
        userScore.setMateri(materi);
        userScoreRepository.save(userScore);
    }

    @Override
    public void update(String scoreid, int questionTotal) {
        var score = userScoreRepository.findById(scoreid).get();
        score.setCountQuestion(questionTotal);
        userScoreRepository.save(score);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserScore> getDataScore(String userId) {
        return Collections.singletonList(userScoreRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException("User id not found")));
    }
    @Transactional(readOnly = true)
    @Override
    public UserScore getDataScoreMateri(String userId, String materiId) {
        return userScoreRepository.findByUserIdAndMateriId(userId, materiId).orElseThrow(() -> new NotFoundException("User or Materi ID not found"));
    }
    @Transactional(readOnly = true)
    @Override
    public Optional<UserScore> getUserQUestion(String userId, String materiId) {
        return userScoreRepository.findByUserIdAndMatriId(userId, materiId);
    }
}


















