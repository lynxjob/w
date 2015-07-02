package pams.model.Test;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pams.model.Crop;
import pams.model.Factor;
import pams.service.Impl.CropManager;
import pams.service.Impl.FactorManager;


public class CropTest {
	
	private static ClassPathXmlApplicationContext ctx;
	private static CropManager cropManager;
	private static FactorManager factorManager;
	
	@BeforeClass
	public static void before(){
		ctx = new ClassPathXmlApplicationContext("beans.xml");
		cropManager = (CropManager)ctx.getBean("cropManager");
		factorManager = (FactorManager)ctx.getBean("factorManager");
	}
	
	
	@Test
	public void delete(){
		cropManager.delete(3);
	}
	@Test
	public void delete2(){
		cropManager.delete("{3}");
	}
	@Test
	public void listFactor(){
		Factor f = cropManager.getCondition(1);
		System.out.println("CO2适宜值为:"+f.getCoc());
	}
	@Test
	public void listLevel(){
		System.out.println("现在所处生长阶段为: "+cropManager.getLevel(1));
	}
	@Test
	public void update(){
		Crop crop = cropManager.get(1);
		Factor f = new Factor();
		f.setCoc(321);
		f.setCrop(crop);
		List<Factor> factors = crop.getFactors();
		for(Factor factor : factors){
			factor.setCrop(null);
			factorManager.update(factor);
		}
		factorManager.delete();
//		Factor factor = factors.get(0);
//		for(Factor factor : factors){
//			factor.setCoc(f.getCoc());
//			factor.setDadiwendu(f.getDadiwendu());
//			factor.setDaqishidu(f.getDaqishidu());
//			factor.setDaqiwendu(f.getDaqiwendu());
//			factor.setGuanghe(f.getGuanghe());
//			factor.setLevel(f.getLevel());
//			factor.setStart(f.getStart());
//			factor.setEnd(f.getEnd());
//			factor.setTurangshuifen(f.getTurangshuifen());
//		}
		crop.getFactors().add(f);
		cropManager.update(crop);
	}
	@AfterClass
	public static void after(){
		ctx.destroy();
	}
}
