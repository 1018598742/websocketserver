FROM java:8
VOLUME /tmp
COPY target/websocketserver-0.0.1-SNAPSHOT.jar websocketserver.jar
RUN bash -c "touch /websocketserver.jar"
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai  /etc/localtime
EXPOSE 6688
EXPOSE 12345
ENTRYPOINT ["java","-jar","-Duser.timezone=GMT+8","websocketserver.jar"]
