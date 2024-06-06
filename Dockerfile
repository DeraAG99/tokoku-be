# Gunakan image base untuk JDK
FROM openjdk:17-jdk

# Menentukan argumen build
ARG JAR_FILE=target/tokoku-be-0.0.1-SNAPSHOT.jar

# Menyalin JAR file ke dalam image
COPY ${JAR_FILE} app.jar

# Menjalankan aplikasi
ENTRYPOINT ["java", "-jar", "/app.jar"]
