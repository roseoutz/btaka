/* TODO - Postgres R2DBC 로 변경
create table btaka_user
(
    OID      varchar(64) not null
        constraint btaka_user_pk
            primary key,
    USER_ID  varchar(32) not null,
    PASSWORD varchar(64) not null,
    USERNAME varchar(32) not null
);

create unique index btaka_user_user_id_uindex
    on btaka_user.btaka_user (USER_ID);
 */
