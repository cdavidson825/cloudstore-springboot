
#!/bin/bash

mvn clean package && java -Dspring.profiles.active="dev" -jar target/cloudstore-sb*.jar
