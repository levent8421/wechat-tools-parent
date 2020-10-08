drop table if exists t_invite_follow_draw;

create table t_invite_follow_draw
(
    id                   int(10)  not null comment '',
    invite_follow_app_id int(10)  not null comment 'App id',
    wechat_user_id       int(10)  not null comment 'Wechat User id',
    state                int(3)   not null comment 'State code',
    prize_id             int(10)  null comment 'InviteFollowPrize id',
    phone                varchar(50)  null comment 'phone number',
    create_time          datetime not null comment 'Row Create time',
    update_time          datetime not null comment 'Row Last modify time',
    deleted              bit(1)   not null comment 'Delete flag'
) engine = 'Innodb'
  default charset utf8
  collate utf8_general_ci;


select ifd.id                   as ifd_id,
       ifd.invite_follow_app_id as ifd_invite_follow_app_id,
       ifd.wechat_user_id       as ifd_wechat_user_id,
       ifd.state                as ifd_state,
       ifd.prize_id             as ifd_prize_id,
       ifd.phone                as ifd_phone,
       ifd.create_time          as ifd_create_time,
       ifd.update_time          as ifd_update_time,
       ifd.deleted              as ifd_deleted
from t_invite_follow_draw as ifd
where ifd.deleted = false;