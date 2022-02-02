
create table stb1
(
    id   serial primary key,
    name text not null unique
    --,mi_id integer REFERENCES menuitem(id) on delete cascade on update cascade
);

