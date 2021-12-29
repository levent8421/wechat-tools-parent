drop table if exists t_super_ctl_action;

create table t_super_ctl_action
(
    id                int(10)      not null auto_increment primary key,
    device_id         int(10)      not null,
    type_code         varchar(100) not null,
    state_json_before text         null,
    state_json_expect text         not null,
    state_json_after  text         null,
    order_time        datetime     null,
    start_time        datetime     not null,
    complete_time     datetime     null,
    state_code        varchar(100) not null,
    response_msg      varchar(255) null,
    create_time       datetime     not null,
    update_time       datetime     not null,
    deleted           bit(1)       not null
) engine = Innodb
  charset utf8mb4
  collate utf8mb4_general_ci;


select id                as sc_a_id,
       device_id         as sc_a_device_id,
       type_code         as sc_a_type_code,
       state_json_before as sc_a_state_json_before,
       state_json_expect as sc_a_state_json_expect,
       state_json_after  as sc_a_state_json_after,
       start_time        as sc_a_start_time,
       complete_time     as sc_a_complete_time,
       state_code        as sc_a_state_code,
       response_msg      as sc_a_response_msg,
       create_time       as sc_a_create_time,
       update_time       as sc_a_update_time,
       deleted           as sc_a_deleted
from t_super_ctl_action as sc_a
where sc_a.deleted = false;