DROP TABLE IF EXISTS master.dbo.Empleado
DROP TABLE IF EXISTS master.dbo.Cliente
DROP TABLE IF EXISTS master.dbo.Repuesto
DROP TABLE IF EXISTS master.dbo.OrdenTrabajo
DROP TABLE IF EXISTS master.dbo.OrdenTrabajoRepuesto
create table Empleado (ID int identity(1,1) primary key not null, Nombre varchar(255), DNI int not null)
create table Cliente (ID int primary key not null, DNI int not null, Nombre varchar(255), Direccion varchar(255), Provincia varchar(255))
create table Repuesto (ID int identity(1,1) primary key not null, Nombre varchar(255), Precio decimal(8,2))
create table OrdenTrabajo (ID int primary key not null, FechaInicio varchar(255) not null, FechaFin varchar(255), Estado varchar(255) not null, DNICliente int not null, DNIEmpleado int, Marca varchar(255) not null, Modelo varchar(255), PatenteVehiculo varchar(255) not null, Descripcion varchar (255))
create table OrdenTrabajoRepuesto (ID int identity(1,1) primary key not null, OrdenID int not null, RepuestoID int not null, CantidadHoras int, CantidadRepuestos int)
insert into master.dbo.Empleado (Nombre, DNI) values ('Valeria', 27364536)
insert into master.dbo.Empleado (Nombre, DNI) values ('Pablo', 30293847)
insert into master.dbo.Empleado (Nombre, DNI) values ('Andres', 32789543)
insert into master.dbo.AutoParte (Nombre, Precio) values ('Neumatico', 700)
insert into master.dbo.AutoParte (Nombre, Precio) values ('Bateria', 6789.29)
insert into master.dbo.AutoParte (Nombre, Precio) values ('Faro antiniebla', 1700)
insert into master.dbo.AutoParte (Nombre, Precio) values ('Carcaza filtro de aire', 1845.07)