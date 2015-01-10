package cwd.cs.server.model.authentication;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * This class is a mocked authentication service and will return true for all user authentication
 * requests. This class is for development/testing mode and provides not authentication services for
 * a production server
 */
@Component
@Qualifier("Anon")
public class AnonAuthenticationService implements AuthenticationService
{

    private static Log log = LogFactory.getLog(AnonAuthenticationService.class);

    @Override
    public boolean authenticateUser(UserCredentials userCredentials)
    {
        boolean authenticated = false;
        if (userCredentials.getUserId().toUpperCase().startsWith("ANON"))
        {
            authenticated = true;
        }
        log.debug("Anon authenticator Received login request for " + userCredentials
                + " --> authenticated = " + authenticated);
        return authenticated;
    }

}
