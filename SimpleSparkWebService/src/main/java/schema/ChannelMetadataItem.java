package schema;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by kaylafitzsimmons on 1/30/16.
 */


@DynamoDBTable(tableName="ChannelMetaData")
public class ChannelMetadataItem {

  //  public enum State {Active, Inactive};

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("dateCreated")
    private String dateCreated;
    @JsonProperty("state")
    private String state;

    @DynamoDBHashKey
    public String getId() { return id;}
    public void setId(String id) {this.id = id;}

    @DynamoDBAttribute(attributeName="name")
    public String getName() {return name; }
    public void setName(String name) { this.name = name; }

    @DynamoDBAttribute(attributeName="dateCreated")
    public String getDateCreated() { return dateCreated;}
    public void setDateCreated(String dateCreated) { this.dateCreated = dateCreated; }

    @DynamoDBAttribute(attributeName="state")
    public String getState() { return state;}
    public void setState(String state ) { this.state = state; }

}