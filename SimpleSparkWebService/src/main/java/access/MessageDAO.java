package access;

import schema.MessageItem;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kaylafitzsimmons on 2/1/16.
 */
public class MessageDAO extends DynamoDAO {

    public MessageDAO() { }

    /**
     * create
     * @param username
     * @param text
     * @return
     */
    public String create(String username, String text ){

        // setting attributes for ChannelMetaData table
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String now = df.format(new Date());

        MessageItem data = new MessageItem();
        data.setUsername(username);
        data.setDate(now);
        data.getText(text);
        getMapper().save(data);

        return data.getId();
    }

    /**
     * read
     * @param id
     * @return
     */
    public MessageItem read(String id){ return getMapper().load( MessageItem.class, id); }




}
