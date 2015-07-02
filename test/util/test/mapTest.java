package util.test;

import java.util.HashMap;
import java.util.Iterator;

import org.junit.Test;


public class mapTest {
	
	/**
	 * 结果:4 3
	 * 说明:HashMap如果是用已经重载过
	 * HashCode的类型做key,则不会重复
	 * 存储
	 */
	@Test
	public void testMap(){
		HashMap<String,Integer> maps = new HashMap<String,Integer>();
		maps.put("a", 1);
		maps.put("b", 2);
		maps.put("a", 3);
		maps.put("b", 4);
		Iterator<String> it = maps.keySet().iterator();
		while(it.hasNext()){
			System.out.println(maps.get(it.next()));
		}
	}
	
	
	
}
