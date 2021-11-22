drop table if exists t_super_ctl_app;


create table t_super_ctl_app
(
    id          int(10)      not null auto_increment primary key,
    merchant_id int(10)      not null,
    app_name    varchar(255) not null,
    description text         null,
    expire_time datetime     null,
    create_time datetime     not null,
    update_time datetime     not null,
    deleted     bit(1)       not null
) engine = Innodb
  charset utf8mb4
  collate utf8mb4_general_ci;



select sc_app.id          as sc_app_id,
       sc_app.merchant_id as sc_app_merchant_id,
       sc_app.app_name    as sc_app_app_name,
       sc_app.description as sc_app_description,
       sc_app.expire_time as sc_app_expire_time,
       sc_app.create_time as sc_app_create_time,
       sc_app.update_time as sc_app_update_time,
       sc_app.deleted     as sc_app_deleted
from t_super_ctl_app as sc_app
where deleted = false;