CREATE TABLE user_traffic (
    id VARCHAR(255) PRIMARY KEY NOT NULL,
    visitors INT NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    school_id VARCHAR(255),
    create_date TIMESTAMP,
    update_date TIMESTAMP,
    CONSTRAINT fk_user_traffic_user FOREIGN KEY(user_id) REFERENCES user(id),
    CONSTRAINT fk_user_school FOREIGN KEY(school_id) REFERENCES school(id)
)