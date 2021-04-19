# Akka

Build highly concurrent, distributed, and resilient message-driven applications on the JVM. <https://github.com/akka/akka>

## Run

Running the [official quickstart demo](https://developer.lightbend.com/guides/akka-quickstart-java/) provided by Lightend:

    mvn compile exec:exec@official

Running my Game of Throne demo to see the Army of the Dead (Night King and White Walkers):

    mvn compile exec:exec@game-of-throne


## Actor Path

    akka://sys@hostA:2552          # ActorSystem
    akka://sys@hostB:2552          # ActorSystem
    akka://sys@hostC:2552          # ActorSystem
    akka://sys@hostA:2552/user     # User guardian, parent
    akka://sys@hostA:2552/user/a   # child
    akka://sys@hostA:2552/user/b   # child

## References

- Akka, "What is an Actor?", _Akka Documentation_, 2019.
  <https://doc.akka.io/docs/akka/current/general/actors.html>
- Lightend, "Akka Quickstart with Java", _Lightend_, 2019.
  <https://developer.lightbend.com/guides/akka-quickstart-java/>
