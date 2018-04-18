# Fake CLI

Fake CLI is a fake command-line interface for command line self training.

## Synopsis

    fakectl COMMAND [OPTIONS...]

## Description

**fakectl** may be used to introspect and control the state of a fake server.

## Options

The following options are understood:

**-h,--help**

Show detailed help.

## Commands

The following commands are understood:

### Server Commands

**start**

**stop**

**restart**

**console**

**status**

**startbg**

**restartbg**

### Environment Commands

**showconf**

### Marketplace Commands

**mp-add**

**mp-hotfix**

**mp-init**

#### mp-install

    mp-install PACKAGES...
    mp-install (-f | --file) PACKAGE

Run Marketplace package installation. You must provide at least one package
value. A package value can be a package name or a package ID. Multiple values
are supported. When giving a package name, it will be resolved to an ID before
doing an effective installation.

If file flag `-f,--file` is present, then the command refers to a local
installation command. It will lookup the package file in file system. Only one
file is allowed.

This command is automatically called at startup if `installAfterRestart.log`
file exists in data directory.

**mp-list**

**mp-uninstall**

**mp-update**

**mp-upgrade**

**mp-purge**

**mp-remove**

**mp-request**

**mp-reset**

**mp-show**

### Other Commands

TODO Should be categorized properly

**help**

~~**gui**~~

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Deprecated. Use `--gui` option instead.

**config**

**encrypt**

**decrypt**

**configure**

**wizard**

**pack**

**connect-report**

**register**

## Exit Status

## Environment Variables

OS level variables read by `fakectl`.

# Specification

## Launcher Lifecycle

Launcher lifecycle is defined as following stages:

- `init`: Launcher initialization.
- `pre-command`: Operations before launching the command.
- `command`: Command execution.
- `post-command`: Operations after launching the command.
- `exit`: Launcher tear down and exit.

If multiple commands exist, the lifecycle will be:

```
Launcher::init
CommandA::pre-command
CommandA::command
CommandA::post-command
CommandB::pre-command
CommandB::command
CommandB::post-command
CommandC::pre-command
CommandC::command
CommandC::post-command
Launcher::exit
```

## Pending Commands

Pending commands are commands given by users but not yet executed. Pending
commands consists 2 parts:

1. given by previous launcher execution, left in a persistent file
2. given by current launcher execution, entered by user

Part 1 has higher priority for execution. Part 2 will execute once part 1 is
finished.

During a normal lifecycle, pending commands are consumed one by one by launcher and
nothing left at the end. In the event of an anomaly, the pending commands are
serialized and persisted into file system. Thus, launcher can resume the command
executions after its restart. When restarting launcher, the pending commands are
deserialized from the persistent file. Then, file will be backed up for further
usages.

## OS Support

Launcher can be used for Windows, macOS, and Linux.

## Exception Handling

Launcher should never exit during a command execution. If any failure occurs,
the command stops and raise an exception. Exception will be caught by an
exception mapper, which handles it properly. The handling mechanism is triggered
in _post-command_ phase. If any severe error happens, launcher can decide to quit
the Java process: an explicit error message should be displayed in the console, and a
non-zero exit code should be returned.

# Implementation

## Ideas

- Fake CLI is a command-based launcher.
- Each command should have pre-command and post-command hook.
- Command might fail. A failed command should _never_ exit the JVM itself, exit the JVM is an
  application-level decision. It can only be decided by the launcher itself.
- It's crucial to have checkpoints. A failed persistent command must be able to be resumed, even if
  launcher has exited. Command data is saved as persistent data.
- The result of a command should not be represented as boolean. Using enum or object is better: it
  provides more information about the result.
- The result of a command is returned as `CommandStatus` (like HTTP status), it is composed by
  command exit code and command line message.
- Options are handled by library [Commons CLI][commons-cli].
- A complex command is a composition of multiple unitary commands.
- A unitary command can _not_ be composed by other commands.
- Options can be grouped. For example, server options or marketplace options.
- Package name is resolved before running effective commands.
- The command is always executed using package ID.
- Command can be created in 2 ways: by passing arguments or by constructing a
  Java object. The 1st case refers to the real world scenario, and the 2nd case
  refers to programatical scenario (e.g. tests).

### i18n

### Package

- Remote package: package stored in remote repository and can be downloaded.
- Local package: package started in local repository or being used.

## Unclear

What're operations:

- purge:
- remove:
- uninstall:

## References

- [Manual `systemctl`](https://www.freedesktop.org/software/systemd/man/systemctl.html)

[commons-cli]: https://github.com/apache/commons-cli
