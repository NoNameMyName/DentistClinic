--liquibase formatted sql

--changeset SmykovRoman:create-reservations-schema
--comment create new reservation schema
create schema reservations;
--rollback drop schema reservations;

--changeset SmykovRoman:create-reservations-doctor-table
--comment create table reservations.doctor
create table reservations.doctor (
    id serial primary key,
    user_account_id integer not null,
    speciality varchar(120) not null,
    experience integer not null
);
--rollback drop table reservations.doctor;

--changeset SmykovRoman:add-doctor-constraints
--comment add constraints to reservations.doctor
alter table reservations.doctor
    add constraint doctor__user_account__fk
        foreign key (user_account_id) references identity.user_accounts (id);
alter table reservations.doctor
    add constraint doctor__user_account_id__unique
        unique (user_account_id);
--rollback alter table reservations.doctor drop constraint doctor__user_account__fk;
--rollback alter table reservations.doctor drop constraint doctor__user_account_id__unique;

--changeset SmykovRoman:insert-default-values-into-reservations.doctor-constraints-table
--comment insert default values into reservations.doctor-constraints

INSERT INTO reservations.doctor (id, user_account_id, speciality, experience)
VALUES (1, 2, 'Dentist', 5);

--rollback DELETE FROM reservations.doctor WHERE id IN (1);

--changeset SmykovRoman:create-reservations-administrator-table
--comment create table reservations.administrator
create table reservations.administrator (
   id serial primary key,
   user_account_id integer not null
);
--rollback drop table reservations.administrator;

--changeset SmykovRoman:add-administrator-constraints
--comment add constraints to reservations.administrator
alter table reservations.administrator
    add constraint administrator__user_account__fk
        foreign key (user_account_id) references identity.user_accounts (id);
alter table reservations.administrator
    add constraint administrator__user_account_id__unique
        unique (user_account_id);
--rollback alter table reservations.administrator drop constraint administrator__user_account__fk;
--rollback alter table reservations.administrator drop constraint administrator__user_account_id__unique;

--changeset SmykovRoman:insert-default-values-into-reservations.administrator-constraints-table
--comment insert default values into reservations.administrator-constraints

INSERT INTO reservations.administrator (id, user_account_id)
VALUES (1, 1);

--rollback DELETE FROM reservations.administrator WHERE id IN (1);

--changeset SmykovRoman:create-reservations-reservation-table
--comment create table reservations.reservation
create table reservations.reservation (
  id serial primary key,
  reservation_description varchar(120) not null,
  reservation_date date not null,
  start_time time not null,
  end_time time not null,
  doctor_id integer not null,
  administrator_id integer,
  user_account_id integer not null,
  is_accepted boolean not null
);
--rollback drop table reservations.reservation;

--changeset SmykovRoman:add-reservations-constraints
--comment add constraints to reservations.reservation
alter table reservations.reservation
    add constraint reservations__doctor__fk
        foreign key (doctor_id) references reservations.doctor (id);
alter table reservations.reservation
    add constraint reservations__administrator__fk
        foreign key (administrator_id) references reservations.administrator (id);
alter table reservations.reservation
    add constraint reservations__user_account__fk
        foreign key (user_account_id) references identity.user_accounts (id);
--rollback alter table reservations.reservation drop constraint reservation__doctor__fk;
--rollback alter table reservations.reservation drop constraint reservation__administrator__fk;
--rollback alter table reservations.reservation drop constraint reservation__user_account__fk;