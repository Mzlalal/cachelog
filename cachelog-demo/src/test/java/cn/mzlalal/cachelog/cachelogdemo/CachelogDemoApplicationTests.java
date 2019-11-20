package cn.mzlalal.cachelog.cachelogdemo;

import cn.mzlalal.cachelog.cachelogcore.entity.enums.MethodHead;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CachelogDemoApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void enums () {
		for (MethodHead temp : MethodHead.values()) {
			System.out.println(temp.getHead() + temp.getType());
		}
	}
}
