create table products (
    id character varying(64) not null primary key default uuid_generate_v4(),
    created_at timestamp without time zone not null default now(),
    status_record character varying(255) not null,
    updated_at timestamp without time zone not null,
    created_by character varying(255),
    updated_by character varying(255),
    description character varying(255) not null,
    name character varying(100) not null,
    price numeric(19,2) not null,
    quantity integer not null
);
