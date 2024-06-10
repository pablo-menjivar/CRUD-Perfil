create table tbTickets (
numero_ticket number(10) primary key,
uuid varchar2(50),
titulo_ticket varchar2(50) not null,
descripcion_ticket varchar2(200) not null,
autor_ticket varchar2(40) not null,
email_autor varchar2(100) not null,
creacion_ticket date not null,
estado_ticket varchar(20) check (estado_ticket in ('Activo', 'Finalizado')) not null,
finalizacion_ticket date not null
)