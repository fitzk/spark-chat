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

    private Integer id;
    private Integer metaId;
    private String name;
    private List<MessageItem> messages = new ArrayList<>();


    @DynamoDBHashKey(attributeName="Id")
    public Integer getId() { return id;}
    public void setId(Integer id) { this.id = id; }

    @DynamoDBAttribute
    public Integer getMetaId() { return metaId;}
    public void setMetaId(Integer metaId) {this.metaId = metaId;}

    @DynamoDBAttribute(attributeName="Name")
    public String getName() {return name; }
    public void setName(String name) { this.name = name; }

    @DynamoDBAttribute(attributeName="Messages")
    public List<MessageItem> getMessages() {return messages; }
    public void setName(List<MessageItem> messages) { this.messages = messages; }
}