drop table if exists producto;

drop table if exists producto;

create table producto (
                          id bigint not null,
                          fecha_creacion_producto datetime(6),
                          nombre varchar(255),
                          precio double,
                          fecha_actualizacion_producto datetime(6),
                          version integer,
                          primary key (id)

) engine=InnoDB;