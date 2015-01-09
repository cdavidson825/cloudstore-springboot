package cwd.cs.sample;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("Normal")
public class NormalGreeting implements Greeting
{

    @Override
    public String sayHello()
    {
        return "Hello";
    }

}
