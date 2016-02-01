package access;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.*;
import com.sun.jmx.snmp.Timestamp;
import schema.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * Created by kaylafitzsimmons on 1/31/16.
 */
public class ChatRoomItem extends DynamoDAO {

    /**
     * @constructor
     */
    public ChatRoomItem(){

    }

    public String create(String name) {
        // setting attributes for ChannelMetaData table
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String now = df.format(new Date());
        ChatRoomMetaDataItem data = new ChatRoomMetaDataItem();
        data.setName(name);
        data.setDateCreated(now);
        data.setState("Active");
        getMapper().save(data);

        // setting attributes for ChannelState table
        ChatRoomStateItem state = new ChatRoomStateItem();
        state.setId(data.getId());
        state.setName(name);
        getMapper().save(state);
        return data.getId();
    }
    /**
     *
     * @param id
     * @return ChatRoomMetaDataItem
     * @throws Exception
     */
    public ChatRoomMetaDataItem getChatRoomMetaDataById(String id) throws Exception {
       return getMapper().load(ChatRoomMetaDataItem.class,id);
    }

    /**
     *
     * @param id
     * @return ChatRoomMetaDataItem
     * @throws Exception
     */
    public ChatRoomStateItem getChatRoomStateById(String id) throws Exception {
        return getMapper().load(ChatRoomStateItem.class,id);
    }

    @Override
    public void delete(String id) throws Exception {
        ChatRoomMetaDataItem meta = getChatRoomMetaDataById(id);
        ChatRoomStateItem state = getChatRoomStateById(id);
        getMapper().delete(meta);
        getMapper().delete(state);
    }

}
