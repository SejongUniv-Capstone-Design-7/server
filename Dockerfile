# Dockerfile
# Docker 이미지가 어떤 단계를 거쳐 Build되는지 담고 있는 텍스트 파일

# 1. FROM
# base이미지 지정, 보통 Docker Hub와 같이 Docker repository에 올려 놓은 잘 알려진 공개 이미지인 경우가 많다.
# ex) ubuntu:lastest, openjdk:17-jdk-alpine

# 2. WORKDIR
# 쉘의 cd 명령어처럼, 컨테이너상에서 작업 디렉토리 전환을 위해서 사용
# RUN, CMD, ENTRYPOINT, COPY, ADD 명령문은 해당 디렉토리를 기준으로 실행

# ENTRYPOINT
# 이미지를 컨테이너로 띄울 때 항상 실행되어야하는 커멘트를 지정할 때 유용
# Docker 이미지를 마치 하나의 실행 파일처럼 사용할 때 유용
# /- WHY? :  컨테이너가 뜰 때, ENTRYPOINT 명령문으로 지정된 커멘드 실행되고, 커멘드로 실행된 프로세스가 죽을 때, 컨테이너도 따라서 종료

FROM openjdk:17-jdk-alpine
COPY build/libs/*jar capstone7.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/capstone7.jar"]
# 설정파일 분리해서 사용시
# java -jar -Dspring.profiles.active=prod capstone7.jar