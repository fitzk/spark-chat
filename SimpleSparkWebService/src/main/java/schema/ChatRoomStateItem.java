package schema;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;


import java.sql.Time;
import java.util.*;

/**
 * Created by kaylafitzsimmons on 1/30/16.
 */


@DynamoDBTable(tableName="ChatRoomState")
public class ChatRoomStateItem {
//
//    public enum State {
//        ACTIVE("ACTIVE"),
//        INACTIVE("INACTIVE");
//
//        private State(String stringValue) { this.stringValue = stringValue; }
//        private String stringValue;
//        @Override public String toString() { return this.stringValue; }
//    }

    private String id;
    private String name;
    private String dateCreated;

    private String state;
    private Map<String,String> users = new HashMap<>();

    @DynamoDBHashKey(attributeName="Id")
    public String getId() { return id;}
    public void setId(String id) {this.id = id;}

    @DynamoDBAttribute(attributeName="Name")
    public String getName() {return name; }
    public void setName(String name) { this.name = name; }

    @DynamoDBAttribute(attributeName="DateCreated")
    public String getDateCreated() { return dateCreated;}
    public void setDateCreated(String dateCreated) { this.dateCreated = dateCreated; }

    @DynamoDBAttribute(attributeName="State")
    public String getState() { return state;}
    public void setState(String state ) { this.state = state; }

    @DynamoDBAttribute(attributeName="Users")
    public Map<String,String> getUsers() { return users;}
    public void setUsers(Map<String,String> users ) { this.users = users; }

}
