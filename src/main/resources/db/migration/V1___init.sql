CREATE TABLE accounts (
  id           MEDIUMINT    NOT NULL AUTO_INCREMENT PRIMARY KEY,
  identifier   VARCHAR(255) NOT NULL UNIQUE,
  organisation VARCHAR(255) NOT NULL,
  account_type VARCHAR(255) NOT NULL,
  linked_id    VARCHAR(255) NOT NULL,
  created      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
)
  ENGINE = InnoDB;
ALTER TABLE accounts
  ADD INDEX accounts_identifier_index (identifier);