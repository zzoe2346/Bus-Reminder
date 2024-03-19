정# Base image
#FROM openjdk:17-jdk
#
## 소스 코드 복사
#COPY . /usr/src/myapp
#
## 작업 디렉토리 설정
#WORKDIR /usr/src/myapp
#
## 애플리케이션 빌드
#RUN ./gradlew build
#
## 포트 노출
#EXPOSE 8080
#
## 애플리케이션 실행
#CMD ["java", "-jar", "build/libs/myapp.jar"]

FROM openjdk:17-jdk
ARG JAR_FILE=./build/libs/daegu-bus-api-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

