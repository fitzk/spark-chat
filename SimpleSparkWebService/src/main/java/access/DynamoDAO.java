package access;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import schema.ChannelMetaDataItem;
import schema.ChannelStateItem;

/**
 * Created by phil on 1/31/2016.
 */
public class DynamoDAO {

    private AmazonDynamoDB client;
    private DynamoDBMapper mapper;
    private DynamoDB dynamoDB;

    public void init(AmazonDynamoDB client) {
        this.client = client;
        this.mapper = new DynamoDBMapper(client);
        this.dynamoDB = new DynamoDB(client);
    }

    public DynamoDB getDynamoDB() {
        return dynamoDB;
    }

    public void setDynamoDB(DynamoDB dynamoDB) {
        this.dynamoDB = dynamoDB;
    }

    public DynamoDBMapper getMapper() {
        return mapper;
    }

    public void setMapper(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    public AmazonDynamoDB getClient() {
        return client;
    }

    public void setClient(AmazonDynamoDB client) {
        this.client = client;
    }
}
