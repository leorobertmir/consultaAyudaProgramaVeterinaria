# Usar la imagen oficial de MySQL 5.7
FROM mysql:5.7

# Establecer las variables de entorno necesarias para la base de datos
ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=veterinariaLemas
ENV MYSQL_USER=lemas

ENV MYSQL_PASSWORD=Lemas2025.

# Exponer el puerto de MySQL
EXPOSE 3307

# Comando por defecto para iniciar MySQL
CMD ["mysqld"]


