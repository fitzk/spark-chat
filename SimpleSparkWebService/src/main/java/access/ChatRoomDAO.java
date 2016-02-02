package access;

import schema.ChatRoomContentItem;
import schema.ChatRoomMetadataItem;
import schema.ChatRoomStateItem;
import schema.MessageItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by kaylafitzsimmons on 1/31/16.
 */
public class ChatRoomDAO extends DynamoDAO {

    /**
     * @constructor
     */
    public ChatRoomDAO(){

    }

    public String create(String name) {
        // setting attributes for ChannelMetaData table
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String now = df.format(new Date());
        ChatRoomMetadataItem data = new ChatRoomMetadataItem();
        data.setName(name);
        data.setId(name);
        data.setDateCreated(now);
        data.setState("Active");
        getMapper().save(data);

        // setting attributes for ChannelState table
        ChatRoomStateItem state = new ChatRoomStateItem();
        state.setId(data.getId());
        state.setName(name);
        getMapper().save(state);

        ChatRoomContentItem content = new ChatRoomContentItem();
        content.setId(data.getId());
        content.setName(name);
        List<MessageItem> messages = new ArrayList<>();
        getMapper().save(content);

        return data.getId();
    }
    /**
     *
     * @param id
     * @return ChatRoomMetadataItem
     * @throws Exception
     */
    public ChatRoomMetadataItem getChatRoomMetaDataById(String id) throws Exception {
       return getMapper().load(ChatRoomMetadataItem.class,id);
    }

    /**
     *
     * @param id
     * @return ChatRoomMetadataItem
     * @throws Exception
     */
    public ChatRoomStateItem getChatRoomStateById(String id) throws Exception {
        return getMapper().load(ChatRoomStateItem.class,id);
    }

    /**
     *
     * @param id
     * @return ChatRoomMetadataItem
     * @throws Exception
     */
    public ChatRoomContentItem getChatRoomContentById(String id) throws Exception {
        return getMapper().load(ChatRoomContentItem.class,id);
    }

    @Override
    public void delete(String id) throws Exception {
        try {
                ChatRoomMetadataItem meta = getChatRoomMetaDataById(id);
                ChatRoomStateItem state = getChatRoomStateById(id);
                ChatRoomContentItem content = getChatRoomContentById(id);
                getMapper().delete(meta);
                getMapper().delete(state);
                getMapper().delete(content);
        }catch(Exception e){
            System.err.println(e);
        }
    }

    public void addMessage(String chatroomid, String userid, String text) throws Exception{
        MessageDAO messageAccessor = new MessageDAO();
        UserDAO userAccessor = new UserDAO();
        ChatRoomContentItem content = getChatRoomContentById(chatroomid);

        String username = userAccessor.read(userid).getUsername();
        String messageid = messageAccessor.create(username, text);

        List<String> users = content.getUsers();
        List<String> messages = content.getMessages();

        users.add(userid);
        messages.add(messageid);
    }

}
