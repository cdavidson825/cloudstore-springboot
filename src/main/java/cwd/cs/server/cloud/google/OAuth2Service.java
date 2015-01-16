package cwd.cs.server.cloud.google;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.storage.Storage;
import com.google.api.services.storage.StorageScopes;

/**
 * Class to handle the OAuth2 details for authenticating with the Google services.
 */
public class OAuth2Service
{
    private static final Log log = LogFactory.getLog(OAuth2Service.class);
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private final GCSConfiguration gcsConfig;

    private HttpTransport httpTransport;
    private FileDataStoreFactory dataStoreFactory;

    public OAuth2Service(GCSConfiguration gcsConfig)
    {
        this.gcsConfig = gcsConfig;
        if (!validateConfiguration())
        {
            String msg = "GCSConfiguration is not valid.  Cannot continue" + gcsConfig;
            log.error(msg);
            throw new RuntimeException(msg);
        }
    }

    private boolean validateConfiguration()
    {
        boolean isValid = false;
        String bucketName = gcsConfig.getBucketName();
        boolean validTmpDir = new File(gcsConfig.getAuthTokenTempDirectory()).isDirectory();

        boolean validJsonSecretsFile = false;
        try
        {
            validJsonSecretsFile =
                    new InputStreamReader(this.getClass().getResourceAsStream(
                            gcsConfig.getJsonOAuth2File())).ready();
        }
        catch (Exception ioe)
        {
            log.error("caught exception while validating secrets file: " + ioe);
        }

        if (!bucketName.isEmpty() && validJsonSecretsFile && validTmpDir)
        {
            isValid = true;
        }
        else
        {
            log.error(String
                    .format("validation failed due to: bucketValid/validJsonSecretsFile/validTmpDir: %b/%b/%b",
                            !bucketName.isEmpty(), validJsonSecretsFile, validTmpDir));
        }
        return (isValid);
    }

    protected Storage authenticate() throws GeneralSecurityException, IOException
    {
        httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        dataStoreFactory =
                new FileDataStoreFactory(new File(gcsConfig.getAuthTokenTempDirectory()));
        Credential credential = authorize();
        Storage storage =
                new Storage.Builder(httpTransport, JSON_FACTORY, credential).setApplicationName(
                        gcsConfig.getBucketName()).build();
        return storage;
    }

    /**
     * Authorizes the installed application to access user's protected data.
     * 
     * @throws IOException
     */
    private Credential authorize() throws IOException
    {
        // load client secrets
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(this.getClass()
                        .getResourceAsStream(gcsConfig.getJsonOAuth2File())));
        // set up authorization code flow
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, clientSecrets,
                        Collections.singleton(StorageScopes.DEVSTORAGE_FULL_CONTROL))
                        .setDataStoreFactory(dataStoreFactory).build();
        // authorize
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

}
