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


    public ChannelMetaDataItem getChannelMetaById(String id) {
        return getMapper().load(ChannelMetaDataItem.class, id);
    }

    /**
     * @constructor
     */
    public ChannelItem(){

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
        Set<String> tags = new HashSet<>();
        tags.add("stupid");
        data.setTags(tags);
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
     * @param room_id
     * @throws Exception
     */
    protected void addRoomToChannel(Integer channel_id, Integer room_id) throws Exception {
//        try {
//                channelState = getChannelStateObject(channel_id);
//                List<Integer> rooms = channelState.getRooms();
//                rooms.add(room_id);
//                channelState.setRooms(rooms);
//            }catch(Exception e){
//           //  System.prntln.out(e);
//        }
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
}
