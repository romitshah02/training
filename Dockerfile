FROM bellsoft/liberica-runtime-container:jre-21-slim-musl

WORKDIR /app    

COPY target/*.jar app.jar 

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]