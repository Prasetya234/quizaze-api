ALTER TABLE users ADD CONSTRAINT fk_school_user FOREIGN KEY(school_id) REFERENCES school(id);
ALTER TABLE question ADD CONSTRAINT fk_question_school FOREIGN KEY(school_id) REFERENCES school(id);
ALTER TABLE user_answer ADD CONSTRAINT fk_user_answer_school FOREIGN KEY(school_id) REFERENCES school(id);