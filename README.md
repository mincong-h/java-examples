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
gRPC | A high performance, open source universal RPC framework. <https://www.grpc.io/>
IO | Java File I/O.
Immutables | Generate simple, safe and consistent value objects. <https://immutables.github.io/>
Jackson | Jackson, a high-performance JSON processor for Java.
Java 8 | New functionality of Java 8, including filter, map, stream.
Jetty | Jetty Server.
JGit | Basic usages of [JGit][jgit].
JMH | Java Microbenchmark Harness (JMH).
JSON | JSON conversion libraries in Java.
JUnit 4 | JUnit 4 testing framework.
JUnit 5 | JUnit 5 testing framework.
Logback | [Logback](http://logback.qos.ch/) logging framework.
Maven | Basic functionality of Maven.
Mongo | The MongoDB database
Mockito | [Mockito](https://site.mockito.org/), the most popular mocking framework for Java unit tests
OCA | Oracle Certified Associate Java SE 8
OCP | Oracle Certified Professional Java SE 8
Reliability | Tips to make production more reliable.
Rest | RESTful API using [Jersey][jersey].
Spring Boot | Spring boot.
Spring Data MongoDB | Spring data integration for MongoDB.
Typesafe Config | [Typesafe Config](https://github.com/lightbend/config), configuration library for JVM languages.
XML | XML serialization, XPath, XSD.
VAVR | Functional component library that provides persistent data types and functional control structures.

## Articles

Here are some blog posts that I wrote using the source code of this repository and related satellite repositories. Visit <https://mincong.io>
to see the complete list.

### Akka

- [Write An Actor In Akka](https://mincong.io/2020/06/20/akka-actor/)
- [Testing Actor with TestActorRef](https://mincong.io/2020/01/08/akka-testing-actor-with-testactorref/)

### Java Annotation Processing

- [Why You Should Use Auto Value in Java?](https://mincong.io/2018/08/21/why-you-should-use-auto-value-in-java/)
- [Introduction of Immutables](https://mincong.io/2020/04/13/introduction-of-immutables/)
- [Introduction to Google Error Prone](https://mincong.io/2020/11/08/google-error-prone/)

### Java Core

- [Glob Expression Understanding](https://mincong.io/2019/04/16/glob-expression-understanding/)
- [Unzipping File in Java](https://mincong.io/2019/10/27/unzipping-file-in-java/)
- [Fixng Comparator](https://mincong.io/2019/07/18/fixing-comparator/)
- [Understanding ISO-8859-1 / UTF-8](https://mincong.io/2019/04/07/understanding-iso-8859-1-and-utf-8/)

### Java Concurrency

- [Why Do We Need Completable Future?](https://mincong.io/2020/06/26/completable-future/)
- [3 Ways to Handle Exception In Completable Future](https://mincong.io/2020/05/30/exception-handling-in-completable-future/)
- [How CompletableFuture is tested in OpenJDK?](https://mincong.io/2020/05/10/completablefuture-test/)
- [Using 100% CPU with ExecutorService.invokeAll()](https://mincong.io/2019/01/29/executorservice-invokeall/)

### Java Date

Date manipulation using `java.util.*` and `java.time.*`.

- [Using Java Time In Different Frameworks](https://mincong.io/2020/10/25/java-time/)
- [Controlling Time with Java Clock](https://mincong.io/2020/05/24/java-clock/)
- [Convert Date to ISO 8601 String in Java](https://mincong.io/2017/02/16/convert-date-to-string-in-java/)

### Java Logging

- [Logback: Test Logging Event](https://mincong.io/2020/02/02/logback-test-logging-event/)
- [SLF4J Understanding](https://mincong.io/2019/03/12/slf4j/)
- [What I know about logs](https://mincong.io/2019/03/05/logs/)

### Java Testing

Mockito, the most popular mocking framework for
Java unit tests. https://site.mockito.org

- [Mockito: 3 Ways to Init Mock in JUnit 5](https://mincong.io/2020/04/19/mockito-junit5)
- [Mockito: 3 Ways to Init Mock in JUnit 4](https://mincong.io/2019/09/13/init-mock)
- [Mockito: ArgumentCaptor](https://mincong.io/2019/12/15/mockito-argument-captor)
- [Mockito: 4 Ways to Verify Interations](https://mincong.io/2019/09/22/mockito-verify)
- [Testing with GwtMockito](https://mincong.io/2019/08/26/testing-with-gwtmockito)

JUnit, the programmer-friendly testing framework for Java and the JVM:

- [Writing Parameterized Tests in JUnit 5](https://mincong.io/2021/01/31/juni5-parameterized-tests/)
- [JUnit 5: Dynamic Tests with TestFactory](https://mincong.io/2021/04/09/junit-5-dynamic-tests/)

### Java Serialization

- [Use Auto Value and Jackson in REST API](https://mincong.io/2018/06/19/auto-value-and-jackson/)
- [Jackson XML Mapper](https://mincong.io/2019/03/19/jackson-xml-mapper/)
- [Vavr Jackson 1.0.0 Alpha 3 release notes](https://mincong.io/2020/07/11/vavr-jackson-1.0.0-alpha-3/)
- [Making Backward-Compatible Schema Changes in MongoDB](https://mincong.io/2021/02/27/mongodb-schema-compatibility/)

### Reliability

- [Create a Throttler in Java](https://mincong.io/2020/11/07/throttler/)
- [Feature Flag: Making Your Application More Reliable](https://mincong.io/2020/11/11/feature-flag/)

### Elasticsearch

- [Testing Elasticsearch With Docker And Java High Level REST Client](https://mincong.io/2020/04/05/testing-elasticsearch-with-docker-and-java-client/)
- [Testing Elasticsearch with ESSingleNodeTestCase](https://mincong.io/2019/11/24/essinglenodetestcase/)
- [Elasticsearch: cat nodes API](https://mincong.io/2020/03/07/elasticsearch-cat-nodes-api/)
- [Elasticsearch: Scroll API in Java](https://mincong.io/2020/01/19/elasticsearch-scroll-api/)
- [Indexing New Data in Elasticsearch](https://mincong.io/2019/12/02/indexing-new-data-in-elasticsearch/)
- [Common Index Exceptions](https://mincong.io/2020/09/13/es-index-exceptions/)
- [Wrap Elasticsearch Response Into CompletableFuture](https://mincong.io/2020/07/26/es-client-completablefuture/)
- [Discovery in Elasticsearch](https://mincong.io/2020/08/22/discovery-in-elasticsearch/)
- [GC in Elasticsearch](https://mincong.io/2020/08/30/gc-in-elasticsearch/)
- [18 Allocation Deciders in Elasticsearch](https://mincong.io/2020/09/27/shard-allocation/)
- [Using Java Time in Different Frameworks](https://mincong.io/2020/10/25/java-time/)
- [DVF: Indexing New Documents](https://mincong.io/2020/12/16/dvf-indexing/)
- [DVF: Indexing Optimization](https://mincong.io/2020/12/17/dvf-indexing-optimization/)
- [DVF: Storage Optimization](https://mincong.io/2020/12/25/dvf-storage-optimization/)
- [DVF: Snapshot And Restore](https://mincong.io/2021/01/10/dvf-snapshot-and-restore/)

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
