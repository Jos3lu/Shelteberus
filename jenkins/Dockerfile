FROM mirekphd/jenkins-jdk17-on-ubuntu2204:latest
USER root
RUN curl -L "https://github.com/docker/compose/releases/download/v2.12.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
RUN mv /usr/local/bin/docker-compose /usr/bin/docker-compose
RUN chmod +x /usr/bin/docker-compose