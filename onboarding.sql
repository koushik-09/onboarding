create table user_details(
  user_id BIGINT NOT NULL AUTO_INCREMENT,
  user_name VARCHAR(30),
  first_name VARCHAR(30),
  last_name varchar(30),
  email VARCHAR(50) unique,
  phone_number BIGINT,
  gender char,
  dob DATE,
  isActive BOOLEAN DEFAULT TRUE,
    isAdmin BOOLEAN DEFAULT FALSE,
    created_on TIMESTAMP DEFAULT current_timestamp,
    updated_on TIMESTAMP DEFAULT current_timestamp,
    PRIMARY KEY (user_id)
);

ALTER TABLE user_details auto_increment = 1000;

create table user(
  id bigint not null auto_increment,
  user_id bigint unique,
  password VARCHAR(100),
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES user_details(user_id)
);

create table address(
    id bigint not null auto_increment,
    user_id bigint unique ,
    street VARCHAR(100),
    city varchar(100),
    state varchar(100),
    country varchar(100),
    pin_code bigint,
    created_on TIMESTAMP DEFAULT current_timestamp,
    updated_on TIMESTAMP DEFAULT current_timestamp,
    primary key (id),
    foreign key (user_id) REFERENCES user_details(user_id)
);

create table documents(
  id bigint not null auto_increment,
    user_id bigint,
    type int,
    url varchar(235),
    created_on TIMESTAMP DEFAULT current_timestamp,
    updated_on TIMESTAMP DEFAULT current_timestamp,
    primary key (id),
    foreign key (user_id) REFERENCES user_details(user_id)
);

CREATE TABLE `look_up` (
  id bigint NOT NULL AUTO_INCREMENT,
  column_name varchar(20),
  value varchar(50),
  actual_value varchar(50),
  PRIMARY KEY (`id`)
);

create table token(
  id bigint not null auto_increment,
  user_id bigint unique ,
  token varchar(200),
  created_on TIMESTAMP DEFAULT current_timestamp,
  updated_on TIMESTAMP DEFAULT current_timestamp,
  primary key (id),
  foreign key (user_id) REFERENCES user_details(user_id)
);

create table email_info(
   id bigint not null auto_increment,
   type VARCHAR(50) unique ,
   subject varchar(100),
   body VARCHAR(500),
   cc VARCHAR(235),
   bcc VARCHAR(235),
   created_by varchar(30) DEFAULT 'admin',
   created_on TIMESTAMP DEFAULT current_timestamp,
   updated_by varchar(30),
   updated_on TIMESTAMP DEFAULT current_timestamp,
   primary key (id)
);

create table user_auth(
  id bigint not null auto_increment,
  user_id bigint unique ,
  auth_token varchar(15),
  created_on TIMESTAMP DEFAULT current_timestamp,
  primary key (id)
);