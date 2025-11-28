// Crea un contenedor Docker con Java 17, copia tu aplicación .jar compilada dentro,
 expone el puerto 8082 y arranca automáticamente con java -jar. Así tu microservicio 
 funciona en cualquier máquina sin instalar nada, solo ejecutando docker build y docker
  run.

FROM eclipse-temurin:17-jdk-slim
ARG JAR_FILE=target/Producto-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} tienda_videojuegos.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/tienda_videojuegos.jar"]
