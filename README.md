# Cloudstore SpringBoot demo app

## Overview
Cloudstore is a SpringBoot demo (i.e. demo/non-production/non-secure/etc...) app that allows a client/user to store data (blobs) in Amazon S3 (and coming soon Microsoft Azure).  The data stored in the Cloud provider is encrypted with crypto info stored in a local database (currently H2).  In addition to crypto info, the local database also stores the internal & external identifiers, hash codes valiation, compression, etc. Cloudstore provides simple CRUD operations to data stored in the cloud provider via a client REPL and a skeleton REST interface (which is currently incomplete).

### Next steps (maybe).
1. Finish the REST interface and make it useful.
2. Storage in Microsoft Azure (http://azure.microsoft.com/en-us/documentation/articles/storage-java-how-to-use-blob-storage/)
3. Local database encryption using a master-key.
4. Fix scripts path issue -- currently must run scripts as ./script/<script_name> do to relative path of pom and target.

### Lame alerts (i.e. places where this app is non-production/non-secure)
1. The UserCredentials are very simplistic and are just enough to make this thing work for a local app.
2. The crypto algorithm is not industry strength.  A production app should do something better.
3. The local database is not encrypted.  Still working on this...
4. The REST API is not complete. Still working on this...

## Prerequisites
### Local H2 database (for local crypto key info).
* Download H2 from: http://www.h2database.com/html/main.html
* set H2_HOME to the root installed H2 directory ($H2_HOME is used by script below).
* H2 Configuration info provided below. 
* (optional) use `scripts/start_h2_db.sh` to start up the H2 database.

## Configuring 
In order to run Cloudstore, you must copy the cloud and database properties file in place.  These properties have been excluded from the Git repo, but example.properties have been checked in for quick configuration.
### Cloud configuration (under src/main/resources/cloud/)
1. Under src/main/resources/cloud/, copy cloud.example.properties to cloud.properties
2. In cloud.properties, update the Amazon info for aws.s3.accessKey, aws.s3.secretKey, and aws.s3.bucketName.  These properties values are loaded into the main src/main/resources/cloud/cloud.xml spring configuration file.

### Local H2 database configuration (under src/main/resources/database/)
1. Under src/main/resources/database/, copy database.example.properties to database.properties
2. In database.properties, update the local database connection info for "test" and "dev" environments.  The "test" env will be used for UnitTests (and will clean the DB after each run).  The "dev" env is for running the application locally (database content will be preserved).  
Note: you can swap out local databases very easily, but the current configuration is H2.

## Building
* To test: `mvn test`
* To build the single "fat" jar: `mvn clean package`

## Running

### Run standalone client REPL.
* `scripts/repl.sh `
<pre>
StoreIt repl > -help
CommandResponse [responseStatus=true, response=
Option                Description
-?, --help            show help
--delete              Delete data for provided ID
--get                 Get data for provided ID --local_path
                        required to indicate local output
                        fully-qualified file location
--get_cloud_keys      Get ALL keys stored in the cloud
--get_cloud_metadata  Get cloud metadata for provided ID
--get_local_keys      Get ALL keys stored locally
--get_local_metadata  Get local metadata for provided ID
--local_path
--save                Save data at provided ID --local_path
                        required to indicate local input
                        fully-qualified file location
, command=-help]
...
StoreIt repl >
</pre>
REPL usage examples:
<pre>
StoreIt repl > --get_local_keys 
StoreIt repl > --get_local_metadata LOCAL_ID
StoreIt repl > --get_cloud_keys 
StoreIt repl > --get_cloud_metadata CLOUD_ID
StoreIt repl > --get CLOUD_ID --local_path PATH_TO_FILE
StoreIt repl > --save CLOUD_ID --local_path PATH_TO_FILE
StoreIt repl > --delete CLOUD_ID
 </pre>
 
 ####For additional REPL usage/examples: [here](docs/repl_usage.md) 

### Startup the local Cloudstore app with skeleton REST endpoints and client REPL.
* `scripts/boot_dev.sh `
<pre>
...
[main] - [cwd.cs.StoreItApp]- Started StoreItApp in 25.299 seconds (JVM running for 25.552)
StoreIt repl >
</pre>


