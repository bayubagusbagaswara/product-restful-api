create table roles (
    id bigserial not null primary key,
    created_at timestamp without time zone not null default now(),
    status_record character varying(255) not null,
    updated_at timestamp without time zone not null,
    name character varying(20) not null
);

alter table roles
    add constraint roles_name_unique unique (name);
