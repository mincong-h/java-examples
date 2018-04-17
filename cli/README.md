# Fake CLI

Fake CLI is a fake command-line interface for command line self training.

## Synopsis

    fakectl COMMAND [OPTIONS...]

## Description

**fakectl** may be used to introspect and control the state of a fake server.

## Options

The following options are understood:

**-h,--help**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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

**mp-install**

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

- Remote package: package stored in remote and can be downloaded.
- Local package: package started in local. (TODO: how to distinguish
  local-stored and local-used packages?)

## Unclear

What're operations:

- purge:
- remove:
- uninstall:

[commons-cli]: https://github.com/apache/commons-cli
