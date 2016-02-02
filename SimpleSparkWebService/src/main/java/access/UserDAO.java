package access;

import schema.ChannelStateItem;
import schema.UserItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by kaylafitzsimmons on 2/1/16.
 */
public class UserDAO extends DynamoDAO {


    public UserDAO() {
    }

    public String create(String username){

        // setting attributes for ChannelMetaData table
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String now = df.format(new Date());

        UserItem data = new UserItem();
        data.setId(username);
        data.setUsername(username);
        data.setDateCreated(now);
        data.setOnline(true);
        getMapper().save(data);
        System.out.println("In create function: "+data.getId());
        return data.getId();
    }

    public UserItem read(String id){ return getMapper().load( UserItem.class, id); }
}
