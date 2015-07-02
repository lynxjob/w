package pams.action;

import java.util.List;

import javax.annotation.Resource;

import pams.model.Log;
import pams.service.Impl.LogManager;
import pams.util.JsonUtil;
import pams.vo.LogInfo;

import com.opensymphony.xwork2.ModelDriven;

/**
 * @author cjl
 * 日志信息Action
 */
public class LogAction extends BaseAction implements ModelDriven<LogInfo>
{
	private static final long serialVersionUID = 1L;
	private LogManager logManager;
	private LogInfo logInfo=new LogInfo();
	
	@Resource(name="logManager")
	public void setLogManager(LogManager logManager) 
	{
		this.logManager = logManager;
	}
	@Override
	public LogInfo getModel()
	{
		return logInfo;
	}


	/**
	 * 列出日志信息
	 */
	public String list()
	{
		Long rowCount=this.logManager.getTotal();
		List<Log> logs=this.logManager.list(logInfo.getStart(),logInfo.getLimit());
		this.jsonString="{rowCount:"+rowCount+",result:"+JsonUtil.getInstance().objectToJson(Log.class,logs)+"}";
		return "list";
	}
}