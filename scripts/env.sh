#!/bin/bash

#Environment setup for various scripts.

environment=${environment:="dev"}
provider=${provider:="aws"}
export profile=${profile:="${environment},${provider}"}

echo "#########################################################"
echo "The Active Spring Profile will be: ${profile}"
echo "#########################################################"
