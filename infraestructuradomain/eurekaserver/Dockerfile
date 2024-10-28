# Usar la imagen base de JRE 17 en Alpine
FROM eclipse-temurin:17-jre-alpine

# Crear grupo y usuario
RUN addgroup -g 1028 devopsc && adduser -D -G devopsc admin

# Establecer el directorio de trabajo
WORKDIR /app


# Copiar el archivo JAR en el contenedor (esto se pasará como un build argument)
ARG JAR_FILE
COPY ${JAR_FILE} /tmp/app.jar

RUN chown -R admin:devopsc /tmp

# Cambiar a usuario no root
USER admin

# Ejecutar la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /tmp/app.jar"]
