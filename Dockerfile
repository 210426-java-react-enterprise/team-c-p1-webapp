FROM tomcat:9.0-jdk8-adoptopenjdk-openj9

EXPOSE 8080

ENV host-url jdbc:postgresql://java-react-training-course-db.crpipnhq23xz.us-west-1.rds.amazonaws.com:5432/postgres?currentschema\=project1
ENV db_username sean_taba
ENV db_password Sean-Java-React-Course

COPY target/p1-1.war webapps/p1-1.war

ENTRYPOINT bin/startup.sh