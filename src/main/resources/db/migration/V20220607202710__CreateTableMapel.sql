CREATE TABLE materi (
    id VARCHAR(255) PRIMARY KEY NOT NULL,
    description TEXT,
    question_total INT,
    teacher VARCHAR(255),
    school_id VARCHAR(255) NOT NULL,
    create_date TIMESTAMP,
    update_date TIMESTAMP,
    CONSTRAINT fk_materi_school FOREIGN KEY(school_id) REFERENCES school(id)
);

ALTER TABLE question ADD COLUMN materi_id VARCHAR(255) NOT NULL;
ALTER TABLE question ADD CONSTRAINT fk_question_materi FOREIGN KEY(materi_id) REFERENCES materi(id);
ALTER TABLE user_answer ADD COLUMN materi_id VARCHAR(255) NOT NULL;
ALTER TABLE user_answer ADD CONSTRAINT fk_user_answer_materi FOREIGN KEY(materi_id) REFERENCES materi(id);
