FROM java:8
VOLUME /tmp
COPY target/netty-websocket-redis.jar websocketserver.jar
RUN bash -c "touch /websocketserver.jar"
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai  /etc/localtime
EXPOSE 8982
ENTRYPOINT ["java","-jar","-Duser.timezone=GMT+8","websocketserver.jar"]
