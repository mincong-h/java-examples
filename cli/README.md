# FakeCTL

FakeCTL is a fake command-line interface for command line self training. It
consists 2 parts: Fake Launcher, a Java application for different command line
usages; `fakectl`, a bash script to interact with Fake Launcher.

## Synopsis

    fakectl COMMAND [OPTIONS...]

You can also build it from the source:

    $ mvn clean install
    $ java -jar java-examples-cli-0.0.1-SNAPSHOT.jar <COMMAND> [OPTIONS...]

## Description

**fakectl** may be used to introspect and control the state of a fake server.

## Options

The following options are understood:

Option                                                            | Description
:---------------------------------------------------------------- | :---
`-h,--help`                                                       | Show detailed help.
`-q,--quiet`                                                      | Suppress information messages.
`-d,--debug <categories>`                                         | Activate debug messages. (Since Fake 7.4) `<categories>`: comma-separated Java categories to debug.
~~`-dc <categories>`~~                                            | (Since Fake 5.6) Deprecated: see `--debug <categories>`.
`--debug-launcher`                                                | (Since Fake 5.9.4) Linux-only. Activate Java debugging mode on the Launcher.
`--clid <arg>`                                                    | (Since Fake 6.0) Use the provided instance CLID file.
`--xml`                                                           | (Since Fake 5.6) Output XML for marketplace commands.
`--json`                                                          | (Since Fake 5.6) Output JSON for marketplace commands.
<code>--gui <true&#124;false&#124;yes&#124;no></code>             | (Since Fake 5.6) Start the Graphical User Interface (aka Fake Control Panel). Default is true on Windows and false on other platforms.
`--nodeps`                                                        | (Since Fake 5.6) Ignore package dependencies and constraints.
<code>--relax <true&#124;false&#124;yes&#124;no&#124;ask></code>  | (Since Fake 5.6) Allow relax constraint on current platform (default: ask).
<code>--accept <true&#124;false&#124;yes&#124;no&#124;ask></code> | (Since Fake 5.6) Accept, refuse or ask confirmation for all changes (default: ask). In non interactive mode, `--accept=true` also sets `--relax=true` if needed.
`-s,--snapshot`                                                   | (Since Fake 5.9.1) Allow use of SNAPSHOT Marketplace packages. This option is implicit: on SNAPSHOT distributions (daily builds), if the command explicitly requests a SNAPSHOT package.
`-f,--force`                                                      | (Since Fake 5.9.1) Deprecated: use --strict option instead.
`--strict`                                                        | (Since Fake 7.4) Abort in error the start command when a component cannot be activated or if a server is already running.
`-im,--ignore-missing`                                            | (Since Fake 6.0) Ignore unknown packages on mp-add, mp-install and mp-set commands.
`-hdw,--hide-deprecation-warnings`                                | (Since Fake 5.6) Hide deprecation warnings.
`--encrypt <algorithm>`                                           | (Since Fake 7.4) Activate encryption on the config command. This option can be used on the encrypt command to customize the encryption algorithm. `<algorithm>` is a cipher transformation of the form: "algorithm/mode/padding" or "algorithm". Default value is "AES/ECB/PKCS5Padding" (Advanced Encryption Standard, Electronic Cookbook Mode, PKCS5-style padding).
`--gzip`                                                          | Compress the output.
`--pretty-print`                                                  | Pretty print the output.
`--output <file>`                                                 | Write output in specified file.
`--set <template>`                                                | (Since Fake 7.4) Set the value for a given key. The value is stored in fake.conf by default unless a template name is provided; if so, it is then stored in the template's fake.defaults file. If the value is empty (''), then the property is unset. This option is implicit if no `--get` or `--get-regexp` option is used and there are exactly two parameters (key value).
`--get-regexp`                                                    | (Since Fake 7.4) Get the value for all keys matching the given regular expression(s).
`--get`                                                           | (Since Fake 7.4) Get the value for a given key. Returns error code 6 if the key was not found. This option is implicit if `--set` option is not used and there are more or less than two parameters.

## Commands

The following commands are understood:

### Server Commands

#### start

Start Fake Server in background, waiting
for effective start. Useful for batch executions requiring the server being
immediately available after the script returned. Note: on Windows, the
`start` command launches the Control Panel.

#### stop

Stop any Fake Server started with the same `fake.conf` file.

#### restart

#### console

Start Fake Server in a console mode. <kbd>CTRL</kbd> + <kbd>C</kbd> will stop it.

#### status

#### startbg

#### restartbg

### Environment Commands

#### showconf

### Marketplace Commands

#### mp-add

#### mp-hotfix

Composite command, package resolution required.

#### mp-init

#### mp-install

    mp-install PACKAGES...
    mp-install (-f | --file) PACKAGE

Run Marketplace package installation. You must provide at least one package
value. A package value can be a package name or a package ID. Multiple values
are supported. When giving a package name, it will be resolved to an ID before
doing an effective installation.

If file flag `-f,--file` is present, then the command refers to a local
installation command. It will lookup the package file in file system. Only one
file is allowed. Local installation command connte be mixed with remote
installation command.

This command is automatically called at startup if `installAfterRestart.log`
file exists in data directory.

#### mp-list

#### mp-listall

**mp-listall** is not supported by fake launcher. Please use the following
alternatives:

- `mp-list -a` to list both local and remote packages.
- `mp-list --all` to list both local and remote packages.
- `mp-list --remote` to list remote packages.

#### mp-uninstall

#### mp-update

#### mp-upgrade

Composite command, package resolution required.

#### mp-purge

Composite command, package resolution required.

#### mp-remove

#### mp-request

#### mp-reset

#### mp-show

### Other Commands

TODO Should be categorized properly

#### help

Print help message.

#### gui

Deprecated. Use `--gui` option instead.

#### config

#### encrypt

#### decrypt

#### configure

#### wizard

#### pack

#### connect-report

#### register

## Exit Value

Launcher exit codes. These values follow the [Linux Standard Base Core
Specification 4.1][LSB-20.2].

If the status command is requested, launcher will turn the following exit status codes:

Value | Description
:---: | :---
0     | Program is running or service is OK.
3     | Program is not running.
4     | Program or service status is unknown.

In case of an error while processing any action except for status, launcher
will print an error message and exit with a non-zero status code:

Value | Description
:---: | :---
1     | Generic or unspecified error
2     | Invalid or excess argument(s)
3     | Unimplemented feature
4     | User had insufficient privilege
5     | Program is not installed
6     | Program is not configured
7     | Program is not running

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

## Command

A command execution should return a result, an instance of type T to allow
launcher follow its evolution. Void is also a valid result.

### Command Type

By business logic:

- Marketplace command
- Server command

By execution logic:

- Unit command
- Composite command

A composite command can be resolved as multiple unit commands. Regardless unit
or composite commands, each command has its own lifecycle. If a command fails to
handle its lifecycle, a lifecycle exception will be thrown. Such exception
should be handled by the higher lifecycle. For more detail, please refer to
[Exception Handling](#exception-handling).


### Pending Commands

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

### Pending Commands File

- Empty line will not be executed.
- Comment line will not be executed (line starting with symbol `#`).

## Task

Task is the lowest level of work in launcher. One command contains one or more
tasks.

### Pending Tasks

Pending tasks are tasks which are not yet executed by a command. They are
serializable. Pending tasks should be executed before pending commands. You can
consider pending tasks as a « partial » pending command.

## Dry Run

You can do a dry-run without executing any commands in real. The
estimated output will be displayed, but fake server itself won't be modified.

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

## i18n

## Package

- Remote package: package stored in remote repository and can be downloaded.
- Local package: package started in local repository or being used.

### Package Resolution

## Null Check

By default, all objects served as input parameter or returned value are
considered as non-null. If you're using nullable object, you must declare
annotation `@Nullable`:

```java
@Nullable
public Foo getFoo() { ... }
```

## Testing

## References

- [Manual `systemctl`](https://www.freedesktop.org/software/systemd/man/systemctl.html)

[commons-cli]: https://github.com/apache/commons-cli
[LSB-20.2]: http://refspecs.linuxfoundation.org/LSB_4.1.0/LSB-Core-generic/LSB-Core-generic/iniscrptact.html
