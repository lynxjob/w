package pams.model.Test;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pams.service.Impl.RoleManager;
import pams.util.JsonUtil;
import pams.web.form.RoleForm;


public class AuthTest {
	private static ClassPathXmlApplicationContext cxt;
	private static RoleManager roleManager;
	
	@BeforeClass
	public static void before(){
		cxt = new ClassPathXmlApplicationContext("beans.xml");
		roleManager = (RoleManager)cxt.getBean("roleManager");
	}
	
	@Test
	public void getUserRoles(){
		Long rowCount = roleManager.getTotal();
		List<RoleForm> rfs = roleManager.getUserRoles(0, 10);
		String jsonString="{rowCount:"+rowCount+",result:"+JsonUtil.getInstance().objectToJson(RoleForm.class,rfs)+"}";
		System.out.println(jsonString);
	}
	@Test
	public void getUserRoleIds(){
		List<Integer> ids = roleManager.getRolePowerIds(3);
		for(Integer id : ids){
			System.out.println(id+"->");
		}
	}
	
	@AfterClass
	public static void after(){
		cxt.destroy();
	}
}
