package io.mincong.junit5.parameterized_test;

public class StringFormatChatBot implements ChatBot {
  @Override
  public String sayHello(String username) {
    return String.format("Hello, %s", username);
  }
}
