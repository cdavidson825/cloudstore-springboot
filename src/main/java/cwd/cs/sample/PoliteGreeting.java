package cwd.cs.sample;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component
@Qualifier("Polite")
public class PoliteGreeting implements Greeting
{

    @Override
    public String sayHello()
    {
        return "Top of the morning to you";
    }

}
