# syntax=docker/dockerfile:1
FROM gradle:jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM bellsoft/liberica-openjdk-debian:17
RUN apt-get update && apt-get install -y apt-transport-https ca-certificates nginx mariadb-server sudo

RUN rm -rf /root
COPY nginx/default /etc/nginx/sites-enabled
COPY --from=build /home/gradle/src/build/libs/item-management-0.0.1-SNAPSHOT.jar /root/build/libs/
COPY ./init.sh /root/

RUN rm -rf /var/www/html/*
COPY --from=build /home/gradle/src/dist /var/www/html

ARG REMOTE_HOST=127.0.0.1
ENV REMOTE_HOST=$REMOTE_HOST
ARG REMOTE_PORT=3306
ENV REMOTE_PORT=$REMOTE_PORT
ARG REMOTE_PASSWORD=mIigY78xU8C4vk4spyd0FMnXdxyjOATw
ENV REMOTE_PASSWORD=$REMOTE_PASSWORD

EXPOSE 80
STOPSIGNAL SIGQUIT
CMD sh -c "sh /root/init.sh"
