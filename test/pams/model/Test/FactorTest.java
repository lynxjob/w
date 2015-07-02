package pams.model.Test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pams.dao.FactorDao;
import pams.model.Factor;


public class FactorTest {
	private static FactorDao factorDao;
	private static ClassPathXmlApplicationContext ctx;
	
	@BeforeClass
	public static void before(){
		ctx = new ClassPathXmlApplicationContext("beans.xml");
		factorDao = (FactorDao)ctx.getBean("factorDaoImpl");
	}
	
	@Test
	public void save(){
		Factor f = new Factor();
		f.setCoc(123);
		factorDao.save(f);
	}
	@Test
	public void delete(){
		factorDao.delete(Factor.class, 10);
	}
	@Test
	public void update(){
		Factor f = factorDao.get(Factor.class, 9);
		f.setCrop(null);
		factorDao.update(f);
	}
	
	
	@AfterClass
	public static void after(){
		ctx.destroy();
	}
}
