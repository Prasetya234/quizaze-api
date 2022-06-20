package swlayer.project.demo.web.service;

import swlayer.project.demo.web.model.UserAnswer;
import swlayer.project.demo.webrequest.dto.AnswerData;

public interface UserAnswerService {
    UserAnswer answerQuestion(String questionId, AnswerData answer);
}
