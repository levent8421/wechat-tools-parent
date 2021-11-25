drop table if exists t_super_ctl_device;

create table t_super_ctl_device
(
    id                  int(10)      not null auto_increment primary key,
    sn                  varchar(255) not null,
    wechat_user_id      int(10)      not null,
    device_name         varchar(255) not null,
    address             varchar(255) null,
    last_heartbeat_time datetime     null,
    status_json         text         not null,
    create_time         datetime     not null,
    update_time         datetime     not null,
    deleted             bit(1)       not null
) engine = Innodb
  charset utf8mb4
  collate utf8mb4_general_ci;


select sc_d.id                  as sc_d_id,
       sc_d.sn                  as sc_d_sn,
       sc_d.wechat_user_id      as sc_d_wechat_user_id,
       sc_d.device_name         as sc_d_device_name,
       sc_d.address             as sc_d_address,
       sc_d.last_heartbeat_time as sc_d_last_heartbeat_time,
       sc_d.status_json         as sc_d_status_json,
       sc_d.create_time         as sc_d_create_time,
       sc_d.update_time         as sc_d_update_time,
       sc_d.deleted             as sc_d_deleted
from t_super_ctl_device as sc_d
where sc_d.deleted = false;
