package pams.action;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;

import pams.service.Impl.BackupManager;

/**
 * @author cjl
 * 数据备份
 */
public class BackupAction extends BaseAction
{
	private static final long serialVersionUID = 1L;
	//静态的存放路径，存放sql备份文件
	private static String url=ServletActionContext.getServletContext().getRealPath("WEB-INF/data/db");
	//存放用于备份和还原系统的MySQL的exe
	private static String backexe=ServletActionContext.getServletContext().getRealPath("WEB-INF/data/mau");
	
	private BackupManager backupManager;
	private String name;
	private String inputPath;
	private String fileName;
    
	@Resource(name="backupManager")
	public void setBackupManager(BackupManager backupManager) 
	{
		this.backupManager = backupManager;
	}
    public void setInputPath(String value)
    {
        inputPath = value;
    }
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getFileName() 
	{
		return fileName;
	}
	public void setFileName(String fileName) 
	{
		this.fileName = fileName;
	}
	
    public InputStream getInputStream() throws Exception 
    {
    	this.inputPath=url+"\\"+name;
    	//System.err.println(inputPath);
    	return new FileInputStream(inputPath);
        //return ServletActionContext.getServletContext().getResourceAsStream(inputPath);
    }
	//调用此方法可以动态指定下载文件的类型
    public String getContentType()
    {
        return "application/sql";
    }
    
	/**
	 * 数据备份:罗列,备份,还原,下载,删除
	 */
	public String list()
	{
		this.jsonString=backupManager.list(url);
		return "list";
	}
	public String backup()
	{
		boolean temp=this.backupManager.backup(url,backexe);
		if(temp){
			this.jsonString="{success:true,msg:'备份系统当前数据完成!'}";
		}
		else{
			this.jsonString="{success:false,msg:'备份系统当前数据失败!'}";
		}
		return "backup";
	}
	public String restore()
	{
		boolean temp=this.backupManager.restore(url+"\\"+name,backexe);
		if(temp){
			this.jsonString="{success:true,msg:'还原系统数据完成!'}";
		}
		else{
			this.jsonString="{success:false,msg:'还原系统数据失败!'}";
		}
		return "restore";
	}
	public String download()
	{
		try
		{
			this.fileName = new String(this.name.getBytes("GBK"), "ISO8859_1");
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		//System.out.println(name);
		return "download";
	}
	public String delete()
	{
		boolean temp=backupManager.delete(url,name);
		if(temp){
			this.jsonString="{success:true,msg:'删除所选备份数据完成!'}";
		}
		else{
			this.jsonString="{success:false,msg:'备份文件不存在或已被删除!'}";
		}
		return "delete";
	}
}