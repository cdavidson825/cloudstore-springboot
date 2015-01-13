package cwd.si;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class SpringEnabledTest
{
    
    public SpringEnabledTest()
    {
        //Set the test profile so the test objects get mocked in.
        System.setProperty("spring.profiles.active", "test");
    }

}
