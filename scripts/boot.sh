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

java -Dspring.profiles.active="${profile}" -jar ${ROOT_DIR}/target/cloudstore-sb*.jar
