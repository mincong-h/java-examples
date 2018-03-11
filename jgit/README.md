# JGit

Study notes when learning JGit, a lightweight, pure Java library implementing
the Git version control system.

## Command Design Pattern

In object-oriented programming, the command pattern is a behavioral design
pattern in which an object is used to encapsulate all information needed to
perform an action or trigger an event at a later time. This information
includes the method name, the object that owns the method and values for the
method parameters.

In JGit, commands are written in command pattern. For example, the
[git-commit][git-commit] command can be used as following:

```java
RevCommit c = git.commit()
                 .setMessage("Initial commit")
                 .setAuthor("Foo", "foo@example.com")
                 .call();
```

So information like message and author are stored and encapsulated before the
action perform. Now, I'm going to explore more about command design pattern by
analysing class [`CommitCommand`][CommitCommand-javadoc]. Running a command can
be split into 3 steps:

- **Initialization**: create the command from command factory (Git here).
- **Recording**: collect the necessary information before performing the
  action.
- **Execution**: validate the information received, call the command, and
  return an object for presenting the result or for further tracking.

During the execution phase, a request need to be validated first. Then, command
processes options configured during the recording phase. According to contexts,
the workflow can vary in different way. If the business logic is too complex,
the code can be split into different helper methods.

## References

- [Wikipedia: Command pattern][cmd-wiki]
- [Git: git-commit - Record changes to the repository][git-commit]
- [Javadoc: org.eclipse.jgit.api.CommitCommand][CommitCommand-javadoc]

[git-commit]: https://git-scm.com/docs/git-commit
[cmd-wiki]: https://en.wikipedia.org/wiki/Command_pattern
[CommitCommand-javadoc]: http://download.eclipse.org/jgit/site/4.8.0.201706111038-r/apidocs/org/eclipse/jgit/api/CommitCommand.html
