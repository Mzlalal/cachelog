package cn.mzlalal.cachelog.cachelogdemo;

import cn.mzlalal.cachelog.cachelogcore.entity.enums.ExpiredPolicyEnums;
import cn.mzlalal.cachelog.cachelogcore.entity.enums.MethodHeadEnums;
import cn.mzlalal.cachelog.cachelogcore.interfaces.CacheLogFormatTypeInterface;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Arrays;

@Slf4j
@SpringBootTest
public class CachelogDemoApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void enums () {
		for (MethodHeadEnums temp : MethodHeadEnums.values()) {
			System.out.println(temp.getHead() + temp.getType());
		}
	}

	@Test
	public void isInstance () {
		try {
			// 根据指定的类路径查询
			Class clazz = Class.forName("cn.mzlalal.cachelog.cachelogdemo.config.BaseRedisConfig");
			Object instanceObj = clazz.newInstance();
			Assert.isInstanceOf(CacheLogFormatTypeInterface.class, instanceObj, "该类未实现CacheLogFormatTypeInterface接口!");
		} catch (Exception e) {
			log.error("", e);
		}
	}

	@Test
	public void arrs () {
		String test1="test1,test2,test3";
		String test2="test1,test3,test2";
		// 开始第二步详细对比
		String[] fieldNameArr = test1.split(",");
		String[] fieldNameMetaSetArr = test2.split(",");
		Arrays.sort(fieldNameArr);
		Arrays.sort(fieldNameMetaSetArr);
		System.out.println(Arrays.equals(fieldNameArr, fieldNameMetaSetArr));
	}

	@Test
	public void stringEq() {
		String a = ExpiredPolicyEnums.DEFAULT.toString();
		String b = ExpiredPolicyEnums.DEFAULT.toString();
		System.out.println(StringUtils.equals(a, b));
	}
}
