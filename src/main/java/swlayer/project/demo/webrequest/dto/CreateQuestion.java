package swlayer.project.demo.webrequest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class CreateQuestion {
    private String materi;
    private String teacher;
    private String description;
    private Set<QuestionBody> question = new HashSet<>();
}
