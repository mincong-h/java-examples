# Java Examples [![Build Status][travis-img]][travis]

A Maven project for learning Java during my free time. Most of the explanations are
written directly in the code as Javadoc. I use tests to understand technical detail
in different frameworks. You can run these tests using:

    mvn clean install

This project is tested under the following Java versions:

- Java 8
- Java 9
- Java 10
- Java 11

However, it does not mean this project is fully compatible to the latest versions.
Some modules cannot be executed are just skipped.

## Module List

Module | Description
:--- | :---
AssertJ | [AssertJ][assertj] testing framework.
Basic | Basic usage of Java core APIs.
Byteman | [byteman][bm] changes expected Java behavior and JDK runtime code.
CLI | A fake command-line interface (CLI).
Clone | Test behavior of `java.lang.Clonable#clone()`.
Date | Date manipulation using `java.util.*` and `java.time.*`.
Java 8 | New functionality of Java 8, including filter, map, stream.
JGit | Basic usages of [JGit][jgit].
JSF | Basic usage of JavaServer Faces, a Java-based web application framework.
JSON | JSON conversion libraries in Java.
JUnit | JUnit testing framework.
Maven | Basic functionality of Maven.
Mock | Different mocking frameworks, e.g. Mockito, Easy Mock and Power Mock.
OCA | Oracle Certified Associate Java SE 8
OCP | Oracle Certified Professional Java SE 8
Regex | Regular expressions.
Rest | RESTful API using [Jersey][jersey].
Selenium | Functional tests using Selenium.
XML | XML serialization and XML path language (XPath).

## Code Style

I use [Google Java Code Style][style-java] for this repo.

[assertj]: http://joel-costigliola.github.io/assertj/
[bm]: http://byteman.jboss.org
[jersey]: https://jersey.github.io
[jgit]: https://github.com/eclipse/jgit
[style-java]: https://google.github.io/styleguide/javaguide.html
[travis]: https://travis-ci.org/mincong-h/java-examples
[travis-img]: https://travis-ci.org/mincong-h/java-examples.svg?branch=master
