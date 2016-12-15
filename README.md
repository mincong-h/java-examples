[![Build Status][travis-img]][travis]

# Java Examples

A multi-module Maven project for learning Java. Most of the explanations are
written directly in the code as javadoc. This is a Maven project, you can run it
using:

    mvn clean install

### Module Clone

The module _Clone_ tests the behaviors of the method `Clonable#clone()` by
comparing the original object and cloned object through different class types.
So after the clone, what are the differences by reference and by value?

### Module Byteman

The module _Byteman_ uses the [byteman][bm] tool to change expected behaviour
of the Java application and JDK runtime code. It injects Java code into my
application without the need to recompile. In this module, the class `Counter`
is supposed to count a number from 0 to 10. Due to the bytemen script
`check.btm`, the counter stops at number 6 (exception thrown at i = 5).

### Module Java 8

The module _Java 8_ tests the new functionality of Java 8, including filter,
map, stream and more.

### Module JSF

The module _JSF_ demonstrated the basic usage of JavaServer Faces, a Java-based
web application framework intended to simplify development integration of
web-based user interfaces. JavaServer Faces is a standardized display technology
which was formalized in a specification through the Java Community Process.

### Module RESTful API

The module _RESTful API_ tests the basic usage of RESTful API. I'm following
tutorials from [Mkyong - Jersey hello world example][mkyong-rest] and
[Jersey - Getting started][jersey-getting-started].

## Code Style

I use [Google Java Code Style][style-java] for this repo.

[bm]: http://byteman.jboss.org
[jersey-getting-started]: https://jersey.java.net/documentation/latest/getting-started.html
[mkyong-rest]: https://www.mkyong.com/webservices/jax-rs/jersey-hello-world-example/
[style-java]: https://google.github.io/styleguide/javaguide.html
[travis]: https://travis-ci.org/mincong-h/java-examples
[travis-img]: https://travis-ci.org/mincong-h/java-examples.svg?branch=master
