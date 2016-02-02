package access;

import schema.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by kaylafitzsimmons on 1/31/16.
 */
public class ChannelDAO extends DynamoDAO {


    ChatRoomDAO chatRoomAccessor;

    public ChannelMetadataItem getChannelMetaById(String id) {
        return getMapper().load(ChannelMetadataItem.class, id);
    }
    public ChannelStateItem getChannelStateById(String id) {
        return getMapper().load(ChannelStateItem.class, id);
    }
    /**
     * @constructor
     */
    public ChannelDAO(ChatRoomDAO chatRoomAccessor){
        this.chatRoomAccessor = chatRoomAccessor;
    }

    /**
     * create
     * @description Creates all necessary channel items for new a new channel
     * @param channel_name
     * @return id
     */
    public String create(String channel_name){

        // setting attributes for ChannelMetaData table
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String now = df.format(new Date());
        ChannelMetadataItem data = new ChannelMetadataItem();
        data.setId(channel_name);
        data.setName(channel_name);
        data.setDateCreated(now);
        data.setState("Public");
        getMapper().save(data);

        // setting attributes for ChannelState table
        ChannelStateItem state = new ChannelStateItem();
        state.setId(data.getId());
        state.setName(channel_name);
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

    /**
     * removeRoomFromChannel
     * @description Uses the chatRoomAccessor to delete a chat room and it's association in the
     *              Channel State table, then saves the changes
     * @param channel_id
     * @param room_id
     * @throws Exception
     */
    public void removeRoomFromChannel(String channel_id, String room_id) throws Exception {
        try {
            chatRoomAccessor.delete(room_id);
            ChannelStateItem state = getChannelStateById(channel_id);
            state.getRooms().remove(room_id);
            save(state);
        }catch(Exception e){
            System.err.println(e);
        }
    }

    /**
     *
     * @param channel_id
     * @throws Exception
     */
    @Override
    public void delete(String channel_id) throws Exception {
        try {
            ChannelMetadataItem meta = getChannelMetaById(channel_id);
            ChannelStateItem state = getChannelStateById(channel_id);

            // removes all rooms associated with channel
            List<String> rooms = state.getRooms();
            for (String room_id : rooms) {
                removeRoomFromChannel(channel_id, room_id);
            }
            getMapper().delete(meta);
            getMapper().delete(state);
        }catch(Exception e){
            System.out.println(e);
        }
    }


}
