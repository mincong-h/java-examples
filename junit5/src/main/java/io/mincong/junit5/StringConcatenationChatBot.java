package io.mincong.junit5;

public class StringConcatenationChatBot implements ChatBot {

  @Override
  public String sayHello(String username) {
    return "Hello, " + username;
  }
}
