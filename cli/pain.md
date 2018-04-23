## Pain Points

Pain points of the actual launcher.

## Launcher Architecture

Launcher contains multiple methods, each commands is a method. The logic is not
very clear and hard to read.

## Method pkgRequest

The method `pkgRequest` is very confusing. Actually:

- **mp-hotfix** is resolved as a mp-request before being executed.
- **mp-purge** is resolved as a mp-request before being executed.
- **mp-upgrade** is resolved as a mp-request before being executed.

So it means that **mp-hotfix**, **mp-purge**, **mp-upgrade** are all composite
commands. Composite commands are composed by multiple unit commands. Among these
unit commands, some of them might require launcher to restart—a pending queue is
required to resume the remaining actions.

Confusing points:

- Too many inputs for a `pkgRequest`.
- Notion of composite command and unit command.
- `pkgRequest` is not only a request, but also implies resolution.
- How to resume pending tasks or pending commands?

## Test TestConnectBroker

- The list of package status is hidden in `.package`.
- It asserts log.
- It stores binaries, e.g. ZIP files.
- The set-up context is too complex.
- It isn't a unit test, it's an integration test.
- Package names are too magic: A, B, C, ...
