package me.nielcho.akka.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

public class Greeter extends AbstractActor {

    public static Props props(String message, final ActorRef printerActor) {
        return Props.create(Greeter.class, () -> new Greeter(message, printerActor));
    }

    public static class WhoToGreet {
        public final String who;

        public WhoToGreet(String who) {
            this.who = who;
        }
    }

    public static class Greet {
        public Greet() {
        }
    }

    private final String message;
    private final ActorRef printActor;
    private String greeting = "";

    public Greeter(String message, ActorRef printActor) {
        this.message = message;
        this.printActor = printActor;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(WhoToGreet.class, wtg -> this.greeting = message + ", " + wtg.who)
                .match(Greet.class, x -> printActor.tell(new Printer.Greeting(greeting), getSelf()))
                .build();
    }
}
