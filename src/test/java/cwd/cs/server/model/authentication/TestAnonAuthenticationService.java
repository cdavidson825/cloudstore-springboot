package cwd.cs.server.model.authentication;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import cwd.cs.SpringEnabledTest;
import cwd.cs.common.TestUserSuppliedCredentials;
import cwd.cs.common.UserSuppliedCredentials;
import cwd.cs.server.model.authentication.AuthenticationService;
import cwd.cs.server.model.authentication.UserCredentials;

public class TestAnonAuthenticationService extends SpringEnabledTest
{
    @Autowired
    @Qualifier("Anon")
    AuthenticationService authService = null;

    List<String> validCreds = Arrays.asList("anon", "Anon a b c", "ANON d e f",
            "Anonymous FOO BAR", "ANON1 123");
    List<String> invalidCreds = Arrays.asList("an", "Ano n", "bogus anon", "FOO BAR", "ANO1 123");

    @Test
    public void testMockAuthForAnonCert()
    {

        for (String userDN : validCreds)
        {
            UserCredentials creds =
                    new UserCredentials(new UserSuppliedCredentials(
                            TestUserSuppliedCredentials.getMockedCert(userDN)));
            assertThat("Should be valid: " + userDN, true, is(authService.authenticateUser(creds)));
        }
    }

    @Test
    public void testMockAuthForAnonUserPass()
    {

        for (String username : validCreds)
        {
            UserCredentials creds =
                    new UserCredentials(new UserSuppliedCredentials(username, "bogus"));
            assertThat("Should be valid: " + username, true,
                    is(authService.authenticateUser(creds)));
        }
    }

    @Test
    public void testMockAuthForInvalidCert()
    {

        for (String userDN : invalidCreds)
        {
            UserCredentials creds =
                    new UserCredentials(new UserSuppliedCredentials(
                            TestUserSuppliedCredentials.getMockedCert(userDN)));
            assertThat("Should be invalid: " + userDN, false,
                    is(authService.authenticateUser(creds)));
        }
    }

    @Test
    public void testMockAuthForInvalidUserPass()
    {

        for (String username : invalidCreds)
        {
            UserCredentials creds =
                    new UserCredentials(new UserSuppliedCredentials(username, "bogus"));
            assertThat("Should be invalid: " + username, false,
                    is(authService.authenticateUser(creds)));
        }
    }

}
