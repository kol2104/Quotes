FROM tomcat:8-jdk8-openjdk
WORKDIR /usr/local/tomcat/webapps/
COPY ./target/view.war /usr/local/tomcat/webapps/
COPY tomcat-users.xml /usr/local/tomcat/conf/
EXPOSE 8080

CMD ["catalina.sh", "run"]