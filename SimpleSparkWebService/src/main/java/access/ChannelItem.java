package access;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.sun.jmx.snmp.Timestamp;
import schema.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by kaylafitzsimmons on 1/31/16.
 */
public class ChannelItem {

    private AmazonDynamoDBClient client;
    private DynamoDBMapper mapper;
    private ChannelMetaDataItem channelMeta;
    private ChannelStateItem channelState;
    private DynamoDB dynamoDB;

    public ChannelMetaDataItem getChannelMeta() {
        return channelMeta;
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


        client = new AmazonDynamoDBClient(new ProfileCredentialsProvider());
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
    /**
     *
     * @param ChannelName
     * @param tags
     */
    protected String addChannel(String ChannelName,Set<String> tags){

            // setting attributes for ChannelMetaData table
            channelMeta.setName(ChannelName);
            java.util.Date date= new java.util.Date();
            String now = new Timestamp(date.getTime()).toString();
            channelMeta.setDateCreated(now);
           // ChannelMetaDataItem.State state = ChannelMetaDataItem.State.Active;
          //  channelMeta.setState(state);
            channelMeta.setTags(tags);

            // setting attributes for ChannelState table
            channelState.setName(ChannelName);
            String metaId = channelMeta.getId();
            channelState.setId(metaId);

            mapper.save(channelMeta);
            mapper.save(channelState);
            return channelMeta.getId();
    }
    public String addChannel(){

        // setting attributes for ChannelMetaData table

        java.util.Date date= new java.util.Date();
        String now = new Timestamp(date.getTime()).toString();
        channelMeta.setDateCreated(now);
        String state = "Active";
        channelMeta.setState(state);


        // setting attributes for ChannelState table
        channelState.setName(channelMeta.getName());
        String metaId = channelMeta.getId();
        channelState.setId(metaId);

        mapper.save(channelMeta);
        mapper.save(channelState);
        return channelMeta.getId();
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
