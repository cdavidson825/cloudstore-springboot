package cwd.cs.server.cloud;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class CloudConfiguration
{
    /**
     * Used to format the Date used as a Container name.
     */
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    
    /**
     * Method used to get the default name for the container/bucket used to store
     * data in the cloud.
     * @return 
     */
    public String getDefaultContainerString()
    {
        String dateString = sdf.format(new Date());
        
        return dateString;
    }
}
