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

select sc_a.id                as sc_a_id,
       sc_a.device_id         as sc_a_device_id,
       sc_a.type_code         as sc_a_type_code,
       sc_a.state_json_before as sc_a_state_json_before,
       sc_a.state_json_expect as sc_a_state_json_expect,
       sc_a.state_json_after  as sc_a_state_json_after,
       sc_a.start_time        as sc_a_start_time,
       sc_a.order_time        as sc_a_order_time,
       sc_a.complete_time     as sc_a_complete_time,
       sc_a.state_code        as sc_a_state_code,
       sc_a.response_msg      as sc_a_response_msg,
       sc_a.create_time       as sc_a_create_time,
       sc_a.update_time       as sc_a_update_time,
       sc_a.deleted           as sc_a_deleted
from t_super_ctl_action as sc_a
where sc_a.deleted = false;