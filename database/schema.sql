BEGIN TRANSACTION;

DROP TABLE IF EXISTS article_author;
DROP TABLE IF EXISTS tags;
DROP TABLE IF EXISTS article;
DROP TABLE IF EXISTS columns;
DROP TABLE IF EXISTS journal;
DROP TABLE IF EXISTS author;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
                       user_id SERIAL,
                       username varchar(50) NOT NULL UNIQUE,
                       password_hash varchar(200) NOT NULL,
                       role varchar(50) NOT NULL,
                       CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE author (
                        author_id SERIAL PRIMARY KEY,
                        author_name VARCHAR(255) NOT NULL,
                        affiliations TEXT
);

CREATE TABLE journal (
                         journal_id SERIAL PRIMARY KEY,
                         date_issued DATE,
                         editor_id INT,
                         volume_issue_id INT
);

CREATE TABLE columns (
                         column_id SERIAL PRIMARY KEY,
                         author_id INT,
                         name VARCHAR(255) NOT NULL,
                         FOREIGN KEY (author_id) REFERENCES author(author_id)
);

CREATE TABLE article (
                         article_id SERIAL PRIMARY KEY,
                         author_id INT,
                         journal_id INT,
                         title VARCHAR(255) NOT NULL,
                         page_number INT,
                         abstract TEXT,
                         article_content TEXT,
                         column_id INT,
                         FOREIGN KEY (author_id) REFERENCES author(author_id),
                         FOREIGN KEY (journal_id) REFERENCES journal(journal_id),
                         FOREIGN KEY (column_id) REFERENCES columns(column_id)
);

CREATE TABLE tags (
                      tag_id SERIAL PRIMARY KEY,
                      column_id INT,
                      author_id INT,
                      name VARCHAR(255) NOT NULL,
                      FOREIGN KEY (column_id) REFERENCES columns(column_id),
                      FOREIGN KEY (author_id) REFERENCES author(author_id)
);

CREATE TABLE article_author (
                                article_id INT,
                                author_id INT,
                                PRIMARY KEY (article_id, author_id),
                                FOREIGN KEY (article_id) REFERENCES article(article_id),
                                FOREIGN KEY (author_id) REFERENCES author(author_id)
);

CREATE INDEX idx_article_author ON article(author_id);
CREATE INDEX idx_article_journal ON article(journal_id);
CREATE INDEX idx_article_column ON article(column_id);
CREATE INDEX idx_column_author ON columns(author_id);
CREATE INDEX idx_tag_column ON tags(column_id);
CREATE INDEX idx_tag_author ON tags(author_id);

COMMIT TRANSACTION;