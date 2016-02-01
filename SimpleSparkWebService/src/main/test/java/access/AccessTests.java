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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
        ListTablesResult tables = dynamodb.listTables();
        List<String> names = tables.getTableNames();
        assertThat(names.size()).isNotEqualTo(0);
        System.out.println("Tables:");
        for(String name:names) {
            System.out.println(name);
        }
    }

    @AfterClass
    public void teardown() {
        _deleteTables();
    }

    @Test
    public void createChannelTest() {
        ChannelItem accessor = new ChannelItem();
        accessor.init(dynamodb);
        String id = accessor.create("testchannel");
        ChannelMetaDataItem meta = accessor.getChannelMetaById(id);
        assertThat(id).isNotEmpty();
        assertThat(meta.getId()).isEqualTo(id);
        System.out.println("new id: "+id);
    }


    private void _createTables() throws InterruptedException {
        DynamoDBUtils.createTable(dynamodb, ChannelMetaDataItem.class);
        Table table = DynamoDBUtils.createTable(dynamodb, ChannelStateItem.class);
        table.waitForActive();
    }

    private void _deleteTables() {
        DynamoDBUtils.deleteTable(dynamodb, ChannelMetaDataItem.class);
        DynamoDBUtils.deleteTable(dynamodb, ChannelStateItem.class);
    }
}