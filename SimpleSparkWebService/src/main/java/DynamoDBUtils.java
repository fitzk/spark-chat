import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;

import java.util.Map;


public class DynamoDBUtils
{

    public static Table createTable(AmazonDynamoDB client, Class schema) throws InterruptedException {
        DynamoDB dynamodb = new DynamoDB(client);
        DynamoDBMapper mapper = new DynamoDBMapper(client);
        CreateTableRequest req = mapper.generateCreateTableRequest(schema);
        req.setProvisionedThroughput(new ProvisionedThroughput(5L, 5L));
        Table table = dynamodb.createTable(req);
        return table;
    }

    public static DeleteTableResult deleteTable(AmazonDynamoDB dynamodb, Class schema) {
        DynamoDBMapper mapper = new DynamoDBMapper(dynamodb);
        DeleteTableRequest req = mapper.generateDeleteTableRequest(schema);
        return dynamodb.deleteTable(req);
    }
    public static void listItems(AmazonDynamoDB client, String table_name){

        ScanRequest scanRequest = new ScanRequest().withTableName(table_name);
        ScanResult result = client.scan(scanRequest);
        for(Map<String, AttributeValue> item : result.getItems()){
            System.out.println(item.values().toString());
        }
    }


}
