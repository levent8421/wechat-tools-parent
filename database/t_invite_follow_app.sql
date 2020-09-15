drop table if exists t_invite_follow_app;


create table t_invite_follow_app
(
    id             int(10)      not null auto_increment primary key comment 'Row id',
    merchant_id    int(10)      not null comment 'Merchant id',
    theme_color    varchar(100) not null comment 'Page Theme color',
    banner_image   varchar(255) null comment 'Banner image file name',
    title          varchar(255) not null comment 'page title',
    subtitle       varchar(255) null comment 'Subtitle',
    footer_text    varchar(255) null comment 'Page footer text',
    button_image   varchar(255) null comment 'Draw button image',
    rules_text     text         null comment 'Rules text',
    deadline       datetime     null comment 'Deadline (time)',
    phone_required bit(1)       not null comment 'Phone number required',
    images_json    text         null comment 'images list store as json',
    state          int(3)       not null comment 'State code',
    default_app    bit(1)       not null comment 'Is default app',
    create_time    datetime     not null comment 'Row create time',
    update_time    datetime     not null comment 'Row last update time',
    deleted        bit(1)       not null comment 'Delete flag'
) engine = 'Innodb'
  charset utf8
  collate utf8_general_ci;

select ifa.id             as ifa_id,
       ifa.merchant_id    as ifa_merchant_id,
       ifa.theme_color    as ifa_theme_color,
       ifa.banner_image   as ifa_banner_image,
       ifa.title          as ifa_title,
       ifa.subtitle       as ifa_subtitle,
       ifa.footer_text    as ifa_footer_text,
       ifa.button_image   as ifa_button_image,
       ifa.rules_text     as ifa_rules_text,
       ifa.deadline       as ifa_deadline,
       ifa.phone_required as ifa_phone_required,
       ifa.images_json    as ifa_images_json,
       ifa.state          as ifa_state,
       ifa.default_app    as ifa_default_app,
       ifa.create_time    as ifa_create_time,
       ifa.update_time    as ifa_update_time,
       ifa.deleted        as ifa_deleted
from t_invite_follow_app as ifa
where ifa.deleted = false;

