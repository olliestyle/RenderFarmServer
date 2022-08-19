create table users (
       id serial primary key ,
       username varchar(32),
       password varchar(32)
);

create table task_statuses (
       id serial primary key ,
       status varchar(32)
);

create table tasks (
       id serial primary key ,
       timeToRender int,
       name varchar(256),
       created timestamp,
       done timestamp,
       task_status_id int references task_statuses(id),
       user_id int references users(id)
);

insert into task_statuses (status) values ('rendering'), ('complete');