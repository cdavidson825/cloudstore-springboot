#!/bin/bash

mvn clean package

if [[ $? != 0 ]] ; then
  exit 1
fi

#############################################################################################
###Super lame that I need to explode the springboot jar because it embeds its jars under lib/
export explode_dir=target/explode

mkdir ${explode_dir} 2> /dev/null
rm -rf ${explode_dir}/*
(cd ${explode_dir} && jar -xvf ../cloudstore-sb*.jar > /dev/null )

#############################################################################################

SPRING_OPTS='-Dspring.profiles.active=dev -Dspring.jpa.hibernate.ddl-auto=validate -Dspring.jpa.show-sql=true -Dspring.jpa.database=H2'

java -cp "target/explode/lib/*":target/explode/. ${SPRING_OPTS} cwd.cs.client.ClientDriver "$@"
