FROM azul/zulu-openjdk:17

# Establecer el directorio de trabajo
WORKDIR /app

# Configurar la zona horaria
ENV TZ=America/New_York
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# Copiar el archivo JAR de la aplicación
COPY ./build/libs/utils-0.0.1-SNAPSHOT.jar /app/utils-0.0.1-SNAPSHOT.jar

# Configurar el usuario no root
RUN useradd -m myuser
USER myuser

# Configurar el puerto
ENV PORT 8282

# Configurar el healthcheck
HEALTHCHECK --interval=5m --timeout=3s \
  CMD curl -f http://localhost:${PORT}/ || exit 1

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/utils-0.0.1-SNAPSHOT.jar"]
