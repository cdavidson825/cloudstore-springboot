package cwd.si.sample;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cwd.cs.sample.Greeting;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class TestSample
{

    @Autowired
    @Qualifier("Normal")
    Greeting normalGreeting = null;
    
    @Autowired
    @Qualifier("Slang")
    Greeting slangGreeting = null;
    
    @Autowired
    @Qualifier("Polite")
    Greeting politeGreeting = null;
    
    @Test
    public void TestAutowireNormalGreeting()
    {
        assertThat(normalGreeting.sayHello(), is("Hello"));
    }
    
    @Test
    public void TestAutowireSlangGreeting()
    {
        assertThat(slangGreeting.sayHello(), is("Sup"));
    }

    @Test
    public void TestAutowirePoliteGreeting()
    {
        assertThat(politeGreeting.sayHello(), is("Top of the morning to you"));
    }

}

