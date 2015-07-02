package pams.model.Test;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pams.model.Shed;
import pams.service.Impl.ShedManager;


public class ShedTest {
	
	private static ClassPathXmlApplicationContext ctx;
	private static ShedManager shedManager;
	
	@BeforeClass
	public static void before(){
		ctx = new ClassPathXmlApplicationContext("beans.xml");
		shedManager = (ShedManager)ctx.getBean("shedManager");
	}

	@Test
	public void get(){
		Shed shed = (Shed)shedManager.get(1);
		System.out.println(shed.getName());
	}
	@Test
	public void list(){
		Long rowCount = shedManager.getCount(0);
		List<Shed> sheds = shedManager.list(0);
		StringBuffer sb = new StringBuffer();
		sb.append("{rowCount:"+rowCount+",result:[");
		for(Shed shed : sheds){
			sb.append(shed.toString());
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]}");
		
		String jsonString = sb.toString();
		System.out.println(jsonString);
	}
	@Test
	public void listTree(){
		System.out.println(shedManager.list());
	}
	@AfterClass
	public static void after(){
		ctx.destroy();
	}
}
