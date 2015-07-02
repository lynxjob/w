package pams.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import pams.model.Role;
import pams.model.User;
import pams.service.Impl.RoleManager;
import pams.util.JsonUtil;
import pams.vo.RoleInfo;

import com.opensymphony.xwork2.ModelDriven;
/**
 * @author cjl
 * Role的action
 */
public class RoleAction extends BaseAction implements ModelDriven<RoleInfo>
{
	private static final long serialVersionUID = 1L;
	private RoleManager roleManager;
	private RoleInfo roleInfo=new RoleInfo();

	@Resource(name="roleManager")
	public void setRoleManager(RoleManager roleManager) 
	{
		this.roleManager = roleManager;
	}
	@Override
	public RoleInfo getModel()
	{
		return roleInfo;
	}
	
	public String list()
	{
		Long rowCount=this.roleManager.getTotal();
		List<Role> roles=this.roleManager.list(roleInfo.getStart(),roleInfo.getLimit());
		this.jsonString="{rowCount:"+rowCount+",result:"+JsonUtil.getInstance().objectToJson(Role.class,roles)+"}";
		return "list";
	}
	
	public String save()
	{
		User user = (User)this.getSessionAtrribute("user");
		
		Role role=new Role();
		role.setName(roleInfo.getName());
		role.setDes(roleInfo.getDes());
		role.setCdate(new Date());
		role.setCreatorId(user.getId());
		role.setCreatorName(user.getName());
		this.roleManager.save(role);
		this.jsonString="{success:true,msg:'添加角色完成！'}";
		return "save";
	}
	
	public String update()
	{
		Role role=this.roleManager.get(this.roleInfo.getId());
		role.setName(roleInfo.getName());
		role.setDes(roleInfo.getDes());
		this.roleManager.update(role);
		this.jsonString="{success:true,msg:'修改角色信息完成！'}";
		return "update";
	}
	
	public String delete()
	{
		this.roleManager.delete(roleInfo.getIds());
		this.jsonString="{success:true,msg:'删除角色完成！'}";
		return "delete";
	}
}