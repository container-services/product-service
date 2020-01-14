FROM openjdk:12
WORKDIR products-service/src
ADD ./target/products-service.jar products-service.jar
EXPOSE 80
ENTRYPOINT ["java", "-jar", "products-service.jar"] 
