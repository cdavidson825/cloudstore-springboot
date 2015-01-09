package cwd.cs.server.model.authentication;



/**
 * The Authentication Interface that all authentication service providers must
 * implement.
 */
public interface AuthenticationService
{

    /**
     * Method to determine if a user should be authenticated based on the provided
     * UserCredentials object.
     * 
     * @param userCredentials
     *            The userCredentials object associated with the user
     * @return true if the userCredentials are authenticated, false otherwise.
     */
    public boolean authenticateUser(UserCredentials userCredentials);
}
