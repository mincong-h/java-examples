# MongoDB

## MongoDB Shell

Install MongoDB Shell on macOS:

```
$ brew tap mongodb/brew
$ brew install mongodb-community-shell
```

Connect to MongoDB Shell:

```
$ mongo
```

## Quickstart

Start the MongoDB docker image:

```
mvn docker:start
```

Don't forget the stop it once finished:

```
mvn docker:stop
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

## Collections

Show collections:

```
> show collections
users
companies
```

Create a new collection
([`db.createCollection`](https://docs.mongodb.com/manual/reference/method/db.createCollection/)):

```
> db.createCollection("users")
{ "ok" : 1 }
```

## Documents

Insert document(s) to collection:

```
> user1 = { "first_name": "Foo", "last_name": "Bar" }
{ "first_name" : "Foo", "last_name" : "Bar" }
> db.users.insert(user1)
WriteResult({ "nInserted" : 1 })
```

```
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

```
> db.users.find({ first_name: { $eq: "Foo" } })
{ "_id" : ObjectId("5e4908c14638a83462bc6a15"), "first_name" : "Foo", "last_name" : "Bar" }
{ "_id" : ObjectId("5e490a2a4638a83462bc6a16"), "first_name" : "Foo", "last_name" : "Bar2" }
```

```
> db.users.find({ first_name: "Foo" })
{ "_id" : ObjectId("5e4908c14638a83462bc6a15"), "first_name" : "Foo", "last_name" : "Bar" }
{ "_id" : ObjectId("5e490a2a4638a83462bc6a16"), "first_name" : "Foo", "last_name" : "Bar2" }
```

```
> db.users.find()
{ "_id" : ObjectId("5e4908c14638a83462bc6a15"), "first_name" : "Foo", "last_name" : "Bar" }
{ "_id" : ObjectId("5e490a2a4638a83462bc6a16"), "first_name" : "Foo", "last_name" : "Bar2" }
```

Update document(s) via
[`db.collection.findAndModify`](https://docs.mongodb.com/manual/reference/method/db.collection.findAndModify/index.html).
Although the query may match multiple documents, only one document will be
modified :warning:.

```
> db.users.findAndModify({ query: {}, update: { $set: { tel: "123" }}})
{
	"_id" : ObjectId("5e49118b095f13556162b497"),
	"first_name" : "First1",
	"last_name" : "Last1"
}
```

Update documents by adding a new field "tel" for all users:

```
> db.users.updateMany({}, { $set: { tel: "456" }})
{ "acknowledged" : true, "matchedCount" : 2, "modifiedCount" : 2 }
```

Update documents by adding a nested field "address.label" for all users:

```
> db.users.updateMany({}, { $set: { address: { label: "hi" }}})
{ "acknowledged" : true, "matchedCount" : 2, "modifiedCount" : 2 }

> db.users.find()
{ "_id" : ObjectId("5e4913e0095f13556162b499"), "first_name" : "Foo", "last_name" : "Bar1", "address" : { "label" : "hi" } }
{ "_id" : ObjectId("5e4913e0095f13556162b49a"), "first_name" : "Foo", "last_name" : "Bar2", "address" : { "label" : "hi" } }
```

Remove all documents from collection (:warning: be careful!):

```
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
