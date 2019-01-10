package gnova.geometry.io;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args) {

        ServerAddress serverAddress = new ServerAddress("192.168.179.130", 27017);

        MongoClientOptions.Builder builder = new MongoClientOptions.Builder();

        char c1 = '\u0061';

        List<MongoCredential> mongoCredentials = new ArrayList<>();
        MongoCredential mongoCredential = MongoCredential.createCredential(
                "birderyu",
                "admin",
                "birderyu".toCharArray()
        );
        mongoCredentials.add(mongoCredential);
        MongoClient mongoClient = new MongoClient(serverAddress, mongoCredentials, builder.build());

        MongoDatabase mdb = mongoClient.getDatabase("admin");
        mdb.createCollection("test");
        MongoCollection<Document> mc = mdb.getCollection("test");
    }

}
