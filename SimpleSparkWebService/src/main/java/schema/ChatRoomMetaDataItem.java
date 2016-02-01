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
    private Timestamp dateCreated;

    public enum Type { Private, Public };
    private Type type;
    public enum State { Active, Inactive };
    private State state;
    private Set<String> tags = new HashSet<>();

    @DynamoDBHashKey(attributeName="id")
    public Integer getId() { return id;}
    public void setId(Integer id) {this.id = id;}

    @DynamoDBAttribute(attributeName="name")
    public String getName() {return name; }
    public void setName(String name) { this.name = name; }

    @DynamoDBAttribute(attributeName="DateCreated")
    public Timestamp getDateCreated() { return dateCreated;}
    public void setDateCreated(Timestamp dateCreated) { this.dateCreated = dateCreated; }

    @DynamoDBAttribute(attributeName="State")
    public State getState() { return state;}
    public void setState(State state ) { this.state = state; }

    @DynamoDBAttribute(attributeName="Type")
    public Type getType() { return type;}
    public void setType( Type type ) { this.type = type; }

    @DynamoDBAttribute(attributeName="Tags")
    public Set<String> getTags() { return tags;}
    public void setTags( Set<String> tags ) { this.tags = tags; }

}