# MongoDB

## Installation

### Docker

Start the MongoDB Docker image via Docker:

```sh
> docker run --rm --name mongo-demos --detach -p 27017:27017 mongo:4.2
```

Don't forget to stop it when you finish:

```sh
> docker stop mongo-demos
```

### Maven

Start the MongoDB docker image via Maven:

```sh
> mvn docker:start
```

Don't forget the stop it once finished:

```sh
> mvn docker:stop
```

## Mongo Shell

Install MongoDB Shell on macOS:

```sh
> brew tap mongodb/brew
> brew install mongodb-community-shell
```

Learn how to use it via the option `-h,--help`:

```
> mongo -h
MongoDB shell version v4.2.0
usage: mongo [options] [db address] [file names (ending in .js)]
db address can be:
  foo                   foo database on local machine
  192.168.0.5/foo       foo database on 192.168.0.5 machine
  192.168.0.5:9999/foo  foo database on 192.168.0.5 machine on port 9999
  mongodb://192.168.0.5:9999/foo  connection string URI can also be used
Options:
...
```

### Interactive Mode

Connect to MongoDB via Mongo Shell:

```sh
> mongo
```

Once connected to MongDB via MongDB Shell, you can perform the following commands.

Display the current database:

```
> db
test
``` 

Switch to database `test`:

```
> use test
switched to db test
```

Check the MongoDB version:

```
> db.version()
4.0.0
```

### Background Mode

Evaluate a string expression and return the result in JSON format. You can also use `jq` to transform it to something else.

```sh
mongo --quiet --eval "db.fruits.find({ ... })" | jq
```

Run a script and return the result in JSON format.

```sh
mongo --quiet find_fruits.js
```

## Collections

Show collections:

```
> show collections
users
companies
```

Create a new collection
([`db.createCollection`](https://docs.mongodb.com/manual/reference/method/db.createCollection/)):

```js
> db.createCollection("users")
{ "ok" : 1 }
```

## Documents

Insert document(s) to collection:

```js
> user1 = { "first_name": "Foo", "last_name": "Bar" }
{ "first_name" : "Foo", "last_name" : "Bar" }
> db.users.insert(user1)
WriteResult({ "nInserted" : 1 })
```

```js
> db.users.insert([
...   { "first_name": "First1", "last_name": "Last1" },
...   { "first_name": "First2", "last_name": "Last2" },
... ])
BulkWriteResult({
	"writeErrors" : [ ],
	"writeConcernErrors" : [ ],
	"nInserted" : 2,
	"nUpserted" : 0,
	"nMatched" : 0,
	"nModified" : 0,
	"nRemoved" : 0,
	"upserted" : [ ]
})
```

Find documents from collection:

```js
> db.users.find({ first_name: { $eq: "Foo" } })
{ "_id" : ObjectId("5e4908c14638a83462bc6a15"), "first_name" : "Foo", "last_name" : "Bar" }
{ "_id" : ObjectId("5e490a2a4638a83462bc6a16"), "first_name" : "Foo", "last_name" : "Bar2" }
```

```js
> db.users.find({ first_name: "Foo" })
{ "_id" : ObjectId("5e4908c14638a83462bc6a15"), "first_name" : "Foo", "last_name" : "Bar" }
{ "_id" : ObjectId("5e490a2a4638a83462bc6a16"), "first_name" : "Foo", "last_name" : "Bar2" }
```

```js
> db.users.find()
{ "_id" : ObjectId("5e4908c14638a83462bc6a15"), "first_name" : "Foo", "last_name" : "Bar" }
{ "_id" : ObjectId("5e490a2a4638a83462bc6a16"), "first_name" : "Foo", "last_name" : "Bar2" }
```

Update document(s) via
[`db.collection.findAndModify`](https://docs.mongodb.com/manual/reference/method/db.collection.findAndModify/index.html).
Although the query may match multiple documents, only one document will be
modified :warning:.

```js
> db.users.findAndModify({ query: {}, update: { $set: { tel: "123" }}})
{
	"_id" : ObjectId("5e49118b095f13556162b497"),
	"first_name" : "First1",
	"last_name" : "Last1"
}
```

Update documents by adding a new field "tel" for all users:

```js
> db.users.updateMany({}, { $set: { tel: "456" }})
{ "acknowledged" : true, "matchedCount" : 2, "modifiedCount" : 2 }
```

Update documents by adding a nested field "address.label" for all users:

```js
> db.users.updateMany({}, { $set: { address: { label: "hi" }}})
{ "acknowledged" : true, "matchedCount" : 2, "modifiedCount" : 2 }

> db.users.find()
{ "_id" : ObjectId("5e4913e0095f13556162b499"), "first_name" : "Foo", "last_name" : "Bar1", "address" : { "label" : "hi" } }
{ "_id" : ObjectId("5e4913e0095f13556162b49a"), "first_name" : "Foo", "last_name" : "Bar2", "address" : { "label" : "hi" } }
```

Remove all documents from collection (:warning: be careful!):

```js
> db.users.remove({})
WriteResult({ "nRemoved" : 4 })
```

## Ports

The following table lists the default TCP ports used by MongoDB:

Default Port | Description
-----------: | :---------- 
27017        | The default port for mongod and mongos instances. You can change this port with port or `--port`.
27018        | The default port for mongod when running with `--shardsvr` command-line option or the shardsvr value for the clusterRole setting in a configuration file.
27019        | The default port for mongod when running with `--configsvr` command-line option or the configsvr value for the clusterRole setting in a configuration file.

## References

- Mongo, "Getting Started (MongoDB Shell)", _MongoDB Manual_, 2019.
  <https://docs.mongodb.com/manual/tutorial/getting-started/>
- Mongo, "Database Commands", _MongoDB Manual_, 2019.
  <https://docs.mongodb.com/manual/reference/command/>
- Mongo, "Default MongoDB Port", _MongoDB Manual_, 2019.
  <https://docs.mongodb.com/manual/reference/default-mongodb-port/>
- Shubham Aggarwal, "A Guide to MongoDB", _Baeldung_, 2019.
  <https://www.baeldung.com/java-mongodb>
- Caconde, "Install mongo shell in mac", _Stack Overflow_, 2019.
  <https://stackoverflow.com/questions/58504865/>
- Antoine Roger, "How to deal with MongoDB shell output in your shell scripts – Part 2/2: parsing JSON with jq", 2018.
  <https://www.mydbaworld.com/mongodb-shell-output-parsing-json-jq/>
