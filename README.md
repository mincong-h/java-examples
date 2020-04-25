# Java Examples [![Build Status][actions-img]][actions]

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
CLI | [Apache Commons CLI][commons-cli].
Date | Date manipulation using `java.util.*` and `java.time.*`.
Elasticsearch | [Elasticsearch](https://github.com/elastic/elasticsearch): Open source, distributed, RESTful search engine
Encoding | Encoding challenge in Java.
IO | Java File I/O.
Immutables | Generate simple, safe and consistent value objects. <https://immutables.github.io/>
Jackson | Jackson, a high-performance JSON processor for Java.
Java 8 | New functionality of Java 8, including filter, map, stream.
JGit | Basic usages of [JGit][jgit].
JMH | Java Microbenchmark Harness (JMH).
JSON | JSON conversion libraries in Java.
JUnit | JUnit testing framework.
Logback | [Logback](http://logback.qos.ch/) logging framework.
Maven | Basic functionality of Maven.
Mongo | The MongoDB database
Mockito | [Mockito](https://site.mockito.org/), the most popular mocking framework for Java unit tests
OCA | Oracle Certified Associate Java SE 8
OCP | Oracle Certified Professional Java SE 8
Regex | Regular expressions.
Rest | RESTful API using [Jersey][jersey].
Typesafe Config | [Typesafe Config](https://github.com/lightbend/config), configuration library for JVM languages.
XML | XML serialization, XPath, XSD.
VAVR | Functional component library that provides persistent data types and functional control structures.

## Code Style

I use [Google Java Code Style][style-java] for this repo.

## Mockito

[Mockito](https://site.mockito.org/), the most popular mocking framework for
Java unit tests.

- [Mockito: 3 Ways to Init Mock in JUnit 5](https://mincong.io/2020/04/19/mockito-junit5)
- [Mockito: 3 Ways to Init Mock in JUnit 4](https://mincong.io/2019/09/13/init-mock)
- [Mockito: ArgumentCaptor](https://mincong.io/2019/12/15/mockito-argument-captor)
- [Mockito: 4 Ways to Verify Interations](https://mincong.io/2019/09/22/mockito-verify)
- [Testing with GwtMockito](https://mincong.io/2019/08/26/testing-with-gwtmockito)

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
[actions]: https://github.com/mincong-h/java-examples/actions
[actions-img]: https://github.com/mincong-h/java-examples/workflows/Actions/badge.svg
