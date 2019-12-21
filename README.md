# Java Examples [![Build Status][travis-img]][travis]

A Maven project for learning Java during my free time. Most of the explanations are
written directly in the code as Javadoc. I use tests to understand technical detail
in different frameworks. You can run these tests using:

    mvn clean install

This project is tested under Java 11.

## Module List

Module | Description
:--- | :---
Akka | Build highly concurrent, distributed, and resilient message-driven applications on the JVM <https://akka.io>
AssertJ | [AssertJ][assertj] testing framework.
Basic | Basic usage of Java core APIs.
Byteman | [byteman][bm] changes expected Java behavior and JDK runtime code.
CLI | [Apache Commons CLI][commons-cli].
Date | Date manipulation using `java.util.*` and `java.time.*`.
Elasticsearch | [Elasticsearch](https://github.com/elastic/elasticsearch): Open source, distributed, RESTful search engine
Encoding | Encoding challenge in Java.
IO | Java File I/O.
Java 8 | New functionality of Java 8, including filter, map, stream.
JGit | Basic usages of [JGit][jgit].
JMH | Java Microbenchmark Harness (JMH).
JSON | JSON conversion libraries in Java.
JUnit | JUnit testing framework.
Maven | Basic functionality of Maven.
Mongo | The MongoDB database
Mockito | [Mockito](https://site.mockito.org/), the most popular Mocking framework for Java unit tests
OCA | Oracle Certified Associate Java SE 8
OCP | Oracle Certified Professional Java SE 8
Regex | Regular expressions.
Rest | RESTful API using [Jersey][jersey].
Typesafe Config | [Typesafe Config](https://github.com/lightbend/config), configuration library for JVM languages.
XML | XML serialization, XPath, XSD.
VAVR | Functional component library that provides persistent data types and functional control structures.

## Code Style

I use [Google Java Code Style][style-java] for this repo.

## Generation

Generate a new module, e.g. `java-examples-io`, using [Maven Archetype
Plugin](https://maven.apache.org/archetype/maven-archetype-plugin/generate-mojo.html):

```sh
mvn archetype:generate \
    -DarchetypeArtifactId=maven-archetype-quickstart \
    -DarchetypeVersion=1.4 \
    -DinteractiveMode=false \
    -DgroupId=io.mincongh \
    -DartifactId=java-examples-io
```

Then, rename module by removing prefix "java-examples-" so that the naming of the new module is
consistent with the existing ones.

[assertj]: http://joel-costigliola.github.io/assertj/
[bm]: http://byteman.jboss.org
[commons-cli]: https://commons.apache.org/proper/commons-cli/
[jersey]: https://jersey.github.io
[jgit]: https://github.com/eclipse/jgit
[style-java]: https://google.github.io/styleguide/javaguide.html
[travis]: https://travis-ci.org/mincong-h/java-examples
[travis-img]: https://travis-ci.org/mincong-h/java-examples.svg?branch=master
