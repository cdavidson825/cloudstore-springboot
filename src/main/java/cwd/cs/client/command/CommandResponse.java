package cwd.cs.client.command;

public class CommandResponse
{

    private final boolean responseStatus;
    private final String response;
    
    private String command = null;
    
    public CommandResponse(boolean status)
    {
        this(status, null);
    }
    
    public CommandResponse(boolean status, String response)
    {
       responseStatus = status;
       this.response = response;
    }

    public boolean getResponseStatus()
    {
        return responseStatus;
    }

    public String getResponse()
    {
        return response;
    }

    public String getCommand()
    {
        return command;
    }

    public void setCommand(String command)
    {
        this.command = command;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("CommandResponse [responseStatus=");
        builder.append(responseStatus);
        builder.append("\n");
        if (command != null)
        {
            builder.append("command = ");
            builder.append(command);
        }
        if (response != null)
        {
            builder.append("\n");
            builder.append("response = ");
            builder.append("\n");
            builder.append(response);
        }
        builder.append("]");
        return builder.toString();
    }
}
