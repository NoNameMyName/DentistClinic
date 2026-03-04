--liquidbase formatted sql

--changeset SmykovRoman:create-identity-schema
--comment create new identity schema
create schema identity;
--rollback drop schema identity;

--changeset SmykovRoman:create-identity-user_accounts-table
--comment create table identity.user_accounts
create table identity.user_accounts
(
    id          serial primary key,
    email       varchar(32) unique,
    password    varchar(128) not null ,
    first_name   varchar(50) not null ,
    last_name    varchar(80) not null ,
    phone_number varchar(12) not null
);
--rollback drop table identity.user_accounts;

--changeset SmykovRoman:create-identity-user_roles-table
--comment create table identity.user_roles
create table identity.user_roles(
    id          serial primary key,
    authority   varchar(32) unique not null
);
--rollback drop table identity.user_roles;

--changeset SmykovRoman:create-identity-user_accounts_roles-table
--comment create table identity.user_accounts_roles
create table identity.user_accounts_roles(
    user_account_id integer not null ,
    user_role_id integer not null
);
--rollback drop table identity.user_accounts_roles;

--changeset SmykovRoman:add-user_accounts_roles-constraints
--comment add constraints to user_accounts_roles
alter table identity.user_accounts_roles
    add constraint user_accounts_roles__user_roles__fk
        foreign key (user_role_id) references identity.user_roles (id);

alter table identity.user_accounts_roles
    add constraint user_accounts_roles__user_accounts__fk
        foreign key (user_account_id) references identity.user_accounts (id);

alter table identity.user_accounts_roles
    add constraint user_accounts_roles_unique
        unique (user_account_id, user_role_id);
--rollback alter table identity.user_accounts_roles drop constraint user_accounts_roles__user_roles__fk;
--rollback alter table identity.user_accounts_roles drop constraint user_accounts_roles__user_accounts__fk;
--rollback alter table identity.user_accounts_roles drop constraint user_accounts_roles_unique;
