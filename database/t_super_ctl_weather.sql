drop table if exists t_super_ctl_weather;

create table t_super_ctl_weather
(
    id                int(10)        not null auto_increment primary key,
    address           varchar(255)   not null,
    title             varchar(255)   not null,
    temperature       decimal(10, 3) null,
    humidity          decimal(10, 3) null,
    in_rain           bit(1)         not null,
    ext_info          text           null,
    last_refresh_time datetime       null,
    create_time       datetime       not null,
    update_time       datetime       not null,
    deleted           bit(1)         not null
) engine = Innodb
  charset utf8mb4
  collate utf8mb4_general_ci;


select sc_w.id                as sc_w_id,
       sc_w.address           as sc_w_address,
       sc_w.title             as sc_w_title,
       sc_w.temperature       as sc_w_temperature,
       sc_w.humidity          as sc_w_humidity,
       sc_w.in_rain           as sc_w_in_rain,
       sc_w.ext_info          as sc_w_ext_info,
       sc_w.last_refresh_time as sc_w_last_refresh_time,
       sc_w.create_time       as sc_w_create_time,
       sc_w.update_time       as sc_w_update_time,
       sc_w.deleted           as sc_w_deleted
from t_super_ctl_weather sc_w
where sc_w.deleted = false;
