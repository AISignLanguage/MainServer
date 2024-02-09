# 베이스 이미지 선택
FROM amazoncorretto:20
# 애플리케이션 파일 복사
COPY build/libs/moment-of-gestures-0.0.1-SNAPSHOT.jar moment-of-gestures-0.0.1-SNAPSHOT.jar
# 애플리케이션 실행
ENTRYPOINT ["java","-jar","/moment-of-gestures-0.0.1-SNAPSHOT.jar"]
