#!/bin/bash

mvn clean package 

# if build failed, exit with error.
if [[ $? != 0 ]] ; then
  echo "maven return non-zero code, exiting...."
  exit 1
fi

SCRIPT_DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
source ${SCRIPT_DIR}/env.sh

java -Dspring.profiles.active="${profile}" -jar target/cloudstore-sb*.jar
