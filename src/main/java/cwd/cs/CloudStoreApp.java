package cwd.cs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;

import cwd.cs.client.ClientDriver;

@SpringBootApplication
@ImportResource("classpath:/spring-config.xml")
public class CloudStoreApp {
    
    private static ApplicationContext springContext;
   
    protected static void setSpringContext(ApplicationContext springContext)
    {
        CloudStoreApp.springContext = springContext;
    }
    
    public static ApplicationContext getSpringContext()
    {
        return springContext;
    }       

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(CloudStoreApp.class, args);
        setSpringContext(ctx);
        
        //Set spring context so other beans can get it if needed.
        CloudStoreApp.setSpringContext(ctx);
        
        //Start client repl so you can interact with the database from the cmd-line.
        ClientDriver client = ctx.getBean(ClientDriver.class);
        client.startRepl();
    }

}
