FROM amazoncorretto:17
VOLUME /tmp

# tzdata 패키지 설치
RUN yum install -y tzdata

# 서울 시간대 설정
RUN ln -sf /usr/share/zoneinfo/Asia/Seoul /etc/localtime && echo "Asia/Seoul" > /etc/timezone

COPY build/libs/issueMoa-batch-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/app.jar"]
