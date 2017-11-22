package me.nielcho.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import me.nielcho.akka.actor.Greeter;
import me.nielcho.akka.actor.Printer;

import java.io.IOException;

public class Application {
    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create();
        try {
            ActorRef printerActor = actorSystem.actorOf(Printer.props(), "printerActor");
            ActorRef howdyGreeter =
                    actorSystem.actorOf(Greeter.props("Howdy", printerActor), "howdyGreeter");
            ActorRef helloGreeter =
                    actorSystem.actorOf(Greeter.props("Hello", printerActor), "helloGreeter");
            ActorRef goodDayGreeter =
                    actorSystem.actorOf(Greeter.props("Good day", printerActor), "goodDayGreeter");

            howdyGreeter.tell(new Greeter.WhoToGreet("Akka"), ActorRef.noSender());
            howdyGreeter.tell(new Greeter.Greet(), ActorRef.noSender());

            howdyGreeter.tell(new Greeter.WhoToGreet("Lightbend"), ActorRef.noSender());
            howdyGreeter.tell(new Greeter.Greet(), ActorRef.noSender());

            helloGreeter.tell(new Greeter.WhoToGreet("Java"), ActorRef.noSender());
            helloGreeter.tell(new Greeter.Greet(), ActorRef.noSender());

            goodDayGreeter.tell(new Greeter.WhoToGreet("Play"), ActorRef.noSender());
            goodDayGreeter.tell(new Greeter.Greet(), ActorRef.noSender());

            System.out.println(">>> Press ENTER to exit <<<");

            System.in.read();
        } catch (IOException e) {
            //
        } finally {
            actorSystem.terminate();
        }

    }
}
