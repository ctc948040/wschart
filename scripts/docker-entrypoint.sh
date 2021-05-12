#./mvnw spring-boot:run
jvm_opt=-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8000
java -jar ${jvm_opt} ./app.jar
