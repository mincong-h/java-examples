package io.mincongh.akka;

import akka.actor.typed.ActorSystem;
import io.mincongh.akka.GreeterMain.Start;
import java.io.IOException;

/**
 * @author Akka Authors
 * @since 0.1.0
 */
public class AkkaQuickstart {
  public static void main(String[] args) {
    //#actor-system
    final ActorSystem<Start> greeterMain = ActorSystem
        .create(GreeterMain.create(), "helloakka");
    //#actor-system

    //#main-send-messages
    greeterMain.tell(new GreeterMain.Start("Charles"));
    //#main-send-messages

    try {
      System.out.println(">>> Press ENTER to exit <<<");
      System.in.read();
    } catch (IOException ignored) {
    } finally {
      greeterMain.terminate();
    }
  }
}
