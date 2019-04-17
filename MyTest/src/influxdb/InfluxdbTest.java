package influxdb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.influxdb.InfluxDB;
import org.influxdb.dto.QueryResult;

public class InfluxdbTest {
	/**
     * 数据库名称
     */
    private static final String database = "influxdb-database";

    /**
     * 数据报存策略1
     */
    private static String   retentionPolicy = "default";

    public static void main(String[] args) {

        InfluxDB influxDB = new InfluxdbBuilder("http://127.0.0.1:8086", "user", "pass").build();

        InfluxdbService service = new InfluxdbService(database, retentionPolicy, influxDB);

        // 创建数据库
        service.createDatabase();

        // 创建数据保存策略
        service.createRetentionPolicy("30d", 1);

        // 插入数据
        Map<String, String> tags = new HashMap<>();
        tags.put("tag1", "getName");
        Map<String, Object> fields = new HashMap<>();
        fields.put("value1", 200);
        fields.put("value2", 300);
        service.insert("biao1", tags, fields);

        // 查询数据
        QueryResult queryResult = service.query("select * from \"biao1\"");
        List<QueryResult.Result> results = queryResult.getResults();
        if (CollectionUtils.isNotEmpty(results)) {
            for (QueryResult.Result result : results) {
                System.out.println(result.toString());
            }
        }

    }
}
