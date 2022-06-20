package swlayer.project.demo.web.service;

import swlayer.project.demo.web.model.Materi;
import swlayer.project.demo.web.model.Question;
import swlayer.project.demo.webrequest.dto.CreateQuestion;

import java.util.List;

public interface QuestionService {
    Materi createQuestion(String schoolId, CreateQuestion question);

    Question getQUestionById(String id, String materiId);

}
