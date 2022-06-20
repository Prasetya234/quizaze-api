CREATE TABLE token_user (
    id VARCHAR(255) PRIMARY KEY NOT NULL,
    expired_date DATETIME NOT NULL,
    token VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    CONSTRAINT fk_token_user_user FOREIGN KEY(user_id) REFERENCES user(id)
)