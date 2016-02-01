package access;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import schema.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by kaylafitzsimmons on 1/31/16.
 */
public class ChannelItem extends DynamoDAO {


    ChatRoomItem chatRoomAccessor;

    public ChannelMetaDataItem getChannelMetaById(String id) {
        return getMapper().load(ChannelMetaDataItem.class, id);
    }

    /**
     * @constructor
     */
    public ChannelItem(ChatRoomItem chatRoomAccessor){
        this.chatRoomAccessor = chatRoomAccessor;
    }

    /**
     * Returns a channel meta data object
     * @param id
     * @return ChannelMetaDataItem
     * @throws Exception
     */
    protected ChannelMetaDataItem getChannelMetaDataObject(Integer id) throws Exception {
        return getMapper().load(ChannelMetaDataItem.class,id);
    }
    /**
     *
     * @param id
     * @return ChannelStateItem
     * @throws Exception
     */
    protected ChannelStateItem getChannelStateObject(Integer id) throws Exception { return getMapper().load(ChannelStateItem.class,id);}

    public String create(String name){

        // setting attributes for ChannelMetaData table
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String now = df.format(new Date());
        ChannelMetaDataItem data = new ChannelMetaDataItem();
        data.setName(name);
        data.setDateCreated(now);
        data.setState("Active");
        getMapper().save(data);

        // setting attributes for ChannelState table
        ChannelStateItem state = new ChannelStateItem();
        state.setId(data.getId());
        state.setName(name);
        state.setRooms(new ArrayList<>());
        getMapper().save(state);
        return data.getId();
    }
    /**
     *
     * @param channel_id
     * @param roomName
     * @throws Exception
     */
    public String addRoomToChannel(String channel_id, String roomName) throws Exception {
        ChannelStateItem state = getChannelStateById(channel_id);
        String room_id = chatRoomAccessor.create(roomName);
        state.getRooms().add(room_id);
        save(state);
        return room_id;
    }

    public void removeRoomFromChannel(String channel_id, String room_id) throws Exception {
        chatRoomAccessor.delete(room_id);
        ChannelStateItem state = getChannelStateById(channel_id);
        state.getRooms().remove(room_id);
        save(state);
    }

    /**
     *
     * @param channelId
     * @param tableName
     * @throws Exception
     */
    protected void deleteChannel(Integer channelId, String tableName) throws Exception {
//        channelMeta = getChannelMetaDataObject(channelId);
//        getMapper().generateDeleteTableRequest(ChannelMetaDataItem.class);
    }

    protected void getAllChannels(){
//        ScanRequest scanRequest = new ScanRequest()
//                .withTableName("ChannelMetaData");
//
////        ScanResult result = client.scan(scanRequest);
//
//        for (Map<String, AttributeValue> item : result.getItems()) {
//            //item);
//        }
//        // edit this!!!
//        // List<ChannelMetaDataItem> hi = new ArrayList<ChannelMetaDataItem>;
//        //  return hi;
    }

    public ChannelStateItem getChannelStateById(String id) {
        return getMapper().load(ChannelStateItem.class, id);
    }
}
