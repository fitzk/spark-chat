package access;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import schema.ChannelMetaDataItem;
import schema.ChannelStateItem;
import schema.ChatRoomMetaDataItem;
import schema.ChatRoomStateItem;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 * Created by phil on 1/31/2016.
 */
public class AccessTests {


    private AmazonDynamoDB dynamodb;
    private ChatRoomItem chatRoomAccessor;
    private ChannelItem accessor;

    /**
     * This is run before all tests, it creates necessary tables and sets up db connection
     * @throws InterruptedException
     */
    @BeforeClass
    public void setup() throws InterruptedException {
        dynamodb = new AmazonDynamoDBClient();
        dynamodb.setEndpoint("http://192.168.0.200:8000/");
        _createTables();
        ListTablesResult tables = dynamodb.listTables();
        List<String> names = tables.getTableNames();
        assertThat(names.size()).isNotEqualTo(0);
        System.out.println("Tables:");
        for(String name:names) {
            System.out.println(name);
        }
        chatRoomAccessor = new ChatRoomItem();
        chatRoomAccessor.init(dynamodb);
        accessor = new ChannelItem(chatRoomAccessor);
        accessor.init(dynamodb);
    }

    /**
     * This is run after all tests. it destroys the tables
     */
    @AfterClass
    public void teardown() {
        _deleteTables();
    }

    @Test
    public void createChannelTest() {
        String id = accessor.create("testchannel");
        ChannelMetaDataItem meta = accessor.getChannelMetaById(id);
        assertThat(id).isNotEmpty();
        assertThat(meta.getId()).isEqualTo(id);
        System.out.println("new id: "+id);
    }

    @Test
    public void createChatRoomTest() throws Exception {
        ChatRoomItem accessor = new ChatRoomItem();
        accessor.init(dynamodb);
        String id = accessor.create("testroom");
        ChatRoomMetaDataItem meta = accessor.getChatRoomMetaDataById(id);
        assertThat(id).isNotEmpty();
        assertThat(meta.getId()).isEqualTo(id);
        System.out.println("new id: "+id);
    }

    @Test
    public void removeRoomFromChannel() throws Exception {

        // Creating the Channel with 1 room
        String id = accessor.create("One Room Channel");
        String roomId = accessor.addRoomToChannel(id, "r00m");
        ChatRoomMetaDataItem meta = chatRoomAccessor.getChatRoomMetaDataById(roomId);
        assertThat(meta).isNotNull();
        assertThat(meta.getName()).isEqualTo("r00m");

        // Assert channel has 1 room
        ChannelStateItem state = accessor.getChannelStateById(id);
        assertThat(state.getRooms().size()).isEqualTo(1);

        // Remove room from channel
        accessor.removeRoomFromChannel(id, roomId);
        state = accessor.getChannelStateById(id);

        // Assert remove worked
        assertThat(state.getRooms()).isEmpty();
        meta = chatRoomAccessor.getChatRoomMetaDataById(roomId);
        assertThat(meta).isNull();
    }

    @Test
    public void addRoomToChannel() throws Exception {
        // Creating the Channel with 1 room
        String id = accessor.create("Two Room Channel");
        accessor.addRoomToChannel(id, "r00m");
        accessor.addRoomToChannel(id, "tw00m");

        ChannelStateItem state = accessor.getChannelStateById(id);
        assertThat(state.getRooms().size()).isEqualTo(2);
    }


    private void _createTables() throws InterruptedException {
        DynamoDBUtils.createTable(dynamodb, ChannelMetaDataItem.class);
        DynamoDBUtils.createTable(dynamodb, ChannelStateItem.class);

        DynamoDBUtils.createTable(dynamodb, ChatRoomMetaDataItem.class);
        Table table = DynamoDBUtils.createTable(dynamodb, ChatRoomStateItem.class);

        table.waitForActive();
    }

    private void _deleteTables() {
        DynamoDBUtils.deleteTable(dynamodb, ChannelMetaDataItem.class);
        DynamoDBUtils.deleteTable(dynamodb, ChannelStateItem.class);

        DynamoDBUtils.deleteTable(dynamodb, ChatRoomMetaDataItem.class);
        DynamoDBUtils.deleteTable(dynamodb, ChatRoomStateItem.class);

    }
}