package pams.action;

import java.util.List;

import javax.annotation.Resource;

import pams.model.TPower;
import pams.model.User;
import pams.service.Impl.RoleManager;
import pams.service.Impl.TPowerManager;
import pams.service.Impl.UserManager;
import pams.util.JsonUtil;
import pams.vo.AuthInfo;
import pams.web.form.RoleForm;

import com.opensymphony.xwork2.ModelDriven;

/**
 * @author cjl
 * 处理权限：
 * 		角色授权
 * 		用户的角色管理，用户额外授权
 * 		用户登录后动态生成菜单和控制项的按钮
 */
public class AuthAction extends BaseAction implements ModelDriven<AuthInfo>
{
	private static final long serialVersionUID = 1L;
	private RoleManager roleManager;
	private UserManager userManager;
	private TPowerManager tpowerManager;
	private AuthInfo authInfo=new AuthInfo();

	@Resource(name="roleManager")
	public void setRoleManager(RoleManager roleManager) 
	{
		this.roleManager = roleManager;
	}
	@Resource(name="userManager")
	public void setUserManager(UserManager userManager) 
	{
		this.userManager = userManager;
	}
	@Resource(name="tpowerManager")
	public void setTpowerManager(TPowerManager tpowerManager)
	{
		this.tpowerManager = tpowerManager;
	}
	@Override
	public AuthInfo getModel() 
	{
		return authInfo;
	}
	
	/**
	 * 根据用户权限动态生成菜单
	 * userID
	 */
	public String loadMenus()
	{
		User user = (User)this.getSessionAtrribute("user");
		
		List<String> menus=this.userManager.listMenus(user.getId());
		StringBuffer sb=new StringBuffer();
		sb.append("[");
		for(String menu : menus)
		{
			sb.append(menu+",");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append("]");
		this.jsonString=sb.toString();
		return "loadMenus";
	}
	/**
	 * 根据用户权限动态生成选项按钮
	 * userID
	 */
	public String loadButtons()
	{
		User user = (User)this.getSessionAtrribute("user");
		
		List<String> buttons=this.userManager.listButtons(user.getId());
		StringBuffer sb=new StringBuffer();
		sb.append("Ext.namespace('Long');\n");
		for(String button : buttons)
		{
			sb.append(button+"\n");
		}
		this.jsonString=sb.toString();
		return "loadButtons";
	}
	/**
	 * 获取用户拥有角色
	 */
	public String getUserRoles()
	{
		Long rowCount=roleManager.getTotal();
		List<Integer> urs=userManager.getUserRoleIds(authInfo.getUserId());
		List<RoleForm> rfs=roleManager.getUserRoles(authInfo.getStart(),authInfo.getLimit());
		Integer id=0;
		System.out.println("用户拥有角色"+urs);
		//根据用户拥有角色id 和 所有角色id.如果有,check设置为true.
		for(RoleForm temp : rfs)
		{
			id=temp.getId();
			if(urs.contains(id))
			{
				temp.setCheck(true);
				urs.remove(id);
			}
		}
		this.jsonString="{rowCount:"+rowCount+",result:"+JsonUtil.getInstance().objectToJson(RoleForm.class,rfs)+"}";
		return "getUserRoles";
	}
	
	/**
	 * 用户角色保存
	 */
	public String saveUserRoles()
	{
		//System.out.println(authInfo.getUserId());
		//System.out.println(this.jsonString);
		List<RoleForm> rfs=JsonUtil.getInstance().jsonToRoleForms(jsonString);
		for(RoleForm temp : rfs)
		{
			if(temp.isCheck())//true，添加角色
			{
				this.userManager.addUserRole(authInfo.getUserId(),temp.getId());
			}
			else//false，删除角色
			{
				this.userManager.deleteUserRole(authInfo.getUserId(),temp.getId());
			}
		}
		this.jsonString="{success:true,msg:'保存用户角色完成！'}";
		return "saveUserRoles";
	}
	
	/**
	 * 获取用户的所有额外权限
	 */
	public String getUserPowers()
	{
		List<TPower> powers=tpowerManager.listAllTPowers();
		List<Integer> ups=userManager.getUserPowerIds(authInfo.getUserId());
		this.jsonString=this.tpowersToJson(powers,ups);
		System.out.println(this.jsonString);
		return "getUserPowers";
	}
	
	/**
	 * 保存用户单条额外权限
	 */
	public String saveUserPower()
	{
		userManager.addUserPower(authInfo.getUserId(),authInfo.getPowerId());
		this.jsonString="{success:true,msg:'授予用户一条权限完成！'}";
		//System.out.println("添加用户权限："+authInfo.getUserId()+"  "+authInfo.getPowerId());
		return "saveUserPower";
	}
	
	/**
	 * 删除用户的单条额外权限记录
	 */
	public String deleteUserPower()
	{
		userManager.deleteUserPower(authInfo.getUserId(),authInfo.getPowerId());
		this.jsonString="{success:true,msg:'删除用户一条权限完成！'}";
		//System.out.println("删除用户权限："+authInfo.getUserId()+"  "+authInfo.getPowerId());
		return "deleteUserPower";
	}
	
	
	/**
	 * 获取单个角色的所有权限
	 */
	public String getRolePowers()
	{
		List<TPower> powers=tpowerManager.listAllTPowers();
		List<Integer> rps=roleManager.getRolePowerIds(authInfo.getRoleId());
		this.jsonString=this.tpowersToJson(powers,rps);
		//System.out.println(this.jsonString);
		return "getRolePowers";
	}
	
	/**
	 * 保存角色的单条权限记录
	 */
	public String saveRolePower()
	{
		roleManager.addRolePower(authInfo.getRoleId(),authInfo.getPowerId());
		this.jsonString="{success:true,msg:'授予角色一条权限完成！'}";
		//System.out.println("添加角色权限："+authInfo.getRoleId()+"  "+authInfo.getPowerId());
		return "saveRolePower";
	}
	
	/**
	 * 删除用户单条权限记录
	 */
	public String deleteRolePower()
	{
		roleManager.deleteRolePower(authInfo.getRoleId(),authInfo.getPowerId());
		this.jsonString="{success:true,msg:'删除角色一条权限完成！'}";
		//System.out.println("删除角色权限："+authInfo.getRoleId()+"  "+authInfo.getPowerId());
		return "deleteRolePower";
	}
	
	
	/**
	 * 私有方法
	 * 将List<TPower>转换成json字符串
	 */
	private String tpowersToJson(List<TPower> powers,List<Integer> tempIds)
	{
		StringBuffer jsonString=new StringBuffer();
		jsonString.append("[");
		for(TPower power : powers)
		{
			jsonString.append("{id:'"+power.getId()+"',text:'"+power.getName());
			this.recursion(power,jsonString,tempIds);
		}
		jsonString.deleteCharAt(jsonString.length()-1);
		jsonString.append("]");
		return jsonString.toString();
	}
	/**
	 * 递归循环树
	 */
	private void recursion(TPower power,StringBuffer jsonString,List<Integer> tempIds)
	{
		if(power.getChildren().size()==0)
		{
			Integer pid=power.getId();
			if(tempIds.contains(power.getId()))
			{
				power.setChecked(true);
				tempIds.remove(pid);
			}
			jsonString.append("',leaf:true,checked:"+power.isChecked()+"},");
		}
		else
		{
			Object[] powers= power.getChildren().toArray();
			TPower temp=null;
			for(int i=0;i< powers.length;i++)
			{
				temp=(TPower)powers[i];
				if(i==0)
				{
					jsonString.append("',children:[{id:'"+temp.getId()+"',text:'"+temp.getName());
					this.recursion(temp,jsonString,tempIds);
				}
				else 
				{
					jsonString.append("{id:'"+temp.getId()+"',text:'"+temp.getName());
					this.recursion(temp,jsonString,tempIds);
				}
			}
			jsonString.deleteCharAt(jsonString.length()-1);
			jsonString.append("]},");
		}
	}
}