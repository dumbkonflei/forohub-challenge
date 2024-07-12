create TABLE respuesta(
    id serial not null,
    mensaje varchar(500) not null,
    solved boolean not null,
    topico_id int not null,
    usuario_id int not null,
    borrado boolean not null,

    primary key(id),

    constraint fk_respuesta_topico_id foreign key(topico_id) references topico(id),
    constraint fk_respuesta_usuario_id foreign key(usuario_id) references usuario(id)
)