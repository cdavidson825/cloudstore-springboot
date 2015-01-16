package cwd.cs.common;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.security.Principal;
import java.security.cert.X509Certificate;

import org.junit.Test;
import org.mockito.Mockito;

import cwd.cs.common.UserSuppliedCredentials;


public class TestUserSuppliedCredentials
{
    @Test
    public void testUserNamePasswordCreds()
    {
        String user = "bogusUser";
        String passwd = "bogusPassword";
        UserSuppliedCredentials creds = new UserSuppliedCredentials(user, passwd);
        assertThat(creds.getCredentialMode(),
                is(UserSuppliedCredentials.CredentialModeEnum.USERNAME_PASSWORD));
        assertThat(creds.getUsername(), is(user));
        assertThat(creds.getPassword(), is(passwd));
        assertNull(creds.getClientCert());
    }

    @Test
    public void testCertCreds()
    {
        X509Certificate mockedCert = getMockedCert("Mocked Chris Davidson");
        UserSuppliedCredentials creds = new UserSuppliedCredentials(mockedCert);
        assertThat(creds.getCredentialMode(),
                is(UserSuppliedCredentials.CredentialModeEnum.CLIENT_PKI));
        assertNull(creds.getUsername());
        assertNull(creds.getPassword());
        assertThat(creds.getClientCert(), is(mockedCert));
        assertThat(creds.getClientCert().getSubjectDN().toString(), is("Mocked Chris Davidson"));
    }

    public static X509Certificate getMockedCert(String userDN)
    {
        final Principal principal = Mockito.mock(Principal.class);
        final X509Certificate cert = Mockito.mock(X509Certificate.class);
        Mockito.when(cert.getSubjectDN()).thenReturn(principal);
        Mockito.when(principal.toString()).thenReturn(userDN);
        return cert;
    }

}
