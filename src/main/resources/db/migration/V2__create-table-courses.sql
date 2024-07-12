create TABLE cursos(
    id serial not null,
    nombre varchar(100) not null,
    categoria varchar(100) null,
    borrado boolean not null,

    primary key(id)
)