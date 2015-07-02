package pams.action;

import java.util.List;

import javax.annotation.Resource;

import pams.model.User;
import pams.service.Impl.UserManager;
import pams.vo.ManageInfo;

import com.opensymphony.xwork2.ModelDriven;
/**
 * @author cjl
 * 主界面
 */
public class ManageAction extends BaseAction implements ModelDriven<ManageInfo>
{
	private static final long serialVersionUID = 1L;
	private UserManager userManager;
	private ManageInfo manageInfo=new ManageInfo();
	private String userName;
	private String shedName;
	
	@Resource(name="userManager")
	public void setUserManager(UserManager userManager) 
	{
		this.userManager = userManager;
	}
	@Override
	public ManageInfo getModel()
	{
		return manageInfo;
	}
	
	public String getUserName() 
	{
		return userName;
	}
	public void setUserName(String userName) 
	{
		this.userName = userName;
	}
	public void setShedName(String shedName) {
		this.shedName = shedName;
	}
	public String getShedName() {
		return shedName;
	}
	
	/**
	 * 登录界面
	 */
	public String login()
	{
//		System.out.println(this.manageInfo.getUsername()+"  "+this.manageInfo.getPassword());
		User user=this.userManager.getUserByName(manageInfo.getUsername());
		if(user==null)
		{
			this.jsonString="{success:false,msg:'用户名不存在!'}";
		}
		else 
		{
			if(manageInfo.getPassword().equals(user.getPassword())){
				this.setSessionAtrribute("user",user);
				this.jsonString="{success:true,msg:'成功登陆,将跳转到管理主界面!'}";
			}
			else{
				this.jsonString="{success:false,msg:'密码不正确!'}";
			}
		}
		return "login";
	}
	
	public String passwordReset()
	{
		//System.out.println(this.manageInfo.getOldPassword()+" "+this.manageInfo.getNewPassword());
		
		User user = (User)this.getSessionAtrribute("user");
		if(manageInfo.getOldPassword().equals(user.getPassword())){
			user.setPassword(manageInfo.getNewPassword());
			this.userManager.update(user);
			this.jsonString="{success:true,msg:'修改密码完成!'}";
		}
		else{
			this.jsonString="{success:false,msg:'旧密码不正确!'}";
		}
		return "reset";
	}
	
	public String logout()
	{
		this.closeSession("user");
		this.jsonString="{success:true,msg:'退出系统!'}";
		return "logout";
	}
	@Override
	public String execute() throws Exception
	{
		String result=null;
		User user = (User)this.getSessionAtrribute("user");		
		if(user==null)
		{
			result="load";
		}
		else
		{
			this.userName=user.getName();
			List<String> menus=this.userManager.listMenus(user.getId());
			//无查看权限
			if(menus.size()==0)
			{
				this.closeSession("user");
				result="nopower";
			}
			else{
			System.out.println("问题");
				result="manage";
			}
			//System.out.println(name);
		}
		return result;
		//return "manage";
	}
	
}