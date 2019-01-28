CREATE TABLE IF NOT EXISTS boarduser (
  id SERIAL PRIMARY KEY,
  username VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS authority (
  id SERIAL PRIMARY KEY,
  authority_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS user_authorities (
  id SERIAL PRIMARY KEY,
  user_id INTEGER REFERENCES boarduser(id),
  authority_id INTEGER REFERENCES authority(id)
);

CREATE TABLE IF NOT EXISTS topic (
  id SERIAL PRIMARY KEY,
  original_poster INTEGER REFERENCES boarduser(id),
  topic_name VARCHAR (255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS post (
  id SERIAL PRIMARY KEY,
  poster INTEGER REFERENCES boarduser(id),
  content TEXT NOT NULL
);