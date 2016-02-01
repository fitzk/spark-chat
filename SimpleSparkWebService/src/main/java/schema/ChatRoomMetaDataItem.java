package schema;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.security.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by kaylafitzsimmons on 1/30/16.
 */


@DynamoDBTable(tableName="ChatRoomMetaData")
public class ChatRoomMetaDataItem {

    private Integer id;
    private String name;
    private String dateCreated;
    //   public enum Type { Private, Public };
    private String type;
//    public enum State { Active, Inactive };
    private String state;
    private Set<String> tags = new HashSet<>();

    @DynamoDBHashKey(attributeName="id")
    public Integer getId() { return id;}
    public void setId(Integer id) {this.id = id;}

    @DynamoDBAttribute(attributeName="name")
    public String getName() {return name; }
    public void setName(String name) { this.name = name; }

    @DynamoDBAttribute(attributeName="dateCreated")
    public String getDateCreated() { return dateCreated;}
    public void setDateCreated(String dateCreated) { this.dateCreated = dateCreated; }

    @DynamoDBAttribute(attributeName="state")
    public String getState() { return state;}
    public void setState(String state ) { this.state = state; }

    @DynamoDBAttribute(attributeName="type")
    public String getType() { return type;}
    public void setType( String type ) { this.type = type; }

    @DynamoDBAttribute(attributeName="tags")
    public Set<String> getTags() { return tags;}
    public void setTags( Set<String> tags ) { this.tags = tags; }

}