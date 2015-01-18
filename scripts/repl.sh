#!/bin/bash

SCRIPT_DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
ROOT_DIR=${SCRIPT_DIR}/..

(cd ${ROOT_DIR} && mvn clean package) 
# if build failed, exit with error.
if [[ $? != 0 ]] ; then
  echo "maven return non-zero code, exiting...."
  exit 1
fi

source ${SCRIPT_DIR}/env.sh

#############################################################################################
###Super lame that I need to explode the springboot jar because it embeds its jars under lib/
export EXPLODE_DIR=${ROOT_DIR}/target/explode

mkdir ${EXPLODE_DIR} 2> /dev/null
rm -rf ${EXPLODE_DIR}/*
(cd ${EXPLODE_DIR} && jar -xvf ../cloudstore-sb*.jar > /dev/null )

#############################################################################################

SPRING_OPTS="-Dspring.profiles.active=${profile} -Dspring.jpa.hibernate.ddl-auto=validate -Dspring.jpa.show-sql=true -Dspring.jpa.database=H2"

java -cp "${EXPLODE_DIR}/lib/*":${EXPLODE_DIR}/. ${SPRING_OPTS} cwd.cs.client.ClientDriver "$@"


