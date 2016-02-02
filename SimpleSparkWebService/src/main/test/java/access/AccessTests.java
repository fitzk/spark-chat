package access;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import org.testng.annotations.*;
import schema.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 * Created by kayla on 1/31/2016.
 */
public class AccessTests {


    private AmazonDynamoDB dynamodb;
    private ChatRoomDAO chatRoomAccessor;
    private ChannelDAO channelAccessor;
    private UserDAO userAccessor;
    private MessageDAO messageAccessor;

    /**
     * This is run before all tests, it creates necessary tables and sets up db connection
     * @throws InterruptedException
     */
    @BeforeClass
    public void setup() throws InterruptedException {
        dynamodb = new AmazonDynamoDBClient();
        dynamodb.setEndpoint("http://localhost:8000/");
        _createTables();
        ListTablesResult tables = dynamodb.listTables();
        List<String> names = tables.getTableNames();
        assertThat(names.size()).isNotEqualTo(0);
        System.out.println("Tables:");
        for(String name:names) {
            System.out.println(name);
        }
        // chat room
        chatRoomAccessor = new ChatRoomDAO();
        chatRoomAccessor.init(dynamodb);

        // channel
        channelAccessor = new ChannelDAO(chatRoomAccessor);
        channelAccessor.init(dynamodb);

        // user
        userAccessor = new UserDAO();
        userAccessor.init(dynamodb);

        // message
        messageAccessor = new MessageDAO();
        messageAccessor.init(dynamodb);

    }

    /**
     * This is run after all tests. It destroys the tables in the
     * database.
     */
    @AfterClass
    public void teardown() {
        _deleteTables();
    }

    /**
     *  CREATE Channel
     */
    @Test
    public void createChannelTest() {
        String id = channelAccessor.create("testchannel");
        ChannelMetadataItem meta = channelAccessor.getChannelMetaById(id);
        assertThat(id).isNotEmpty();
        assertThat(meta.getId()).isEqualTo(id);
        System.out.println("new channel id: " + id);
    }

    /**
     * CREATE User
     */
    @Test
    public void createUserTest() {
        String id = userAccessor.create("testuser");
        UserItem user = new UserItem();
        user.setId(id);
        assertThat(id).isNotEmpty();
        assertThat(user.getId()).isEqualTo(id);
        System.out.println("new user id: " + id);
    }

    /**
     * CREATE Chat Room
     * @throws Exception
     */
    @Test
    public void createChatRoomTest() throws Exception {
        ChatRoomDAO accessor = new ChatRoomDAO();
        accessor.init(dynamodb);
        String id = accessor.create("testroom");
        ChatRoomMetadataItem meta = accessor.getChatRoomMetaDataById(id);
        assertThat(id).isNotEmpty();
        assertThat(meta.getId()).isEqualTo(id);
        System.out.println("new chat room id: " + id);
    }
    /**
     * CREATE Message
     */
    @Test
    public void createMessageTest(){
        String username = "testuser";
        String text = "this is a test";
        String id = messageAccessor.create(username,text);
        MessageItem message = messageAccessor.read(id);
        assertThat(id).isNotEmpty();
        assertThat(message.getId()).isEqualTo(id);
        System.out.println("new message id: " + id);
    }

    /**
     * CREATE Chat Room
     * UPDATE Channel
     * @throws Exception
     */
    @Test
    public void addRoomToChannel() throws Exception {
        // Creating the Channel with 1 room
        String id = channelAccessor.create("Two Room Channel");
        channelAccessor.addRoomToChannel(id, "r00m");
        channelAccessor.addRoomToChannel(id, "tw00m");

        ChannelStateItem state = channelAccessor.getChannelStateById(id);
        assertThat(state.getRooms().size()).isEqualTo(2);
    }
    /**
     * DELETE Channel
     * @throws Exception
     *
    @Test
    public void deleteChannel() throws Exception {
        // Creating the Channel with 1 room
        String id = channelAccessor.create("New Two Room Channel");
        String id_one = channelAccessor.addRoomToChannel(id, "new_r00m");
        String id_two = channelAccessor.addRoomToChannel(id, "new_tw00m");

        channelAccessor.delete(id);
        ChannelStateItem state = channelAccessor.getChannelStateById(id);
        ChatRoomMetadataItem room_one = chatRoomAccessor.getChatRoomMetaDataById(id_one);
        ChatRoomMetadataItem room_two = chatRoomAccessor.getChatRoomMetaDataById(id_two);

        assertThat(state).isNull();
        assertThat(room_one).isNull();
        assertThat(room_two).isNull();


    }*/
    /**
     * DELETE Chat Room
     * @throws Exception
     */
    @Test
    public void removeRoomFromChannel() throws Exception {

        // Creating the Channel with 1 room
        String id = channelAccessor.create("One Room Channel");
        String roomId = channelAccessor.addRoomToChannel(id, "r00m");
        ChatRoomMetadataItem meta = chatRoomAccessor.getChatRoomMetaDataById(roomId);
        assertThat(meta).isNotNull();
        assertThat(meta.getName()).isEqualTo("r00m");

        // Assert channel has 1 room
        ChannelStateItem state = channelAccessor.getChannelStateById(id);
        assertThat(state.getRooms().size()).isEqualTo(1);

        // Remove room from channel
        channelAccessor.removeRoomFromChannel(id, roomId);
        state = channelAccessor.getChannelStateById(id);

        // Assert remove worked
        assertThat(state.getRooms()).isEmpty();
        meta = chatRoomAccessor.getChatRoomMetaDataById(roomId);
        assertThat(meta).isNull();
    }

    /**
     * ADDS ALL TABLES TO DATABASE
     * @throws InterruptedException
     */
    private void _createTables() throws InterruptedException {
        DynamoDBUtils.createTable(dynamodb, UserItem.class);
        DynamoDBUtils.createTable(dynamodb, ChannelMetadataItem.class);
        DynamoDBUtils.createTable(dynamodb, ChannelStateItem.class);
        DynamoDBUtils.createTable(dynamodb, ChatRoomMetadataItem.class);
        DynamoDBUtils.createTable(dynamodb, ChatRoomContentItem.class);
        DynamoDBUtils.createTable(dynamodb, MessageItem.class);
        Table table = DynamoDBUtils.createTable(dynamodb, ChatRoomStateItem.class);

        table.waitForActive();
    }

    /**
     * REMOVES ALL TABLES FROM DATABASE
     */
    private void _deleteTables() {
        DynamoDBUtils.deleteTable(dynamodb, UserItem.class);
        DynamoDBUtils.deleteTable(dynamodb, ChannelMetadataItem.class);
        DynamoDBUtils.deleteTable(dynamodb, ChannelStateItem.class);
        DynamoDBUtils.deleteTable(dynamodb, ChatRoomMetadataItem.class);
        DynamoDBUtils.deleteTable(dynamodb, ChatRoomStateItem.class);
        DynamoDBUtils.deleteTable(dynamodb, ChatRoomContentItem.class);
        DynamoDBUtils.deleteTable(dynamodb, MessageItem.class);

    }
}