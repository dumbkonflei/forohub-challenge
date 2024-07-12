create TABLE topico(
    id serial not null,
    titulo varchar(100) not null,
    mensaje varchar(500) not null,
    fecha_Creacion TIMESTAMP not null,
    estado varchar(100),
    usuario_id serial not null,
    curso_id serial not null,
    borrado boolean not null,

    primary key(id),

    constraint fk_topico_usuario_id foreign key(usuario_id) references usuario(id),
    constraint fk_topico_cursos_id foreign key(curso_id) references cursos(id)
)