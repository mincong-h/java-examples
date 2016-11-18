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

[travis]: https://travis-ci.org/mincong-h/java-examples
[travis-img]: https://travis-ci.org/mincong-h/java-examples.svg?branch=master
