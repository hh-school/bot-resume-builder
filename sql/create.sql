CREATE TABLE IF NOT EXISTS area (
  id   SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS "user" (
  id               SERIAL PRIMARY KEY,
  telegram_id      INT UNIQUE               NOT NULL,
  phone            VARCHAR(15)
  birth_date       DATE,
  first_name       VARCHAR(100),
  last_name        VARCHAR(100),
  area_id          INT REFERENCES area (id),
  gender           CHAR(1),
  career_objective VARCHAR(255),
  salary_amount    INT,
  salary_currency  VARCHAR(3),
  create_datetime  TIMESTAMP WITH TIME ZONE,
  node_id          INT,
  node_relation_id INT
);

CREATE TABLE IF NOT EXISTS specialization (
  id   SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS user__specialization (
  user_id           INT REFERENCES "user" (id)         NOT NULL,
  specialization_id INT REFERENCES specialization (id) NOT NULL,
  UNIQUE (user_id, specialization_id)
);

CREATE TABLE IF NOT EXISTS company (
  id   SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS experience (
  id           SERIAL PRIMARY KEY,
  user_id      INT REFERENCES "user" (id) NOT NULL,
  company_id   INT REFERENCES company (id),
  company_name VARCHAR(255),
  company_url  VARCHAR(255),
  area_id      INT REFERENCES area (id),
  position     VARCHAR(255)               NOT NULL,
  start_date   DATE,
  end_date     DATE,
  description  VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS industry (
  id   SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS experience__industry (
  experience_id INT REFERENCES experience (id) NOT NULL,
  industry_id   INT REFERENCES industry (id)   NOT NULL,
  UNIQUE (experience_id, industry_id)
);

CREATE TABLE IF NOT EXISTS education (
  id               SERIAL PRIMARY KEY,
  user_id          INT REFERENCES "user" (id) NOT NULL,
  year             INT                        NOT NULL,
  institution_name VARCHAR(100),
  faculty_name     VARCHAR(100),
  speciality_name  VARCHAR(100),
  level            VARCHAR(20)
);
