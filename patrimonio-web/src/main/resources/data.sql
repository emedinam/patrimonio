insert into usuarios (email,password_cifrado,rol) values ('admin@catastros.com','$2a$10$P9iRj73x3vWYViDDis/2E.WftvOlvakhI0wFs/WHZu59tiHsE8i.m','ADMIN');
drop table if exists persistent_logins;
create table persistent_logins (username varchar(64) not null,
 series varchar(64) primary key,
 token varchar(64) not null,
 last_used timestamp not null)