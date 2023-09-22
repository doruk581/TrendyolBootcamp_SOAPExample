# İlk aşama: Maven ile Proje Derlemesi
FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
# Bağımlılıkları indir (Opsiyonel)
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package

# İkinci aşama: Uygulamanın Çalıştırılacağı İmaj
FROM amazoncorretto:17.0.7-alpine
WORKDIR /student

# İlk aşamadan JAR dosyasını kopyala
COPY --from=build /app/target/student-0.0.1-SNAPSHOT.jar ./student-0.0.1-SNAPSHOT.jar

# Opsiyonel: Mevcut dizin ve içeriği görüntüle
RUN pwd
RUN ls

# Port açma
EXPOSE 8080

LABEL maintainer="Doruk Su doruk.su@trendyol.com"
LABEL version="1.0"
LABEL description="Bootcamp Student Application uygulaması için Docker imajı"

# Uygulamayı başlat
ENTRYPOINT ["java","-jar", "student-0.0.1-SNAPSHOT.jar"]


