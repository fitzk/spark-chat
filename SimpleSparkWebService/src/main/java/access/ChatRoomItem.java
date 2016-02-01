package access;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.*;
import com.sun.jmx.snmp.Timestamp;
import schema.*;

import java.util.Map;
import java.util.Set;

/**
 * Created by kaylafitzsimmons on 1/31/16.
 */
public class ChatRoomItem {

    private AmazonDynamoDBClient client;
    private DynamoDBMapper mapper;
    private ChatRoomMetaDataItem chatRoomMeta;
    private ChatRoomStateItem chatRoomState;
    private DynamoDB dynamoDB;
    private ChatRoomContentItem chatRoomContent;

    /**
     * @constructor
     */
    protected ChatRoomItem(){


        client = new AmazonDynamoDBClient(new ProfileCredentialsProvider());
        mapper = new DynamoDBMapper(client);
        chatRoomMeta = new ChatRoomMetaDataItem();
        chatRoomState = new ChatRoomStateItem();
        chatRoomContent = new ChatRoomContentItem();
        dynamoDB = new DynamoDB(client);

    }

    /**
     *
     * @param id
     * @return ChatRoomMetaDataItem
     * @throws Exception
     */
    protected ChatRoomMetaDataItem getChatRoomMetaDataObject(Integer id) throws Exception {

       return mapper.load(ChatRoomMetaDataItem.class,id);

    }

    /**
     *
     * @param id
     * @return ChatRoomStateItem
     * @throws Exception
     */
    protected ChatRoomStateItem getChatRoomStateObject(Integer id) throws Exception {

        return mapper.load(ChatRoomStateItem.class,id);

    }
    protected ChatRoomContentItem getChatRoomContentObject(Integer id) throws Exception {

       return mapper.load(ChatRoomContentItem.class,id);

    }
    /**
     *
     * @param chatRoomName
     * @param tags
     */
    protected Integer setChatRoom(String chatRoomName,Set<String> tags){
        // setting attributes for ChatRoomMetaData table
        chatRoomMeta.setName(chatRoomName);
        chatRoomMeta.setTags(tags);

        // setting attributes for ChatRoomState table
        chatRoomState.setName(chatRoomName);
        Integer metaId = chatRoomMeta.getId();
        chatRoomState.setId(metaId);
        java.util.Date date= new java.util.Date();
        String now = new Timestamp(date.getTime()).toString().toString();
        chatRoomState.setDateCreated(now);
        //ChatRoomStateItem.State state = ChatRoomStateItem.State.ACTIVE;
        String state = "Active";
        chatRoomState.setState(state);

        // setting attributes for ChatRoomContent table
        chatRoomContent.setName(chatRoomName);
        chatRoomContent.setId(metaId);
        mapper.save(chatRoomMeta);
        mapper.save(chatRoomState);
        mapper.save(chatRoomState);
        return chatRoomMeta.getId();
    }

    /**
     *
     * @param chatRoomId
     * @param userId
     * @param online
     * @throws Exception
     */
    protected void addUserToChatRoom(Integer chatRoomId, Integer userId, Boolean online) throws Exception {

        chatRoomState = getChatRoomStateObject(chatRoomId);
        Map<Integer,Boolean> users = chatRoomState.getUsers();
        users.put(userId,online);
        chatRoomState.setUsers(users);
    }

    /**
     *
     * @param chatRoomId
     * @param userId
     * @param online
     * @throws Exception
     */
    protected void removeUserFromChatRoom(Integer chatRoomId, Integer userId, Boolean online) throws Exception {

        chatRoomState = getChatRoomStateObject(chatRoomId);
        Map<Integer,Boolean> users = chatRoomState.getUsers();
        users.remove(userId,online);
        chatRoomState.setUsers(users);
    }

    /**
     *
     * @param chatRoomId
     * @param tableName
     * @throws Exception
     */
    protected void deleteChatRoom(Integer chatRoomId, String tableName) throws Exception {
     //   Table ChatRoomMeta =
        chatRoomMeta = getChatRoomMetaDataObject(chatRoomId);
        Integer metaId = chatRoomMeta.getId();

      //  .execute(mapper.generateDeleteTableRequest(ChatRoomMetaDataItem.class));
    }
}
