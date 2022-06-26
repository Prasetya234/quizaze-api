package swlayer.project.demo.web.serviceImplements;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swlayer.project.demo.enggine.data.AuthenticationFacade;
import swlayer.project.demo.enggine.exception.BussinesException;
import swlayer.project.demo.enggine.exception.NotFoundException;
import swlayer.project.demo.web.model.Materi;
import swlayer.project.demo.web.model.Question;
import swlayer.project.demo.web.repository.*;
import swlayer.project.demo.web.service.MateriService;
import swlayer.project.demo.web.service.QuestionService;
import swlayer.project.demo.web.service.UserScoreService;
import swlayer.project.demo.webrequest.dto.CreateQuestion;
import swlayer.project.demo.webrequest.dto.QuestionBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Slf4j
@Service
public class QuestionServiceImpl extends AuthenticationFacade implements QuestionService, MateriService {

    private UserScoreService userScoreService;
    private QuestionRepository questionRepository;
    private UserRepository userRepository;
    private MateriRepository materiRepository;
    private SchoolRepository schoolRepository;

    @Autowired
    public QuestionServiceImpl(UserScoreService userScoreService, QuestionRepository questionRepository, UserRepository userRepository, MateriRepository materiRepository, SchoolRepository schoolRepository) {
        this.userScoreService = userScoreService;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.materiRepository = materiRepository;
        this.schoolRepository = schoolRepository;
    }

    @Transactional
    @Override
    public Materi createQuestion(String schoolId, CreateQuestion question) {
        Materi materi = create(schoolId, question);
        List<Question> soal = new ArrayList<>();
        for (var result : question.getQuestion()) {
            List<String> answerList = result.getAnswerList();
            Question create = new Question();
            create.setAnswerTrue(answerList.stream().filter(x -> result.getAnswerTrue().toLowerCase().equals(x.toLowerCase())).findAny().orElseThrow(() -> new NotFoundException("answer true is not the same as answer list")));
            create.setQuestion(result.getQuestion());
            create.setImage(result.getImage());
            create.setPublish(true);
            create.setCountUsed(0);
            create.setAnswerList(answerList.stream().map(Object::toString).collect(Collectors.joining(",")));
            create.setSchool(schoolRepository.findById(schoolId).orElseThrow(() -> new NotFoundException("School id not found")));
            create.setMateri(materi);
            soal.add(create);
        }
        materi.setQuestionTotal(totalQuestionList(soal));
        materiRepository.save(materi);
        questionRepository.saveAll(soal);
        return getQuestion(materi.getId(), true);
    }

    @Transactional(readOnly = true)
    @Override
    public Question getQUestionById(String id, String materiId) {
        Question arr = questionRepository.findByIdAndMateriIdAndPublishIsTrue(id, materiId).orElseThrow(() -> new NotFoundException("Question ID or Materi ID not found"));
        arr.setListAnswer(List.of(shuffleArray(arr.getAnswerList().split(","))));
        arr.setAnswerTrue("");
        return arr;
    }

    @Transactional
    @Override
    public Materi create(String schoolId, CreateQuestion question) {
        Materi materi = new Materi();
        materi.setMateri(question.getMateri());
        materi.setTeacher(question.getTeacher());
        materi.setDescription(question.getDescription());
        materi.setQuestionTotal(0);
        materi.setSchool(schoolRepository.findById(schoolId).orElseThrow(() -> new NotFoundException("School id not found")));
        return materiRepository.save(materi);
    }

//    @Transactional(readOnly = true)
    @Override
    public Materi showMateriUser(String materiId) {
        var auth = userRepository.findByUsernameAndBlockedIsFalse(getAuthentication().getName()).orElseThrow(() -> new BussinesException("User cannot use this question"));
        Materi result = materiRepository.findById(materiId).orElseThrow(() -> new NotFoundException("Matri id not found"));
        var score =  userScoreService.getUserQUestion(auth.getId());
        if (score.isEmpty()){
         userScoreService.create(auth.getSchool(), auth, result, result.getQuestionTotal());
        }
        userScoreService.update(score.get().getId(), result.getQuestionTotal());
        return getQuestion(materiId, false);
    }

    @Transactional(readOnly = true)
    @Override
    public Materi showMateriAdmin(String materiId) {
        return getQuestion(materiId, true);
    }

    @Transactional
    @Override
    public Materi update(String materiId, CreateQuestion question) {
        Materi materi = materiRepository.findById(materiId).orElseThrow(() -> new NotFoundException("Materi id " + materiId + " not found"));
        List<Question> listQuestion = getAllQuestionAdmin(materiId);
        listQuestion.forEach((e) -> e.setPublish(false));
        materi.setMateri(question.getMateri());
        materi.setDescription(question.getDescription());
        materi.setTeacher(question.getTeacher());
        for (QuestionBody loop : question.getQuestion()) {
            if (loop.getId().isBlank() || loop.getId().isEmpty()) {
                listQuestion = updateQuestion(listQuestion, loop, materi);
            } else {
                boolean istrue = false;
                for (Question res : listQuestion) {
                    if (loop.getId().equals(res.getId())) {
                        res.setImage(loop.getImage());
                        res.setQuestion(loop.getQuestion());
                        res.setPublish(true);
                        res.setAnswerTrue(loop.getAnswerTrue());
                        res.setAnswerList(loop.getAnswerList().stream().map(Object::toString).collect(Collectors.joining(",")));
                        istrue = true;
                    }
                }
                if (!istrue) {
                    listQuestion = updateQuestion(listQuestion, loop, materi);
                }
            }

        }
        materi.setQuestionTotal(totalQuestionList(listQuestion));
        materiRepository.save(materi);
        questionRepository.saveAll(listQuestion);
        return getQuestion(materiId, true);
    }

    private List<Question> updateQuestion(List<Question> listQuestion, QuestionBody loop, Materi materi) {
        Question crt = new Question();
        crt.setImage(loop.getImage());
        crt.setQuestion(loop.getQuestion());
        crt.setAnswerTrue(loop.getAnswerTrue());
        crt.setAnswerList(loop.getAnswerList().stream().map(Object::toString).collect(Collectors.joining(",")));
        crt.setMateri(materi);
        crt.setPublish(true);
        crt.setSchool(materi.getSchool());
        listQuestion.add(crt);
        return listQuestion;
    }

    @Transactional(readOnly = true)
    @Override
    public Materi getQuestion(String materiId, boolean admin) {
        Materi result = materiRepository.findById(materiId).orElseThrow(() -> new NotFoundException("Matri id not found"));
        result.setQuestion(admin ? getAllQuestionAdmin(result.getId()) : getAllQuestionUser(result.getId()));
        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Materi> findAllMateri(String materi, int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        return materiRepository.searchMateri(materi, paging);
    }

    private static String[] shuffleArray(String[] ar) {
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            String a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
        return ar;
    }


    private List<Question> getAllQuestionAdmin(String materiId) {
        List<Question> arr = new ArrayList<>();
        List<Question> result = questionRepository.findByMateriIdAndPublishIsTrueOrderByCreateAtAsc(materiId);
        if (!result.isEmpty()) {
            result.forEach((e) -> e.setListAnswer(List.of(shuffleArray(e.getAnswerList().split(",")))));
            arr = result;
        }
        return arr;
    }

    private List<Question> getAllQuestionUser(String materiId) {
        List<Question> arr = new ArrayList<>();
        List<Question> result = questionRepository.findByMateriIdAndPublishIsTrueUser(materiId);
        List<Question> dat = result;
        if (!dat.isEmpty()) {
            result.forEach((e) -> {
                e.setListAnswer(List.of(shuffleArray(e.getAnswerList().split(","))));
                e.setAnswerTrue("");
            });
            arr = dat;
        }
        return arr;
    }

    private int totalQuestionList(List<Question> list){
        int result = 0;
        for (var s: list) {
            if (s.isPublish()) {
                result++;
            }
        }
        return result;
    }

}
