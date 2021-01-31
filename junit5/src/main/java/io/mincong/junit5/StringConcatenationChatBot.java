package io.mincong.junit5;

public class StringConcatenationChatBot implements ChatBot {

  @Override
  public String sayHello(String username) {
    return "Hello, " + username;
  }

  /**
   * In IntelliJ IDEA, run ChatBotTest with coverage and observe the test coverage here. You can see
   * that this test is not tested. One advantage of using parameterized testing is that it can
   * increase the test coverage easily with difference scenarios.
   */
  @SuppressWarnings("unused")
  public String sayNo(String username) {
    return "No, " + username;
  }
}
