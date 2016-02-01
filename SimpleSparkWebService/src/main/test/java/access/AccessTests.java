package access;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.Table;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import schema.ChatRoomMetaDataItem;

/**
 * Created by phil on 1/31/2016.
 */
public class AccessTests {


    private AmazonDynamoDB dynamodb;

    @BeforeClass
    public void setup() throws InterruptedException {
        dynamodb = new AmazonDynamoDBClient();
        dynamodb.setEndpoint("http://192.168.0.200:8000/");
        _createTables();
    }

    @AfterClass
    public void teardown() {
        _deleteTables();
    }

    @Test
    public void test() {
        ChannelItem accessor = new ChannelItem();
        accessor.init(dynamodb);
        String id = accessor.create("testchannel");
        System.out.println("new id: "+id);
    }


    private void _createTables() throws InterruptedException {
        Table table = DynamoDBUtils.createTable(dynamodb, ChatRoomMetaDataItem.class);
        table.waitForActive();
    }

    private void _deleteTables() {
        DynamoDBUtils.deleteTable(dynamodb, ChatRoomMetaDataItem.class);
    }
}