CREATE TABLE users (
                      id VARCHAR(255) PRIMARY KEY NOT NULL,
                      avatar TEXT,
                      username VARCHAR(255),
                      password VARCHAR(255) NOT NULL,
                      email VARCHAR(255),
                      role VARCHAR(255) NOT NULL,
                      device VARCHAR(255) NOT NULL,
                      blocked BOOLEAN NOT NULL,
                      guest BOOLEAN NOT NULL,
                      school_id VARCHAR(255),
                      create_date TIMESTAMP,
                      update_date TIMESTAMP

);