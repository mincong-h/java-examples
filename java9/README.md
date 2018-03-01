# Java 9

Here's a module for exploring the new features of Java 9.

## JShell

The Java Shell tool (JShell) is an interactive tool for learning the Java
programming language and prototyping Java code. JShell is a Read-Evaluate-Print
Loop (REPL), which evaluates declarations, statements, and expressions as they
are entered and immediately shows the results. The tool is run from the command
line.

```
~ $ jshell
|  Welcome to JShell -- Version 9.0.4
|  For an introduction type: /help intro

jshell> int a = 1
a ==> 1

jshell> int b = 2
b ==> 2

jshell> a + b
$3 ==> 3

jshell> /exit
|  Goodbye
~ $
```

## References

- [Baeldung: Java 9 New Features][1]
- [Oracle: Java Platform, Standard Edition Java Shell User’s Guide][2]

[1]: http://www.baeldung.com/new-java-9
[2]: https://docs.oracle.com/javase/9/jshell/introduction-jshell.htm
