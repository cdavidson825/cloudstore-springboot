package cwd.cs.client;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import cwd.cs.client.command.CommandProcessor;
import cwd.cs.client.command.CommandResponse;


@Component
public class ClientDriver
{
    protected static final List<String> QUIT_COMMANDS = Arrays.asList("QUIT", "EXIT", "Q", "BYE",
            "GOODBYE");
    protected static final String COMMAND_PROMPT = "StoreIt repl > ";
    @Autowired
    private CommandProcessor commandProcessor;


    public void startRepl()
    {
        Scanner inputScanner = new Scanner(System.in);
        String input = "";
        do
        {
            System.out.print(COMMAND_PROMPT);
            input = inputScanner.nextLine();
            if (!input.isEmpty() && !QUIT_COMMANDS.contains(input.toUpperCase()))
            {
                String[] commands = input.trim().split("\\s++");
                CommandResponse response;
                try
                {
                    response = execute(commands);

                }
                catch (Exception e)
                {
                    System.out.println("caught exception: "+ e.getMessage());
                    System.out.println("printing --help");
                    response = execute(new String[] {"--help"});
                }
                response.setCommand(input);
                System.out.println(response);
            }
        }
        while (!QUIT_COMMANDS.contains(input.toUpperCase()));

        inputScanner.close();
    }

    public CommandResponse execute(String[] inputCommand)
    {
        return (commandProcessor.execute(inputCommand));
    }

    public static void main(String[] args)
    {

        // System.setProperty("spring.profiles.active", "dev");
        @SuppressWarnings("resource")
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        ClientDriver client = context.getBean(ClientDriver.class);

        // Run the passed in command (if any), otherwise, run the repl.
        if (args.length > 0)
        {
            CommandResponse response = client.execute(args);
            System.out.println(response);
        }
        else
        {
            client.startRepl();
        }


    }

}
