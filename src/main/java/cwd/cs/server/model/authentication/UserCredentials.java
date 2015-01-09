package cwd.cs.server.model.authentication;

import cwd.cs.common.UserSuppliedCredentials;
import cwd.cs.common.UserSuppliedCredentials.CredentialModeEnum;

/**
 * The server-side object containing the User's credentials. This object takes in the
 * UserSuppliedCredentials object (either username/password or X.509 client PKI) and translates them
 * to a common "credentials" object used to interface the plugable Authentication service.
 */
public class UserCredentials
{

    private final UserSuppliedCredentials userSuppliedCredentials;

    /**
     * Constructor that takes in the UserSuppliedCredentials object and performs the necessary
     * translation into the common credentials used by the rest of the system.
     * 
     * @param userSuppliedCreds The UserSuppliedCredentials object (containing either the
     *        username/password or X.509 Client PKI)
     */
    public UserCredentials(UserSuppliedCredentials userSuppliedCreds)
    {
        this.userSuppliedCredentials = userSuppliedCreds;
    }

    public String getUserId()
    {
        String userId = "unknown";
        if (userSuppliedCredentials.getCredentialMode()
                .equals(CredentialModeEnum.USERNAME_PASSWORD))
        {
            userId = userSuppliedCredentials.getUsername();
        } else if (userSuppliedCredentials.getCredentialMode().equals(CredentialModeEnum.CLIENT_PKI))
        {
            userId = userSuppliedCredentials.getClientCert().getSubjectDN().toString();
        }
        return (userId);
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("UserCredentials: ");
        builder.append(userSuppliedCredentials);
        return builder.toString();
    }
}
