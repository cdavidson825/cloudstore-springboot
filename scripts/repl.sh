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
export TARGET_DIR=${ROOT_DIR}/target
export LIB_DIR=${TARGET_DIR}/lib
export CLOUDSTORE_JAR=$(ls -1 ${TARGET_DIR}/*.original)

echo "JAR = ${CLOUDSTORE_JAR}"

SPRING_OPTS="-Dspring.profiles.active=${profile} -Dspring.jpa.hibernate.ddl-auto=validate -Dspring.jpa.show-sql=true -Dspring.jpa.database=H2"

java -cp "${CLOUDSTORE_JAR}:${LIB_DIR}/*" ${SPRING_OPTS} cwd.cs.client.ClientDriver "$@"


