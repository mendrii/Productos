FROM eclipse-temurin:17-jdk-slim
ARG JAR_FILE=target/Producto-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} tienda_videojuegos.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/tienda_videojuegos.jar"]
