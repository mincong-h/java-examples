package io.mincongh.dev.core;

import io.mincongh.dev.api.Command;

public class InstallCommand implements Command {
  @Override
  public void execute() {
    System.out.println("Installed.");
  }
}
