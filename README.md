# Java Examples [![Build Status][actions-img]][actions]

A Maven project for learning Java during my free time. Most of the explanations are
written directly in the code as Javadoc. I use tests to understand technical detail
in different frameworks. You can run these tests using:

    mvn clean install

This project is tested under Java 11 and Java 15.

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
Jetty | Jetty Server.
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
Reliability | Tips to make production more reliable.
Rest | RESTful API using [Jersey][jersey].
Typesafe Config | [Typesafe Config](https://github.com/lightbend/config), configuration library for JVM languages.
XML | XML serialization, XPath, XSD.
VAVR | Functional component library that provides persistent data types and functional control structures.

## Articles

Here are some blog posts that I wrote using the source code of this repository. Visit <https://mincong.io>
to see the complete list.

### Akka

- [Write An Actor In Akka](https://mincong.io/2020/06/20/akka-actor/)
- [Testing Actor with TestActorRef](https://mincong.io/2020/01/08/akka-testing-actor-with-testactorref/)

### Java Annotation Processing

- [Introduction to Google Error Prone](https://mincong.io/2020/11/08/google-error-prone/)

### Java Core

- [Glob Expression Understanding](https://mincong.io/2019/04/16/glob-expression-understanding/)

### Java Concurrency

- [Why Do We Need Completable Future?](https://mincong.io/2020/06/26/completable-future/)
- [3 Ways to Handle Exception In Completable Future](https://mincong.io/2020/05/30/exception-handling-in-completable-future/)
- [How CompletableFuture is tested in OpenJDK?](https://mincong.io/2020/05/10/completablefuture-test/)

### Java Date

Date manipulation using `java.util.*` and `java.time.*`.

- [Using Java Time In Different Frameworks](https://mincong.io/2020/10/25/java-time/)
- [Controlling Time with Java Clock](https://mincong.io/2020/05/24/java-clock/)
- [Convert Date to ISO 8601 String in Java](https://mincong.io/2017/02/16/convert-date-to-string-in-java/)

### Logging

- [Logback: Test Logging Event](https://mincong.io/2020/02/02/logback-test-logging-event/)
- [SLF4J Understanding](https://mincong.io/2019/03/12/slf4j/)

### Mockito

Mockito, the most popular mocking framework for
Java unit tests. https://site.mockito.org

- [Mockito: 3 Ways to Init Mock in JUnit 5](https://mincong.io/2020/04/19/mockito-junit5)
- [Mockito: 3 Ways to Init Mock in JUnit 4](https://mincong.io/2019/09/13/init-mock)
- [Mockito: ArgumentCaptor](https://mincong.io/2019/12/15/mockito-argument-captor)
- [Mockito: 4 Ways to Verify Interations](https://mincong.io/2019/09/22/mockito-verify)
- [Testing with GwtMockito](https://mincong.io/2019/08/26/testing-with-gwtmockito)

### Reliability

- [Create a Throttler in Java](https://mincong.io/2020/11/07/throttler/)

## Code Style

I use [Google Java Code Style][style-java] for this repo.

[assertj]: http://joel-costigliola.github.io/assertj/
[bm]: http://byteman.jboss.org
[commons-cli]: https://commons.apache.org/proper/commons-cli/
[jersey]: https://jersey.github.io
[jgit]: https://github.com/eclipse/jgit
[style-java]: https://google.github.io/styleguide/javaguide.html
[actions]: https://github.com/mincong-h/java-examples/actions
[actions-img]: https://github.com/mincong-h/java-examples/workflows/Actions/badge.svg
