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
