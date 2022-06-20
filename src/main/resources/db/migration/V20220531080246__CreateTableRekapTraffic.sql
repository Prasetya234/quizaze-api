CREATE TABLE traffic_recap (
    id INT PRIMARY KEY NOT NULL,
    visitors INT NOT NULL,
    this_date DATE NOT NULL,
    school_id VARCHAR(255),
    create_date TIMESTAMP,
    update_date TIMESTAMP,
    CONSTRAINT fk_traffic_recap_school FOREIGN KEY(school_id) REFERENCES school(id)
)