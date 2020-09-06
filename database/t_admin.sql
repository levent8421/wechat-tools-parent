drop table if exists t_admin;

create table t_admin
(
    id                int(10)      not null primary key auto_increment comment 'Row Id',
    login_name        varchar(100) not null comment 'Login name',
    name              varchar(255) not null comment 'Account name',
    password          varchar(255) not null comment 'Login Password',
    authorization_str varchar(255) null comment 'authorization string',
    create_time       datetime     not null comment 'Row create time',
    update_time       datetime     not null comment 'last update time',
    deleted           bit(1)       not null comment 'delete flag'
) engine = InnoDb
  default charset utf8
  collate utf8_general_ci;

create unique index admin_login_name_unique_index on t_admin (login_name);

insert into t_admin (login_name, name, password, authorization_str, create_time, update_time, deleted)
    value ('root', 'root', '32fcfb0ea9713975d13a7202cbce3c09,simple', '', now(), now(), false);

select adm.id                adm_id,
       adm.login_name        adm_login_name,
       adm.name              adm_name,
       adm.password          adm_password,
       adm.authorization_str adm_authorization_str,
       adm.create_time       adm_create_time,
       adm.update_time       adm_update_time,
       adm.deleted           adm_deleted
from t_admin as adm
where deleted = false;
