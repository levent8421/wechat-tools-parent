drop table if exists t_wechat_user;

create table t_wechat_user
(
    id          int(10)      not null auto_increment primary key comment 'Row id',
    merchant_id int(10)      not null comment 'Merchant ID',
    w_nickname  varchar(255) null comment 'Wechat nickname',
    w_avatar    varchar(255) null comment 'Wechat avatar',
    w_open_id   varchar(255) not null comment 'Wechat open id',
    w_union_id  varchar(255) null comment 'Wechat union id',
    w_metadata  text         null comment 'Wechat metadata',
    create_time datetime     not null comment 'Create time',
    update_time datetime     not null comment 'Update time',
    deleted     bit(1)       not null comment 'Delete flag'
) engine = 'Innodb'
  charset utf8
  collate utf8_general_ci;


select wu.id          as wu_id,
       wu.merchant_id as wu_merchant_id,
       wu.w_nickname  as wu_w_nickname,
       wu.w_avatar    as wu_w_avatar,
       wu.w_open_id   as wu_w_open_id,
       wu.w_union_id  as wu_w_union_id,
       wu.w_metadata  as wu_w_metadata,
       wu.create_time as wu_create_time,
       wu.update_time as wu_update_time,
       wu.deleted     as wu_deleted
from t_wechat_user as wu
where wu.deleted = false;