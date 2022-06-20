CREATE TABLE school (
    id VARCHAR(255) PRIMARY KEY NOT NULL,
    head_master VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    create_date TIMESTAMP NOT NULL,
    update_date TIMESTAMP,
    update_by VARCHAR(255)
)