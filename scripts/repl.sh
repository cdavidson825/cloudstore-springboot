#!/bin/bash

mvn clean package
# if build failed, exit with error.
if [[ $? != 0 ]] ; then
  echo "maven return non-zero code, exiting...."
  exit 1
fi

SCRIPT_DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
source ${SCRIPT_DIR}/env.sh

#############################################################################################
###Super lame that I need to explode the springboot jar because it embeds its jars under lib/
export explode_dir=target/explode

mkdir ${explode_dir} 2> /dev/null
rm -rf ${explode_dir}/*
(cd ${explode_dir} && jar -xvf ../cloudstore-sb*.jar > /dev/null )

#############################################################################################

SPRING_OPTS="-Dspring.profiles.active=${profile} -Dspring.jpa.hibernate.ddl-auto=validate -Dspring.jpa.show-sql=true -Dspring.jpa.database=H2"

java -cp "target/explode/lib/*":target/explode/. ${SPRING_OPTS} cwd.cs.client.ClientDriver "$@"


