# build custom jre 11 runtime
FROM maven:3.6.3-jdk-11-slim as build

RUN jlink --no-header-files \
    --no-man-pages \
    --compress=2 \
    --strip-debug \
    --add-modules java.base,java.logging,java.xml,jdk.unsupported,java.sql,java.naming,java.management,java.instrument,java.desktop,java.security.jgss \
    --output /usr/lib/jvm/jlink-jre11-runtime

# package service jar to jre runtime
FROM debian:stretch-slim
COPY --from=build /usr/lib/jvm/jlink-jre11-runtime /usr/lib/jvm/jlink-jre11-runtime

WORKDIR /home/app
COPY target/stock-data-integration.jar service.jar

# https://stackoverflow.com/questions/56523042/sslhandshakeexception-when-trying-to-access-es-instance-from-docker/56577975#56577975
ENTRYPOINT ["/usr/lib/jvm/jlink-jre11-runtime/bin/java","-Djdk.tls.client.protocols=TLSv1.2","-jar","service.jar"]
