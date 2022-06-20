package swlayer.project.demo.webrequest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class QuestionBody {
    private String id;
    private String image;
    private String question;
    private String answerTrue;
//    private boolean publish;
    private List<String> answerList = new ArrayList<>();
}
