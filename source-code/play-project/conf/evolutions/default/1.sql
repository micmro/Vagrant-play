# --- !Ups
CREATE TABLE Users (
  id INT NOT NULL AUTO_INCREMENT,
  username VARCHAR(50) UNIQUE NOT NULL,
  password VARCHAR(50) NOT NULL,
  PRIMARY KEY (id)
);

# --- !Downs
DROP TABLE Users;
