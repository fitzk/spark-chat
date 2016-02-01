package schema; /**
 * Created by kaylafitzsimmons on 1/30/16.
 */


import java.util.Date;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonProperty;

@DynamoDBTable(tableName="UserMetaData")
public class UserMetaDataItem {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("username")
    private String username;
    @JsonProperty("dateCreated")
    private String dateCreated;


    @DynamoDBHashKey(attributeName="id")
    public Integer getId() { return id;}
    public void setId(Integer id) {this.id = id;}

    @DynamoDBAttribute(attributeName="username")
    public String getUsername() {return username; }
    public void setUsername(String username) { this.username = username; }

    @DynamoDBAttribute(attributeName="dateCreated")
    public String getDateCreated() { return dateCreated;}
    public void setDateCreated(String dateCreated) { this.dateCreated = dateCreated; }


}