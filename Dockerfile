FROM openjdk:14-jdk-oraclelinux7

RUN yum install -y git openssh-clients less net-tools which curl procps unzip

COPY . /opt/bingo-java

# this can be done in multi stage docker builds
RUN cd /opt/bingo-java && ./mvnw package -Dmaven.test.skip=true 

RUN yum clean all

WORKDIR /opt/bingo-java