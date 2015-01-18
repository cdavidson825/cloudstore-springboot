# Cloudstore SpringBoot demo app

## Overview
Cloudstore is a SpringBoot demo app that allows a client/user to store data (blobs) in Amazon S3, Google Cloud Storage, and coming soon Microsoft Azure.  The data stored in the Cloud provider (Amazon/Google) is encrypted with crypto info stored in a local database (currently H2).  In addition to crypto info, the local database also stores the internal & external identifiers, hash codes validation, compression, etc. Cloudstore provides simple CRUD operations to data stored in the cloud provider via a client REPL and a skeleton REST interface with a very basic html page.

## Known issues/future improvements ( https://github.com/cdavidson825/cloudstore-springboot/issues/ )
* The local database is not encrypted.  Issue #1
* The UserCredentials are very simplistic and are just enough to make this thing work for a local app. Issue #2
* The crypto algorithm (128-bit ASE) is not industry strength. Issue #3
* The REPL doesn't support up-arrow command history.  Issue #4
* Storage in Microsoft Azure not implemented yet. (http://azure.microsoft.com/en-us/documentation/articles/storage-java-how-to-use-blob-storage/) Issue #7

## Prerequisites
* Java 8
* Maven 3
* Amazon AWS account with IAM privs to S3 (default cloud provider) -- https://console.aws.amazon.com/console/home
* Google Cloud Storage (optional) -- https://console.developers.google.com/project
* Local databasefor local crypto key/info (currently configured for H2)
  * Download H2 from: http://www.h2database.com/html/main.html
  * set H2_HOME to the root installed H2 directory ($H2_HOME is used by script below).
  * H2 Configuration info provided below. 
  * (optional) use `scripts/start_h2_db.sh` to start up the H2 database.

## Configuring 
In order to run Cloudstore, you must copy the cloud and database properties file in place.  These properties have been excluded from the Git repo, but database.example.properties, cloud.properties, and optional google_oauth2.example.properties have been checked in for quick configuration.

### Cloud configuration (under src/main/resources/cloud/)
1. Under src/main/resources/cloud/, copy cloud.example.properties to cloud.properties and (optional) google_oauth2.example.json to google_oauth2.json
2. In cloud.properties, update the Amazon/Google key info for your environment.  These properties values are loaded into the main src/main/resources/cloud/cloud.xml spring configuration file.
3. (optional if using google storage) In google_oauth2.json, update the client_id and client_secret params for your Google Cloud Storage instance.  More documentation needed here...

### Local H2 database configuration (under src/main/resources/database/)
1. Under src/main/resources/database/, copy database.example.properties to database.properties
2. In database.properties, update the local database connection info for "test" and "dev" environments.  The "test" env will be used for UnitTests (and will clean the DB after each run).  The "dev" env is for running the application locally (database content will be preserved).  
Note: you can swap out local databases very easily, but the current configuration is H2.

## Building
* To test: `mvn test`
* To build the single "fat" jar: `mvn clean package`

## Running

By default the scripts will use Amazon/AWS ("aws") as the cloud storage provider; however, you can pass in the "provider" to override the default.  e.g. provider=google ./scripts/<script_name>

### Run standalone client REPL.
* `scripts/repl.sh `  or `provider=google scripts/repl.sh`  <p/>
REPL usage examples:
<pre>
CloudStore> --help 
CloudStore> --get_local_keys 
CloudStore> --get_local_metadata LOCAL_ID
CloudStore> --get_cloud_keys 
CloudStore> --get_cloud_metadata CLOUD_ID
CloudStore> --get_data LOCAL_ID --local_file LOCAL_FILE
CloudStore> --save_data LOCAL_ID --local_file LOCAL_FILE
CloudStore> --delete LOCAL_ID
 </pre>
 
####For additional REPL usage/examples: [here](docs/repl_usage.md) 

### Startup the local Cloudstore app with skeleton REST endpoints, simple/crappy HTML page, and client REPL.
* `scripts/boot.sh ` or `provider=google scripts/boot.sh`
<pre>
...
#########################################################
The Active Spring Profile will be: dev,aws
#########################################################
...
[main] - [cwd.cs.CloudStoreApp]- Started StoreItApp in 25.299 seconds (JVM running for 25.552)
CloudStore>
</pre>

####Basic HTML page with Forms for each action
[http://localhost:8080/](http://localhost:8080/)

####Skeleton REST endpoints
* To get all metadata (HTTP GET): http://localhost:8080/cloudstore/
* To get metadata for given `local_id` (HTTP GET) : http://localhost:8080/cloudstore/get_meta/local_id
* To delete record for given `local_id` (HTTP POST) : http://localhost:8080/cloudstore/delete/local_id
* Save/Get objects do have endpoints, see the html page above.

