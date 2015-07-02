package pams.init;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pams.dao.TPowerDao;
import pams.model.AssistFunction;
import pams.model.PowerUsersRoles;
import pams.model.Role;
import pams.model.Shed;
import pams.model.ShedType;
import pams.model.TPower;
import pams.model.User;
import pams.service.Impl.AssistFunctionManager;
import pams.service.Impl.RoleManager;
import pams.service.Impl.ShedManager;
import pams.service.Impl.ShedTypeManager;
import pams.service.Impl.UserManager;
import pams.util.PowerUtil;


public class InitData {
	
	private static ClassPathXmlApplicationContext cxt;
	private static TPowerDao tpowerDaoImpl;
	private static UserManager userManager;
	private static ShedTypeManager shedTypeManager;
	private static ShedManager shedManager;
	private static SessionFactory sessionFactory;
	private static RoleManager roleManager;
	private static AssistFunctionManager assistFunctionManager;
	
	@BeforeClass
	public static void before(){
		cxt = new ClassPathXmlApplicationContext("beans.xml");
		tpowerDaoImpl = (TPowerDao)cxt.getBean("tpowerDaoImpl");
		userManager = (UserManager)cxt.getBean("userManager");
		shedTypeManager = (ShedTypeManager)cxt.getBean("shedTypeManager");
		shedManager = (ShedManager)cxt.getBean("shedManager");
		sessionFactory = (SessionFactory)cxt.getBean("sessionFactory");
		roleManager = (RoleManager)cxt.getBean("roleManager");
		assistFunctionManager = (AssistFunctionManager)cxt.getBean("assistFunctionManager");
	}
	@Test
	public void init(){
		initTPower();
		initShedType();
		initShed();
		initUser();
		initRole();
		initUserRoles();
		initPowerUsersRoles();
		initAdminShed();
		initAssistFunction();
	}
	/**
	 * 初始化导航树形菜单
	 */
	@Test
	public void initNavMenu(){
 		TPower p;
 		for(String name : PowerUtil.navMenus){
 			p = new TPower();
 			p.setName(name);
 			tpowerDaoImpl.save(p);
 		}
	}
	@Test
	public void initAdminShed(){
		User admin = userManager.get(1);
		List<Shed> sheds = shedManager.list(0, 30);
		for(Shed shed : sheds){
			shed.getUsers().add(admin);
			shedManager.update(shed);
		}
	}
	@Test
	public void initShed(){
		Shed shed = new Shed();
		shed.setName("番茄3号园区");
		shed.setDes("特殊品种番茄种植园区3号区");
		shed.setCreatorId(1);
		shed.setCreatorName("admin");
		shed.setShedType(shedTypeManager.get(3));
		shed.setCdate(new Date());
		shedManager.save(shed);
	}
	@Test
	public void initUserRoles(){
		userManager.addUserRole(11, 1);
		userManager.addUserRole(11, 2);
		
	}
	@Test
	public void initRole(){
		//超级管理员
		Role role = new Role();
		role.setName("超级管理员");
		role.setDes("拥有系统最高权限,是系统默认初始数据");
		role.setCdate(new Date());
		role.setCreatorId(1);
		role.setCreatorName("admin");
		roleManager.save(role);
		//农业专家
		Role role1 = new Role();
		role1.setName("农业专家");
		role1.setDes("拥有丰富权威的农业知识,为大家解决农业生产遇到的问题,同时负责专家系统参数的设定");
		role1.setCdate(new Date());
		role1.setCreatorId(1);
		role1.setCreatorName("admin");
		roleManager.save(role1);
	}
	@Test
	public void initShedType(){
		//蔬菜大棚
		ShedType shed1 = new ShedType();
		shed1.setName("蔬菜大棚");
		shed1.setDes("种植各种蔬菜");
		ShedType scshed1 = new ShedType();
		scshed1.setName("黄瓜");
		scshed1.setDes("特色黄瓜1号");
		scshed1.setParent(shed1);
		ShedType scshed2 = new ShedType();
		scshed2.setName("番茄");
		scshed2.setDes("特色番茄1号");
		scshed2.setParent(shed1);
		shed1.getChildren().add(scshed1);
		shed1.getChildren().add(scshed2);
		shedTypeManager.save(shed1);
		//花卉大棚
		ShedType shed2 = new ShedType();
		shed2.setName("花卉大棚");
		shed2.setDes("种植各种名贵花卉");
		ShedType hhshed1 = new ShedType();
		hhshed1.setName("玫瑰");
		hhshed1.setDes("特色玫瑰1号");
		hhshed1.setParent(shed2);
		ShedType hhshed2 = new ShedType();
		hhshed2.setName("牡丹");
		hhshed2.setDes("特色牡丹1号");
		hhshed2.setParent(shed2);
		shed2.getChildren().add(hhshed1);
		shed2.getChildren().add(hhshed2);
		shedTypeManager.save(shed2);
	}
	@Test
	public void initAssistFunction(){
		String m1 = "{id:'Long.ClientGrid_clientgrid',iconCls:'db_icon',text:'客户信息管理',leaf:true}";
		String m2 = "{id:'Long.ProviderGrid_providergrid',iconCls:'db_icon',text:'供应商信息管理',leaf:true}";
		String m3 = "{id:'Long.GoodsGrid_goodsgrid',iconCls:'db_icon',text:'退幼苗账单管理',leaf:true}";
		String m4 = "{id:'Long.StockGrid_stockgrid',iconCls:'db_icon',text:'幼苗账单管理',leaf:true}";
		String m5 = "{id:'Long.SaleGrid_salegrid',iconCls:'db_icon',text:'退销售账单管理',leaf:true}";
		String m6 = "{id:'Long.SellingGrid_sellinggrid',iconCls:'db_icon',text:'销售账单管理',leaf:true}";
		String m7 = "{id:'Long.ReserveGrid_reservegrid',iconCls:'db_icon',text:'库存信息管理',leaf:true}";
		//基本信息管理
		AssistFunction function1 = new AssistFunction();
		function1.setName("基础信息管理");
		function1.setDes("描述客户和供应商的基础信息");
		AssistFunction jcfunction1 = new AssistFunction();
		jcfunction1.setName("客户信息管理");
		jcfunction1.setDes("描述客户的基础信息");
		jcfunction1.setParent(function1);
		jcfunction1.setValue(m1);
		AssistFunction jcfunction2 = new AssistFunction();
		jcfunction2.setName("供应商信息管理");
		jcfunction2.setDes("描述供应商的基础信息");
		jcfunction2.setParent(function1);
		jcfunction2.setValue(m2);
		function1.getChildren().add(jcfunction1);
		function1.getChildren().add(jcfunction2);
		assistFunctionManager.save(function1);
		//入库信息管理
		AssistFunction function2 = new AssistFunction();
		function2.setName("入库信息管理");
		function2.setDes("描述进入幼苗货单和退幼苗货单的基础信息");
		AssistFunction rcfunction1 = new AssistFunction();
		rcfunction1.setName("进幼苗账单");
		rcfunction1.setDes("描述进幼苗货单的信息");
		rcfunction1.setParent(function2);
		rcfunction1.setValue(m4);
		AssistFunction rcfunction2 = new AssistFunction();
		rcfunction2.setName("退幼苗账单");
		rcfunction2.setDes("描述退幼苗货单的信息");
		rcfunction2.setParent(function2);
		rcfunction2.setValue(m3);
		function2.getChildren().add(rcfunction1);
		function2.getChildren().add(rcfunction2);
		assistFunctionManager.save(function2);
		//销售信息管理
		AssistFunction function3 = new AssistFunction();
		function3.setName("销售信息管理");
		function3.setDes("描述销售单和退货单的信息");
		AssistFunction xsfunction1 = new AssistFunction();
		xsfunction1.setName("销售账单");
		xsfunction1.setDes("描述销售情况的信息");
		xsfunction1.setParent(function3);
		xsfunction1.setValue(m6);
		AssistFunction xsfunction2 = new AssistFunction();
		xsfunction2.setName("退销售账单");
		xsfunction2.setDes("描述销售退货情况的信息");
		xsfunction2.setParent(function3);
		xsfunction2.setValue(m5);
		function3.getChildren().add(xsfunction1);
		function3.getChildren().add(xsfunction2);
		assistFunctionManager.save(function3);
		//库存管理信息
		AssistFunction function4 = new AssistFunction();
		function4.setName("库存信息管理");
		function4.setDes("描述库存容量的信息");
		AssistFunction kcfunction1 = new AssistFunction();
		kcfunction1.setName("库存信息");
		kcfunction1.setDes("描述入库作物的一些情况");
		kcfunction1.setParent(function4);
		kcfunction1.setValue(m7);
		function4.getChildren().add(kcfunction1);
		assistFunctionManager.save(function4);
		
	}
	@Test
	public void deleteShedType(){
		shedTypeManager.delete(3);
		shedTypeManager.delete(1);
		shedTypeManager.delete(4);
	}
	@Test
	public void initUser(){
		User u = new User();
		u.setAge(20);
		u.setCdate(new Date());
		u.setName("admin");
		u.setPassword("admin");
		u.setTel("15667876934");
		u.setState(1);
		userManager.save(u);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void initPowerUsersRoles(){
		Session session = sessionFactory.openSession();
		List<TPower> powers = new ArrayList<TPower>();
		PowerUsersRoles pur;
		session.beginTransaction();
		powers = session.createQuery("from TPower").list();
		for(TPower power : powers){
			pur = new PowerUsersRoles();
			pur.setFlag(0);
			pur.setObjectId(1);
			pur.setPower(power);
			session.save(pur);
		}
		session.getTransaction().commit();
		session.flush();
		session.close();
	}
	/**
	 * 初始化菜单面板及按钮设置
	 * 根据ID对应导航菜单,导航菜
	 * 单ID变化时此对应项需要改变
	 */
	@Test
	public void initTPower(){
		//视频监测 
		TPower tp1 = new TPower();
		tp1.setName(PowerUtil.navMenus[0]);
		TPower p1 = new TPower();
		p1.setFlag(1);
		p1.setName("查看");
		p1.setValue(PowerUtil.menus[0]);
		p1.setParent(tp1);
		tp1.getChildren().add(p1);
		tpowerDaoImpl.save(tp1);
		//环境监测
		TPower tp2 = new TPower();
		tp2.setName(PowerUtil.navMenus[1]);
		TPower p2 = new TPower();
		p2.setFlag(1);
		p2.setName("查看");
		p2.setValue(PowerUtil.menus[1]);
		p2.setParent(tp2);
		tp2.getChildren().add(p2);
		tpowerDaoImpl.save(tp2);
		//控制中心
		TPower tp3 = new TPower();
		tp3.setName(PowerUtil.navMenus[2]);
		TPower p3 = new TPower();
		p3.setFlag(1);
		p3.setName("查看");
		p3.setValue(PowerUtil.menus[2]);
		p3.setParent(tp3);
		tp3.getChildren().add(p3);
		tpowerDaoImpl.save(tp3);
		//专家系统
		TPower tp4 = new TPower();
		tp4.setName(PowerUtil.navMenus[3]);
		TPower p4 = new TPower();
		p4.setFlag(1);
		p4.setName("查看");
		p4.setValue(PowerUtil.menus[3]);
		p4.setParent(tp4);
		tp4.getChildren().add(p4);
		tpowerDaoImpl.save(tp4);
		//查询平台
		TPower tp5 = new TPower();
		tp5.setName(PowerUtil.navMenus[4]);
		TPower p5 = new TPower();
		p5.setFlag(1);
		p5.setName("查看");
		p5.setValue(PowerUtil.menus[4]);
		p5.setParent(tp5);
		tp5.getChildren().add(p5);
		tpowerDaoImpl.save(tp5);
		//大棚管理
		TPower tp6 = new TPower();
		tp6.setName(PowerUtil.navMenus[5]);
		TPower p6 = new TPower();
		p6.setFlag(1);
		p6.setName("管理");
		p6.setValue(PowerUtil.menus[5]);
		p6.setParent(tp6);
		//Buttons
		TPower bp1 = new TPower();
		bp1.setFlag(2);
		bp1.setName("添加分类");
		bp1.setValue("Long.spTAdd=true");
		bp1.setParent(tp6);
		TPower bp2 = new TPower();
		bp2.setFlag(2);
		bp2.setName("修改分类");
		bp2.setValue("Long.spTEdit=true");
		bp2.setParent(tp6);
		TPower bp3 = new TPower();
		bp3.setFlag(2);
		bp3.setName("删除分类");
		bp3.setValue("Long.spTDelete=true");
		bp3.setParent(tp6);
		TPower bp4 = new TPower();
		bp4.setFlag(2);
		bp4.setName("添加");
		bp4.setValue("Long.spAdd=true");
		bp4.setParent(tp6);
		TPower bp5 = new TPower();
		bp5.setFlag(2);
		bp5.setName("修改");
		bp5.setValue("Long.spEdit=true");
		bp5.setParent(tp6);
		TPower bp6 = new TPower();
		bp6.setFlag(2);
		bp6.setName("删除");
		bp6.setValue("Long.spDelete=true");
		bp6.setParent(tp6);
		TPower move = new TPower();
		move.setFlag(2);
		move.setName("移动");
		move.setValue("Long.spMove=true");
		move.setParent(tp6);
		//
		tp6.getChildren().add(p6);
		tp6.getChildren().add(bp1);
		tp6.getChildren().add(bp2);
		tp6.getChildren().add(bp3);
		tp6.getChildren().add(bp4);
		tp6.getChildren().add(bp5);
		tp6.getChildren().add(bp6);
		tp6.getChildren().add(move);
		tpowerDaoImpl.save(tp6);
		//设备管理
		TPower tp7 = new TPower();
		tp7.setName(PowerUtil.navMenus[6]);
		TPower p7 = new TPower();
		p7.setFlag(1);
		p7.setName("管理");
		p7.setValue(PowerUtil.menus[6]);
		p7.setParent(tp7);
		tp7.getChildren().add(p7);
		tpowerDaoImpl.save(tp7);
		//作物管理
		TPower tp_0 = new TPower();
		tp_0.setName(PowerUtil.navMenus[7]);
		TPower p_0 = new TPower();
		p_0.setFlag(1);
		p_0.setName("管理");
		p_0.setValue(PowerUtil.menus[7]);
		p_0.setParent(tp_0);
		//buttons
		TPower cp1 = new TPower();
		cp1.setFlag(2);
		cp1.setName("添加");
		cp1.setValue("Long.cpAdd=true");
		cp1.setParent(tp_0);
		TPower cp2 = new TPower();
		cp2.setFlag(2);
		cp2.setName("修改");
		cp2.setValue("Long.cpEdit=true");
		cp2.setParent(tp_0);
		TPower cp3 = new TPower();
		cp3.setFlag(2);
		cp3.setName("删除");
		cp3.setValue("Long.cpDelete=true");
		cp3.setParent(tp_0);
		tp_0.getChildren().add(p_0);
		tp_0.getChildren().add(cp1);
		tp_0.getChildren().add(cp2);
		tp_0.getChildren().add(cp3);
		tpowerDaoImpl.save(tp_0);
		//辅助系统
		TPower tp8 = new TPower();
		tp8.setName(PowerUtil.navMenus[8]);
		TPower p8 = new TPower();
		p8.setFlag(1);
		p8.setName("管理");
		p8.setValue(PowerUtil.menus[8]);
		p8.setParent(tp8);
		tp8.getChildren().add(p8);
		tpowerDaoImpl.save(tp8);
		//角色信息
		TPower tp9 = new TPower();
		tp9.setName(PowerUtil.navMenus[9]);
		TPower p9 = new TPower();
		p9.setFlag(1);
		p9.setName("管理");
		p9.setValue(PowerUtil.menus[9]);	
		p9.setParent(tp9);
		//Buttons
		TPower bp7 = new TPower();
		bp7.setFlag(2);
		bp7.setName("添加");
		bp7.setValue("Long.rgAdd=true");
		bp7.setParent(tp9);
		TPower bp8 = new TPower();
		bp8.setFlag(2);
		bp8.setName("修改");
		bp8.setValue("Long.rgEdit=true");
		bp8.setParent(tp9);
		TPower bp9 = new TPower();
		bp9.setFlag(2);
		bp9.setName("删除");
		bp9.setValue("Long.rgDelete=true");
		bp9.setParent(tp9);
		TPower bp10 = new TPower();
		bp10.setFlag(2);
		bp10.setName("角色授权");
		bp10.setValue("Long.rgRole=true");
		bp10.setParent(tp9);
		tp9.getChildren().add(p9);
		tp9.getChildren().add(bp7);
		tp9.getChildren().add(bp8);
		tp9.getChildren().add(bp9);
		tp9.getChildren().add(bp10);
		tpowerDaoImpl.save(tp9);
		//用户账户
		TPower tp10 = new TPower();
		tp10.setName(PowerUtil.navMenus[10]);
		TPower p10 = new TPower();
		p10.setFlag(1);
		p10.setName("管理");
		p10.setValue(PowerUtil.menus[10]);
		p10.setParent(tp10);
		//Buttons
		TPower bp11 = new TPower();
		bp11.setFlag(2);
		bp11.setName("添加");
		bp11.setValue("Long.ugAdd=true");
		bp11.setParent(tp10);
		TPower bp12 = new TPower();
		bp12.setFlag(2);
		bp12.setName("修改");
		bp12.setValue("Long.ugEdit=true");
		bp12.setParent(tp10);
		TPower bp13 = new TPower();
		bp13.setFlag(2);
		bp13.setName("删除");
		bp13.setValue("Long.ugDelete=true");
		bp13.setParent(tp10);
		TPower bp14 = new TPower();
		bp14.setFlag(2);
		bp14.setName("用户角色");
		bp14.setValue("Long.ugRole=true");
		bp14.setParent(tp10);
		TPower bp15 = new TPower();
		bp15.setFlag(2);
		bp15.setName("额外权限");
		bp15.setValue("Long.ugPower=true");
		bp15.setParent(tp10);
		tp10.getChildren().add(p10);
		tp10.getChildren().add(bp11);
		tp10.getChildren().add(bp12);
		tp10.getChildren().add(bp13);
		tp10.getChildren().add(bp14);
		tp10.getChildren().add(bp15);
		tpowerDaoImpl.save(tp10);
		//系统公告
		TPower tp11 = new TPower();
		tp11.setName(PowerUtil.navMenus[11]);
		TPower p11 = new TPower();
		p11.setFlag(1);
		p11.setName("查看");
		p11.setValue(PowerUtil.menus[11]);
		p11.setParent(tp11);
		//Buttons
		TPower bp16 = new TPower();
		bp16.setFlag(2);
		bp16.setName("添加");
		bp16.setValue("Long.ngAdd=true");
		bp16.setParent(tp11);
		TPower bp17 = new TPower();
		bp17.setFlag(2);
		bp17.setName("修改");
		bp17.setValue("Long.ngEdit=true");
		bp17.setParent(tp11);
		TPower bp18 = new TPower();
		bp18.setFlag(2);
		bp18.setName("删除");
		bp18.setValue("Long.ngDelete=true");
		bp18.setParent(tp11);
		/*TPower bp180 = new TPower();
		bp180.setFlag(2);
		bp180.setName("查看排序");
		bp180.setValue("Long.ngLookSort=true");
		bp180.setParent(tp11);*/
		tp11.getChildren().add(p11);
		tp11.getChildren().add(bp16);
		tp11.getChildren().add(bp17);
		tp11.getChildren().add(bp18);
//		tp11.getChildren().add(bp180);
		tpowerDaoImpl.save(tp11);
		//系统日志
		TPower tp12 = new TPower();
		tp12.setName(PowerUtil.navMenus[12]);
		TPower p12 = new TPower();
		p12.setFlag(1);
		p12.setName("查看");
		p12.setValue(PowerUtil.menus[12]);	
		p12.setParent(tp12);
		tp12.getChildren().add(p12);
		tpowerDaoImpl.save(tp12);
		//数据备份
		TPower tp13 = new TPower();
		tp13.setName(PowerUtil.navMenus[13]);
		TPower p13 = new TPower();
		p13.setFlag(1);
		p13.setName("管理");
		p13.setValue(PowerUtil.menus[13]);	
		p13.setParent(tp13);
		//Buttons
		TPower bp19 = new TPower();
		bp19.setFlag(2);
		bp19.setName("备份");
		bp19.setValue("Long.bgBack=true");
		bp19.setParent(tp13);
		TPower bp20 = new TPower();
		bp20.setFlag(2);
		bp20.setName("还原");
		bp20.setValue("Long.bgReStore=true");
		bp20.setParent(tp13);
		TPower bp21 = new TPower();
		bp21.setFlag(2);
		bp21.setName("下载");
		bp21.setValue("Long.bgDownLoad=true");
		bp21.setParent(tp13);
		TPower bp22 = new TPower();
		bp22.setFlag(2);
		bp22.setName("删除");
		bp22.setValue("Long.bgDelete=true");
		bp22.setParent(tp13);
		tp13.getChildren().add(p13);
		tp13.getChildren().add(bp19);
		tp13.getChildren().add(bp20);
		tp13.getChildren().add(bp21);
		tp13.getChildren().add(bp22);
		tpowerDaoImpl.save(tp13);	
	}
	
	@Test
	public void deleteShedType3(){
		System.out.println(shedTypeManager.delete(4));
	}
	
	@Test
	public void deleteTPower(){
		tpowerDaoImpl.delete(TPower.class, 1);
	}
	
	
	@AfterClass
	public static void after(){
		cxt.destroy();
	}
}
