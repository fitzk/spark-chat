package schema; /**
 * Created by kaylafitzsimmons on 1/30/16.
 */


import java.util.Date;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonProperty;

@DynamoDBTable(tableName="User")
public class UserItem {
    private String id;
    @JsonProperty("username")
    private String username;
    private String dateCreated;
    @JsonProperty("online")
    private Boolean online;


    @DynamoDBHashKey
    public String getId() { return id;}
    public void setId(String id) { this.id = id;}

    @DynamoDBAttribute(attributeName="username")
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    @DynamoDBAttribute(attributeName="dateCreated")
    public String getDateCreated() { return dateCreated;}
    public void setDateCreated(String dateCreated) { this.dateCreated = dateCreated; }

    @DynamoDBAttribute(attributeName="online")
    public Boolean getOnline() { return online;}
    public void setOnline(Boolean online) { this.online = online; }


}