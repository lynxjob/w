package pams.action;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;

import pams.model.Log;
import pams.model.User;
import pams.service.Impl.LogManager;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
/**
 * @author cjl
 * 公用父Action
 */
public class BaseAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;
	protected String jsonString;
	protected LogManager logManager;
	
	/**
	 * 操作返回参数
	 */
	public String getJsonString()
	{
		return jsonString;
	}
	public void setJsonString(String jsonString)
	{
		this.jsonString = jsonString;
	}
	
	/**
	 *注入日志记录Manager
	 */
	@Resource(name="logManager")
	public void setLogManager(LogManager logManager) 
	{
		this.logManager = logManager;
	}
	
	/**
	 * 获取Servlet Session对象
	 */
	public Map<String,Object> getSession()
	{
		ActionContext actionContext = ActionContext.getContext();
	    Map<String,Object> session = actionContext.getSession();
	    return session;
	}
	/**
	 * 对session取数据
	 */
	public Object getSessionAtrribute(String key)
	{
		return this.getSession().get(key);
	}
	/**
	 * 对session存数据
	 */
	public void setSessionAtrribute(String key, Object value) 
	{
		this.getSession().put(key,value);
	}
	/**
	 * 关闭session
	 */
	public void closeSession(String key)
	{
		this.getSession().clear();
	}
	
	/**
	 * 获取访问者IP
	 */
	public String getIpAddr()
	{
		return ServletActionContext.getRequest().getRemoteAddr();
	}
	
	/**
	 * 记录日志
	 */
	public void recordLog(String oType,String detail)
	{
		User user = (User)this.getSessionAtrribute("user");
		
		Log log=new Log();
		log.setUserName(user.getName());
		log.setIp(this.getIpAddr());
		log.setoType(oType);
		log.setoDetail(detail);
		this.logManager.save(log);
	}
}