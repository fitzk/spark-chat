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
public class ChannelItem {

    private AmazonDynamoDB client;
    private DynamoDBMapper mapper;
    private ChannelMetaDataItem channelMeta;
    private ChannelStateItem channelState;
    private DynamoDB dynamoDB;

    public ChannelMetaDataItem getChannelMetaById(String id) {
        return mapper.load(ChannelMetaDataItem.class, id);
    }

    public void setChannelMeta(ChannelMetaDataItem channelMeta) {
        this.channelMeta = channelMeta;
    }

    public ChannelStateItem getChannelState() {
        return channelState;
    }

    public void setChannelState(ChannelStateItem channelState) {
        this.channelState = channelState;
    }

    /**
     * @constructor
     */
    public ChannelItem(){

    }

    public void init(AmazonDynamoDB client) {
        this.client = client;
        mapper = new DynamoDBMapper(client);
        channelMeta= new ChannelMetaDataItem();
        channelState = new ChannelStateItem();
        dynamoDB = new DynamoDB(client);
    }

    /**
     * Returns a channel meta data object
     * @param id
     * @return ChannelMetaDataItem
     * @throws Exception
     */
    protected ChannelMetaDataItem getChannelMetaDataObject(Integer id) throws Exception {
        return mapper.load(ChannelMetaDataItem.class,id);
    }
    /**
     *
     * @param id
     * @return ChannelStateItem
     * @throws Exception
     */
    protected ChannelStateItem getChannelStateObject(Integer id) throws Exception { return mapper.load(ChannelStateItem.class,id);}

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
        mapper.save(data);

        // setting attributes for ChannelState table
        ChannelStateItem state = new ChannelStateItem();
        state.setId(data.getId());
        state.setName(name);
        state.setRooms(new ArrayList<>());
        mapper.save(state);
        return data.getId();
    }
    /**
     *
     * @param channel_id
     * @param room_id
     * @throws Exception
     */
    protected void addRoomToChannel(Integer channel_id, Integer room_id) throws Exception {
        try {
                channelState = getChannelStateObject(channel_id);
                List<Integer> rooms = channelState.getRooms();
                rooms.add(room_id);
                channelState.setRooms(rooms);
            }catch(Exception e){
           //  System.prntln.out(e);
        }
    }

    /**
     *
     * @param channelId
     * @param tableName
     * @throws Exception
     */
    protected void deleteChannel(Integer channelId, String tableName) throws Exception {
        channelMeta = getChannelMetaDataObject(channelId);
        mapper.generateDeleteTableRequest(ChannelMetaDataItem.class);
    }

    protected void getAllChannels(){
        ScanRequest scanRequest = new ScanRequest()
                .withTableName("ChannelMetaData");

        ScanResult result = client.scan(scanRequest);

        for (Map<String, AttributeValue> item : result.getItems()) {
            //item);
        }
        // edit this!!!
        // List<ChannelMetaDataItem> hi = new ArrayList<ChannelMetaDataItem>;
        //  return hi;
    }
}
