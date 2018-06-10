create table Empleado (DNI int primary key not null, Nombre varchar(255))

create table Cliente (DNI int primary key not null, Nombre varchar(255), AutoPatente varchar(255), Direccion varchar(255),
						Provincia varchar(255))

create table AutoParte (ID int primary key not null, Nombre varchar(255), Precio double(4,2))

create table OrdenTrabajo (ID int primary key not null, FechaInicio varchar(255), FechaFin varchar(255), Estado varchar(255),
						DNICliente int not null, DNIEmpleado int, CantidadHoras int, IDRepuestoUtilizado int,
						PatenteVehiculo varchar(255))

create table Vehiculo (Patente varchar(255) primary key not null, Marca varchar(255), Modelo varchar(255), DNICliente int)