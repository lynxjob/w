package pams.model.Test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class UserRoleTest {
	
	private static ClassPathXmlApplicationContext cxt;
	
	@BeforeClass
	public static void before(){
		cxt = new ClassPathXmlApplicationContext("beans.xml");
	}
	
	
	@AfterClass
	public static void after(){
		cxt.destroy();
	}
}
