create table veterinariaLemas.info_veterinario (
    id_veterinario int not null auto_increment,
    nombre varchar(127),
    animal varchar(63),
    raza varchar(15),
    edad int,
    fe_registro date,
    usr_creacion varchar(63),
    estado varchar(15),
    fecha_modificacion date,
    usr_modificacion varchar(63),
    primary key (id_veterinario)
);
