package cwd.cs.server.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import cwd.cs.common.UserSuppliedCredentials;
import cwd.cs.server.model.authentication.AuthenticationService;
import cwd.cs.server.model.authentication.UserCredentials;

public abstract class ServiceManager
{
    private final UserCredentials userCredentials;
    
    @Autowired
    @Qualifier("Anon")
    private AuthenticationService authenticationService;
    
    public ServiceManager(UserSuppliedCredentials userSuppliedCreds)
    {   
        
        if (userSuppliedCreds == null)
        {
            throw new IllegalArgumentException(
                    "No UserSuppliedCredentials supplied");
        }
        this.userCredentials = new UserCredentials(userSuppliedCreds);
    }
    
    
    public UserCredentials getUserCredentials()
    {
        return this.userCredentials;
    }
    
    public AuthenticationService getAuthenticationService()
    {
        return this.authenticationService;
    }
    
    
    /*
     * Method to determine if the user is an authenticated user. This method
     * delegates the request to the AuthenticationService to determine if the
     * user is authenticated. This method currently throws a RuntimeException if
     * the user is not authenticated.
     */
    public boolean isAuthenticatedUser()
    {
        boolean isAuthenticatedUser = false;

        System.out.println("======Authservice = "+ getAuthenticationService());
        isAuthenticatedUser = authenticationService.authenticateUser(userCredentials);
        if (!isAuthenticatedUser)
        {
            throw new RuntimeException(
                    "User not Authorized to use StorageManager");
        }
        return (isAuthenticatedUser);
    }
}
