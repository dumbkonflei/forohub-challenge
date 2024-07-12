ALTER TABLE respuesta ADD fecha_Creacion timestamp;
update respuesta set fecha_Creacion = NOW();