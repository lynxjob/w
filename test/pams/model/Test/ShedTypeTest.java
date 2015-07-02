package pams.model.Test;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pams.model.ShedType;
import pams.service.Impl.ShedTypeManager;


public class ShedTypeTest {
	
	private static ClassPathXmlApplicationContext ctx;
	private static ShedTypeManager shedManager;
	
	@BeforeClass
	public static void before(){
		ctx = new ClassPathXmlApplicationContext("beans.xml");
		shedManager = (ShedTypeManager)ctx.getBean("shedTypeManager");
	}
	@Test
	public void save(){
		ShedType shed = new ShedType();
		shed.setName("test");
		shed.setDes("test");
		shed.setParent(shedManager.get(4));
		shedManager.save(shed);
	}
	@Test
	public void get(){
		ShedType shed = (ShedType)shedManager.get(1);
		System.out.println(shed.getName());
	}
	@Test
	public void list(){
		Long rowCount = shedManager.getCount(0);
		List<ShedType> sheds = shedManager.list(0);
		StringBuffer sb = new StringBuffer();
		sb.append("{rowCount:"+rowCount+",result:[");
		for(ShedType shed : sheds){
			sb.append(shed.toString());
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]}");
		
		String jsonString = sb.toString();
		System.out.println(jsonString);
	}
	
	@AfterClass
	public static void after(){
		ctx.destroy();
	}
}
