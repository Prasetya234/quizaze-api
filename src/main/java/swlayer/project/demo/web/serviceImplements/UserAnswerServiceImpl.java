package swlayer.project.demo.web.serviceImplements;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swlayer.project.demo.enggine.data.AuthenticationFacade;
import swlayer.project.demo.enggine.exception.BussinesException;
import swlayer.project.demo.enggine.exception.NotFoundException;
import swlayer.project.demo.web.model.User;
import swlayer.project.demo.web.model.UserAnswer;
import swlayer.project.demo.web.repository.*;
import swlayer.project.demo.web.service.UserAnswerService;
import swlayer.project.demo.webrequest.dto.AnswerData;

@Slf4j
@Service
public class UserAnswerServiceImpl extends AuthenticationFacade implements UserAnswerService {


    private UserRepository userRepository;
    private QuestionRepository questionRepository;
    private UserScoreRepository userScoreRepository;
    private UserAnswerRepository userAnswerRepository;

    @Autowired
    public UserAnswerServiceImpl(UserRepository userRepository, QuestionRepository questionRepository, UserScoreRepository userScoreRepository, UserAnswerRepository userAnswerRepository) {
        this.userRepository = userRepository;
        this.userScoreRepository = userScoreRepository;
        this.questionRepository = questionRepository;
        this.userAnswerRepository = userAnswerRepository;
    }

    @Transactional
    @Override
    public UserAnswer answerQuestion(String questionId, AnswerData answer) {
        var quest = questionRepository.findById(questionId).orElseThrow(() -> new NotFoundException("Question id not found"));
        var op = userAnswerRepository.findByQuestionId(questionId);
        if (op.isPresent()) throw new BussinesException("You has been already answer");
        User user = userRepository.findByUsernameAndBlockedIsFalse(getAuthentication().getName()).get();
        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setCorrect(answer.getAnswer().toLowerCase().equals(quest.getAnswerTrue().toLowerCase()));
        userAnswer.setAnswer(answer.getAnswer());
        userAnswer.setUser(user);
        userAnswer.setSchool(user.getSchool());
        userAnswer.setQuestion(quest);
        userAnswer.setMateri(quest.getMateri());
        var res = userAnswerRepository.save(userAnswer);
        quest.setCountUsed(quest.getCountUsed() +1);
        questionRepository.save(quest);
        var score = userScoreRepository.findByUserIdAndMateriId(res.getUser().getId(), res.getMateri().getId()).orElseThrow(() -> new BussinesException("Score data not found"));
        score.setPoint(res.isCorrect() ? score.getPoint() + 1 : score.getPoint());
        score.setTotalQuestionAnswer(score.getTotalQuestionAnswer()+1);
        score.setScore(getScoreUser(score.getPoint(), score.getCountQuestion()));
        userScoreRepository.save(score);
        return res;
    }
    private int getScoreUser(int point, int question){
        int sal = 0;
        for (int i = point; i < question; i++) {
            sal++;
        }
        return (100 / question) * (question - sal);
    }
}
