package pams.model.Test;

import java.util.Date;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pams.model.Shed;
import pams.model.User;
import pams.service.Impl.ShedManager;
import pams.service.Impl.UserManager;


public class UserServiceImpl {
	private static ClassPathXmlApplicationContext ctx = null;
	private static UserManager userManager;
	private static ShedManager shedManager;
	
	@BeforeClass
	public static void before(){
		ctx = new ClassPathXmlApplicationContext("beans.xml");
		userManager = (UserManager)ctx.getBean("userManager");
		shedManager = (ShedManager)ctx.getBean("shedManager");
	}
	@Test
	public void get(){
		User user = userManager.get(1);
		System.out.println(user.getName());
	}
	@Test
	public void save(){
		User u = new User();
		u.setAge(20);
		u.setName("张山2");
		u.setPassword("asdfa");
		u.setCreatorId(1);
		u.setCreatorName("admin");
		u.setCdate(new Date());
		u.setTel("1232435");
		userManager.save(u);
		
		Shed shed = new Shed();
		shed.setName("sta");
		shed.setDes("asdf");
		Shed shed2= new Shed();
		shed2.setName("sta2");
		shed2.setDes("asdf2");
		shed.getUsers().add(u);
		shed2.getUsers().add(u);
//		u.getSheds().add(shed);
//		u.getSheds().add(shed2);
		shedManager.save(shed2);
		shedManager.save(shed);
	}
	@Test
	public void list(){
		List<User> users = userManager.list(0, 20, 3);
		Long count = userManager.getTotal(3);
		for(User u : users){
			System.out.println(u.getName()+", count:"+count);
		}
	}
	@Test
	public void delete(){
		User u = userManager.get(7);
		for(Shed shed : u.getSheds()){
			shed.getUsers().remove(u);
			shedManager.update(shed);
		}
		userManager.delete(7);
//		shedManager.delete(7);
	}
	@Test
	public void deleteIds(){
		String ids = "[2]";
		userManager.delete(ids);
	}
	@AfterClass
	public static void after(){
		ctx.destroy();
	}
}
