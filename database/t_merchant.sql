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
    create_time        datetime     not null comment 'row create time',
    update_time        datetime     not null comment 'row last modify time',
    deleted            bit(1)       not null comment 'logical deleted flag'

) engine = InnoDB
  charset utf8
  collate utf8_general_ci;