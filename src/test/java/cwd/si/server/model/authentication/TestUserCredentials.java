package cwd.si.server.model.authentication;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.security.cert.X509Certificate;

import org.junit.Test;

import cwd.cs.common.UserSuppliedCredentials;
import cwd.cs.server.model.authentication.UserCredentials;
import cwd.si.common.TestUserSuppliedCredentials;

public class TestUserCredentials
{

    @Test
    public void testUserCredsWithUserNamePassword()
    {
        UserCredentials creds =
                new UserCredentials(new UserSuppliedCredentials("fake_user", "incorrect"));
        assertThat(creds.getUserId(), is("fake_user"));
    }

    @Test
    public void testUserCredsWithPKICert()
    {
        X509Certificate mockCert = TestUserSuppliedCredentials.getMockedCert("My Fake DN");
        UserCredentials creds = new UserCredentials(new UserSuppliedCredentials(mockCert));
        assertThat(creds.getUserId(), is("My Fake DN"));
    }
    
}
