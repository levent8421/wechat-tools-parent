drop table if exists t_invite_follow_prize;

create table t_invite_follow_prize
(
    id                   int(10)      not null auto_increment primary key comment 'Row id',
    invite_follow_app_id int(10)      not null comment 'App id',
    name                 varchar(255) not null comment 'Prize name',
    image                varchar(255) null comment 'Prize icon image',
    total_stock          int(5)       not null comment 'Total stock',
    winning_rate         int(3)       not null comment '中奖率',
    sales                int(5)       not null comment 'Sales count',
    state                int(2)       not null comment 'State code',
    create_time          datetime     not null comment 'Row create time',
    update_time          datetime     not null comment 'Row last modify time',
    deleted              bit(1)       not null comment 'Delete flag'
) engine = 'Innodb'
  default charset utf8
  collate utf8_general_ci;

select ifp.id                   as ifp_id,
       ifp.invite_follow_app_id as ifp_invite_follow_app_id,
       ifp.name                 as ifp_name,
       ifp.image                as ifp_image,
       ifp.total_stock          as ifp_total_stock,
       ifp.winning_rate         as ifp_winning_rate,
       ifp.sales                as ifp_sales,
       ifp.state                as ifp_state,
       ifp.create_time          as ifp_create_time,
       ifp.update_time          as ifp_update_time,
       ifp.deleted              as ifp_deleted
from t_invite_follow_prize as ifp
where ifp.deleted = false;