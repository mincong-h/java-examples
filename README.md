[![Build Status][travis-img]][travis]

# Java Examples

A multi-module Maven project for learning Java. Most of the explanations are
written directly in the code as javadoc. This is a Maven project, you can run it
using:

    mvn clean install

## clone

The module _clone_ tests the behaviors of the method `Clonable#clone()` by
comparing the original object and cloned object through different class types.
So after the clone, what are the differences by reference and by value?

## byteman

The module _byteman_ uses the [byteman][bm] tool to change expected behaviour
of the Java application and JDK runtime code. It injects Java code into my
application without the need to recompile. In this module, the class `Counter`
is supposed to count a number from 0 to 10. Due to the bytemen script
`check.btm`, the counter stops at number 6 (exception thrown at i = 5).

[bm]: http://byteman.jboss.org
[travis]: https://travis-ci.org/mincong-h/java-examples
[travis-img]: https://travis-ci.org/mincong-h/java-examples.svg?branch=master
