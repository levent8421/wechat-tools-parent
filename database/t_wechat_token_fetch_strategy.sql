drop table if exists t_wechat_token_fetch_strategy;

create table t_wechat_token_fetch_strategy
(
    id            int(10)  not null auto_increment primary key comment 'Row ID',
    merchant_id   int(10)  not null comment 'Merchant ID',
    strategy_code int(5)   not null comment 'Strategy code',
    config_json   varchar(1024) comment 'Config json',
    create_time   datetime not null comment 'Row create time',
    update_time   datetime not null comment 'Row last update time',
    deleted       bit(1)   not null comment 'Delete flag'
) engine = 'Innodb'
  charset utf8
  collate utf8_general_ci;;


select wtfs.id            as wtfs_id,
       wtfs.merchant_id   as wtfs_merchant_id,
       wtfs.strategy_code as wtfs_strategy_code,
       wtfs.config_json   as wtfs_config_json,
       wtfs.create_time   as wtfs_create_time,
       wtfs.update_time   as wtfs_update_time,
       wtfs.deleted       as wtfs_deleted
from t_wechat_token_fetch_strategy as wtfs
where wtfs.deleted = false;