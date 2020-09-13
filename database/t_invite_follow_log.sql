drop table if exists t_invite_follow_log;

create table t_invite_follow_log
(
    id           int(10)  not null auto_increment primary key comment 'Row ID',
    user_id      int(10)  not null comment 'User id, ref to t_wechat_user.id',
    inviter_id   int(10)  not null comment 'Inviter user id, ref to t_wechat_user.id',
    merchant_id  int(10)  not null comment 'Merchant id',
    stay_focused bit(1)   not null comment 'User stay focused flag',
    create_time  datetime not null comment 'Row create time',
    update_time  datetime not null comment 'Row update time',
    deleted      bit(1)   not null comment 'Deleted flag'
) engine = 'Innodb'
  charset utf8
  collate utf8_general_ci;

select ifl.id           as ifl_id,
       ifl.user_id      as ifl_user_id,
       ifl.inviter_id   as ifl_inviter_id,
       ifl.merchant_id  as ifl_merchant_id,
       ifl.stay_focused as ifl_stay_focused,
       ifl.create_time  as ifl_create_time,
       ifl.update_time  as ifl_update_time,
       ifl.deleted      as ifl_deleted
from t_invite_follow_log as ifl
where ifl.deleted = false;