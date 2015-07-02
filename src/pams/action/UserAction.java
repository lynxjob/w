package pams.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import pams.model.Shed;
import pams.model.User;
import pams.service.Impl.ShedManager;
import pams.service.Impl.UserManager;
import pams.util.JsonUtil;
import pams.vo.UserInfo;

import com.opensymphony.xwork2.ModelDriven;
/**
 * @author cjl
 * 与前台交互
 */
public class UserAction extends BaseAction implements ModelDriven<UserInfo>
{
	private static final long serialVersionUID = 1L;
	private UserManager userManager;
	private ShedManager shedManager;
	private UserInfo userInfo=new UserInfo();

	@Resource(name="userManager")
	public void setUserManager(UserManager userManager) 
	{
		this.userManager = userManager;
	}
	@Resource(name="shedManager")
	public void setShedManager(ShedManager shedManager){
		this.shedManager = shedManager;
	}
	@Override
	public UserInfo getModel()
	{
		return userInfo;
	}

	/**
	 * 普通表格CRUD操作
	 */
	public String list()
	{
		Long rowCount=this.userManager.getTotal(userInfo.getShedId());
		List<User> users=this.userManager.list(userInfo.getStart(),userInfo.getLimit(),userInfo.getShedId());
		this.jsonString="{rowCount:"+rowCount+",result:"+userManager.getJsonString(users)+"}";
		return "list";
	}
	public String save()
	{
		User temp = (User)this.getSessionAtrribute("user");
		int[] shedIds = this.userInfo.getShedIds();
		if(this.userManager.checkUserExistsByName(userInfo.getName()))
		{
			this.jsonString="{success:false,msg:'新添加用户的名字不能与已有用户的名字重复!'}";
		}
		else
		{
			User user=new User();
			user.setName(userInfo.getName());
			user.setPassword(userInfo.getPassword());
			user.setAge(userInfo.getAge());
			user.setTel(userInfo.getTel());
			user.setState(userInfo.getState());
			user.setCdate(new Date());
			user.setCreatorId(temp.getId());
			user.setCreatorName(temp.getName());
			this.userManager.save(user);
			Shed shed = null;
			for(int shedId : shedIds){
				shed = shedManager.get(shedId);
				shed.getUsers().add(user);
				this.shedManager.update(shed);
			}
			this.jsonString="{success:true,msg:'添加用户完成!'}";
			this.recordLog("添加用户信息", "用户姓名:"+userInfo.getName());
		}
		return "save";
	}
	public String update()
	{
		if(this.userManager.checkUserExistsByName(userInfo.getName()))
		{
			this.jsonString="{success:false,msg:'用户名字不能与已有用户的名字重复!'}";
		}
		else
		{
			User user=userManager.get(userInfo.getId());
			user.setName(userInfo.getName());
			user.setPassword(userInfo.getPassword());
			user.setAge(userInfo.getAge());
			user.setTel(userInfo.getTel());
			
			this.userManager.update(user);
			this.jsonString="{success:true,msg:'修改用户信息完成!'}";
			this.recordLog("修改用户信息", "用户ID:"+userInfo.getId());
		}
		return "update";
	}
	public String delete()
	{
		String idsStr=(String) userInfo.getIds().subSequence(1,userInfo.getIds().length()-1);
		for(String ids : idsStr.split(",")){
			int id = Integer.parseInt(ids);
			User u = this.userManager.get(id);
			for(Shed shed : u.getSheds()){
				shed.getUsers().remove(u);
				this.shedManager.update(shed);
			}
			this.userManager.delete(id);
		}
		this.jsonString="{success:true,msg:'删除用户完成!'}";
		this.recordLog("删除用户信息", "用户ID:"+userInfo.getIds());
		return "delete";
	}
	
	/**
	 * 可编辑表格save或update
	 */
	public String saveOrupdate()
	{
		List<User> users=JsonUtil.getInstance().jsonToUsers(jsonString);
		int id;
		for(User user : users){
			id=user.getId();
			if(id!=0){
				userManager.update(user);
			}else{
				userManager.save(user);
			}
		}
		this.jsonString="{success:true,msg:'保存信息完成!'}";
		return "saveOrupdate";
	}
}