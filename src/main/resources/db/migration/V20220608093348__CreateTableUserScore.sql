
CREATE TABLE user_score (
    id VARCHAR(255) PRIMARY KEY NOT NULL,
    score INT NOT NULL,
    point INT NOT NULL,
    school_id VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    materi_id VARCHAR(255) NOT NULL,
    count_question INT,
    create_date TIMESTAMP,
    update_date TIMESTAMP,
    CONSTRAINT fk_user_score_school FOREIGN KEY(school_id) REFERENCES school(id),
    CONSTRAINT fk_user_score_user FOREIGN KEY(user_id) REFERENCES user(id),
    CONSTRAINT fk_user_score_materu FOREIGN KEY(materi_id) REFERENCES materi(id)
)