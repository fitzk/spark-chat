import access.ChannelDAO;
import access.ChatRoomDAO;
import access.MessageDAO;
import access.UserDAO;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import org.testng.annotations.AfterClass;
import schema.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by kaylafitzsimmons on 2/1/16.
 */
public class MainHelper {

    private AmazonDynamoDB dynamodb;
    private ChatRoomDAO chatRoomAccessor;
    private ChannelDAO channelAccessor;
    private UserDAO userAccessor;
    private MessageDAO messageAccessor;
    MainHelper(){


    }
    /**
     * ADDS ALL TABLES TO DATABASE
     * @throws InterruptedException
     */


    /**
     * REMOVES ALL TABLES FROM DATABASE
     */


    public void setup() throws InterruptedException {


    }

}
