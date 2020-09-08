drop table if exists t_merchant;

create table t_merchant
(
    id                 int(10)      not null primary key auto_increment comment 'row id',
    sn                 varchar(255) not null comment 'merchant serial number',
    name               varchar(255) not null comment 'merchant name',
    logo_path          varchar(255) null comment 'merchant logo file path',
    authorization_code varchar(255) not null comment 'merchant authorization code',
    login_name         varchar(255) not null comment 'merchant login name',
    password           varchar(255) not null comment 'merchant login password',
    wechat_app_id      varchar(255) null comment 'wechat appId',
    wechat_secret      varchar(255) null comment 'wechat secret',
    create_time        datetime     not null comment 'row create time',
    update_time        datetime     not null comment 'row last modify time',
    deleted            bit(1)       not null comment 'logical deleted flag'

) engine = InnoDB
  charset utf8
  collate utf8_general_ci;

select m.id                 as m_id,
       m.sn                 as m_sn,
       m.name               as m_name,
       m.logo_path          as m_logo_path,
       m.authorization_code as m_authorization_code,
       m.login_name         as m_login_name,
       m.password           as m_password,
       m.wechat_app_id      as m_wechat_app_id,
       m.wechat_secret      as m_wechat_secret,
       m.create_time        as m_create_time,
       m.update_time        as m_update_time,
       m.deleted            as m_deleted
from t_merchant as m
where m.deleted = false;