package cn.mzlalal.cachelog.cachelogdemo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CachelogDemoApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void json () {
		String json = "{\"dimensions\":[{\"type\":\"long\",\"name\":\"POTNO\"}],\"metrics\":[{\"type\":\"doubleSum\",\"name\":\"sum_CUR\",\"fieldName\":\"CUR\"},{\"type\":\"floatSum\",\"name\":\"sum_VOL\",\"fieldName\":\"VOL\"}],\"queryGranularity\":\"HOUR\",\"topic\":\"1114-1\",\"topicIP\":\"192.168.20.105:9092\"}";
		JSONObject jsonObject = JSON.parseObject(json);
		JSONArray metrics = jsonObject.getJSONArray("metrics");
		JSONArray dimensions = jsonObject.getJSONArray("dimensions");
		System.out.println(jsonObject);
	}

}
