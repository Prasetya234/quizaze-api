CREATE TABLE question (
    id VARCHAR(255) PRIMARY KEY NOT NULL,
    image TEXT NOT NULL,
    question VARCHAR(255) NOT NULL,
    animal_name VARCHAR(255) NOT NULL,
    answer_true VARCHAR(255) NOT NULL,
    answer_list VARCHAR(255) NOT NULL,
    count_used INT NOT NULL,
    publish TINYINT(1) NOT NULL,
    school_id VARCHAR(255) NOT NULL,
    create_date TIMESTAMP,
    update_date TIMESTAMP

)