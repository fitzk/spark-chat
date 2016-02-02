package schema;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.*;
import java.util.List;


/**
 * Created by kaylafitzsimmons on 1/30/16.
 */
@DynamoDBTable(tableName="ChatRoomContent")
public class ChatRoomContentItem{

    private String id;
    private String name;
    private List<String> users = new ArrayList<>();
    private List<String> messages = new ArrayList<>();


    @DynamoDBHashKey
    public String getId() { return id;}
    public void setId(String id) { this.id = id; }

    @DynamoDBAttribute(attributeName="name")
    public String getName() {return name; }
    public void setName(String name) { this.name = name; }


    @DynamoDBAttribute(attributeName="users")
    public List<String> getUsers() { return users;}
    public void setUsers( List<String>users ) { this.users = users; }

    @DynamoDBAttribute(attributeName="messages")
    public List<String> getMessages() {return messages; }
    public void setMessages(List<String> messages) { this.messages = messages; }
}