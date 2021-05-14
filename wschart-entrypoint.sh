#./mvnw spring-boot:run
#jvm_opt=-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=8000
echo "jvm_opt${jvm_opt}"
java -jar ${jvm_opt} ./app.jar
