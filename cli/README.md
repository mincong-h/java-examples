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
- `before-command`: Operations before launching the command.
- `command`: Command execution.
- `after-command`: Operations after launching the command.
- `exit`: Launcher tear down and exit.

If multiple commands exist, the lifecycle will be:

```
launcher::init
cmd-a::before-command
cmd-a::command
cmd-b::after-command
cmd-b::before-command
cmd-b::command
cmd-b::after-command
cmd-c::before-command
cmd-c::command
cmd-c::after-command
launcher::exit
```

## Command Type

By execution logic:

- Unit command
- Composite command

By business logic:

- Marketplace command
- Server command

## Pending Commands

Pending commands are commands given by users but not yet executed. Pending
commands consists 2 parts:

- Part 1: commands given by previous launcher execution
- Part 2: commands given by current launcher execution

Part 1 take precedence over part 2: part 2 will be executed only after part 1 is
finished.

During a normal lifecycle, pending commands are queued. They will be consumed
one by one by launcher until nothing left. In the event of an anomaly, the
pending commands are serialized and persisted into file system. Thus, launcher
can resume the command executions after its restart. When restarting launcher,
the pending commands are deserialized and enqued as part 1. Then, persistent
file will be backed up—which means the original file does not exist anymore.

## Pending Commands File

- Empty line will not be executed
- Line starting with `#` is considered as comment, will not be executed

## Dry Run

You might want to do a dry-run without executing any commands in real. The
estimated output will be displayed, but the server itself won't be modified.

## OS Support

Launcher can be used for Windows, macOS, and Linux.

## Exception Handling

Launcher should never exit during a command execution. If any failure occurs,
the command will stop and raise an exception. Exception will be caught by an
exception mapper, which handles it properly. The handling mechanism is triggered
in _after-command_ phase. If any severe error happens, launcher might quit the
Java process: an explicit error message will be displayed in the console, and a
non-zero exit code will be returned.

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
- What is the difference between command and task?

### i18n

### Package

- Remote package: package stored in remote repository and can be downloaded.
- Local package: package started in local repository or being used.

### Package Resolution

## Unclear

What're operations:

- purge:
- remove:
- uninstall:

## References

- [Manual `systemctl`](https://www.freedesktop.org/software/systemd/man/systemctl.html)

[commons-cli]: https://github.com/apache/commons-cli
