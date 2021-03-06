
## REPL Usage:  `scripts/repl.sh`

* `CloudStore> --help`
<pre>
CommandResponse [responseStatus=true
command = --help
response =
Option                Description
-?, --help            show help
--delete              Delete data for provided ID
--get_cloud_keys      Get ALL keys stored in the cloud
--get_cloud_metadata  Get cloud metadata for provided ID
--get_data            Get data for provided ID --local_file
                        required to indicate local output
                        fully-qualified file location
--get_local_keys      Get ALL keys stored locally
--get_local_metadata  Get local metadata for provided ID
--local_file
--save_data           Save data at provided ID --local_file
                        required to indicate local input
                        fully-qualified file location
]
CloudStore>
</pre>

* `CloudStore> --save_data foobar -local_file README.md`
<pre>
CommandResponse [responseStatus=true
command = --save_data readme -local_file README.md
response =
Data saved with metadata: dbId: 9 -- InternalID: readme, ExternalContainer: cloudstore-sb, ExternalID: cf4a5ba0-6c65-4154-9546-c946d8068561]
CloudStore>
</pre>

* `CloudStore> --get_local_keys`
<pre>
CommandResponse [responseStatus=true
command = --get_local_keys
response =
dbId: 5 -- InternalID: readme, ExternalContainer: cloudstore-sb, ExternalID: cd28287b-1140-4983-a7df-0ce03df98192
]
CloudStore>
</pre>

* `CloudStore> --get_cloud_keys`
<pre>
******* Cloud Key List *******
cd28287b-1140-4983-a7df-0ce03df98192
]
CloudStore>
</pre>

* `CloudStore> --get_local_metadata readme`
<pre>
Getting Local Metadata for key: readme
StorageMetadata:
createDate=2015-01-09 13:06:35.504
smId=5
externalContainer=cloudstore-sb
externalId=cd28287b-1140-4983-a7df-0ce03df98192
internalHashCode=fd17e2b246311ea10933ce829758f8a1b81683bf
externalHashCode=fd56c108589cbfcad8b7d75d952dcdbf207aed3f
internalId=readme
isCompressed=true
isEncrypted=true
lastModifiedDate=null
owner=anonUser
]
CloudStore>
</pre>

* `CloudStore> --get_cloud_metadata cd28287b-1140-4983-a7df-0ce03df98192`
<pre>
CommandResponse [responseStatus=true
command = --get_cloud_metadata cd28287b-1140-4983-a7df-0ce03df98192
response =
Getting Cloud Metadata for key: cd28287b-1140-4983-a7df-0ce03df98192 
key: Accept-Ranges  value: bytes
key: ETag   value: 5a8ff383b6a1d9ad28c470132ab48721
key: Last-Modified  value: Fri Jan 09 13:06:36 EST 2015
key: Content-Length value: 1936
key: Content-Type   value: application/octet-stream
]
CloudStore>
</pre>

* `CloudStore> --get_data readme -local_file foobar`
<pre>
2015-01-10 16:05:39,627 INFO  [main] - [cwd.cs.server.manager.retrieval.RetrievalManagerImpl]- Verifying external hash.
2015-01-10 16:05:39,628 INFO  [main] - [cwd.cs.server.manager.retrieval.RetrievalManagerImpl]- Decrypting data.
2015-01-10 16:05:39,629 INFO  [main] - [cwd.cs.server.manager.retrieval.RetrievalManagerImpl]- Decompressing data.
2015-01-10 16:05:39,631 INFO  [main] - [cwd.cs.server.manager.retrieval.RetrievalManagerImpl]- Verifying internal hash.
CommandResponse [responseStatus=true
command = --get_data readme --local_file foobar
response =
Data retrived and stored at foobar]
CloudStore>
</pre>







