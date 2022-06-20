CREATE TABLE user_answer (
    id INT AUTO_INCREMENT PRIMARY KEY,
    is_correct TINYINT(1) NOT NULL,
    answer VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    question_id VARCHAR(255) NOT NULL,
    school_id VARCHAR(255) NOT NULL,
    create_date TIMESTAMP,
    update_date TIMESTAMP,
    CONSTRAINT fk_user_answers_user FOREIGN KEY (user_id) REFERENCES user(id),
    CONSTRAINT fk_user_question FOREIGN KEY(question_id) REFERENCES question(id)
)