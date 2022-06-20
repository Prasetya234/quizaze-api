package swlayer.project.demo.webrequest.dto;

import lombok.Getter;
import lombok.Setter;
import swlayer.project.demo.enggine.auditing.DateConfig;

@Getter
@Setter
public class QuestionResponse extends DateConfig {
    private String id;
    private String image;
    private String question;
    private String listAnswer;
    private String countUsed;
    private boolean publish;
}
