package cwd.cs.sample;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("Slang")
public class SlangGreeting implements Greeting
{
    @Override
    public String sayHello()
    {
        return("Sup");
    }
}
