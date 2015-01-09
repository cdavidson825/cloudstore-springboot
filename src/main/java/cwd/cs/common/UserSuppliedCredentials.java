package cwd.cs.common;

import java.security.cert.X509Certificate;

/**
 * This object is used by the Client to provide the user's credentials.
 * Currently, user credentials can either be username/password or X.509 Client
 * PKI.
 */
public class UserSuppliedCredentials
{

    /*
     * The valid credential modes
     */
    public enum CredentialModeEnum
    {
        USERNAME_PASSWORD, CLIENT_PKI
    };

    private final String username;
    private final String password;

    private final X509Certificate clientPKICert;

    private final CredentialModeEnum credentialMode;

    /**
     * Constructor that takes in the username/password
     * @param username
     * @param password
     */
    public UserSuppliedCredentials(String username, String password)
    {
        this.username = username;
        this.password = password;

        clientPKICert = null;

        credentialMode = CredentialModeEnum.USERNAME_PASSWORD;
    }

    /**
     * Constructor that takes in the X.509 Client PKI certificate.
     * @param clientPKI
     */
    public UserSuppliedCredentials(X509Certificate clientPKI)
    {
        this.clientPKICert = clientPKI;

        this.username = null;
        this.password = null;

        credentialMode = CredentialModeEnum.CLIENT_PKI;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public X509Certificate getClientCert()
    {
        return clientPKICert;
    }
    
    public CredentialModeEnum getCredentialMode()
    {
        return credentialMode;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        if (credentialMode == CredentialModeEnum.USERNAME_PASSWORD)
        {
            builder.append("UserSuppliedCredentials [password=");
            builder.append(password);
            builder.append(", username=");
            builder.append(username);
            builder.append("]");
        }
        else if (credentialMode == CredentialModeEnum.CLIENT_PKI)
        {
            builder.append("UserSuppliedCredentials [clientPKI=");
            builder.append(clientPKICert);
            builder.append("]");
        }
        else
        {
            builder.append("UserSuppliedCredentials unknown");
        }
        return builder.toString();
    }

}
