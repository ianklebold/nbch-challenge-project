ALTER TABLE producto ADD descripcion varchar(5000);


ALTER TABLE producto MODIFY COLUMN nombre VARCHAR(100) NOT NULL;
ALTER TABLE producto MODIFY COLUMN precio double NOT NULL;
ALTER TABLE producto MODIFY COLUMN fecha_creacion_producto datetime(6) NOT NULL;