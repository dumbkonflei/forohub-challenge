create TABLE usuario (
    id serial not null,
    nombre varchar(100) not null,
    email varchar(100) not null,
    password varchar(300) not null,
    activo boolean not null,

    primary key(id)
)