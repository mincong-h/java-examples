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
