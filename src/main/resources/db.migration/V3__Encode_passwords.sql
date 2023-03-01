create extension if not exists pgcrypto;

update users set password_user = crypt(password_user, gen_salt('bf',8));
update users set confirm_password_user = crypt(confirm_password_user, gen_salt('bf',8));
