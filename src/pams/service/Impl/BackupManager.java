package pams.service.Impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.stereotype.Component;

import pams.model.DbInfo;
import pams.util.DateUtil;
import pams.util.JsonUtil;

/**
 * @author cjl
 * 处理备份文件逻辑
 */
@Component("backupManager")
public class BackupManager 
{
	/**
	 * 私有方法，获取文件的大小
	 */
	private String getFileSize(File file)
	{
		String str = "0.000";
		if (file.isFile()) 
		{
			try 
			{
				RandomAccessFile raf = new RandomAccessFile(file, "r");
				Double d = (double) raf.length() / (double) (1024 * 1024);
				raf.close();
				DecimalFormat format = new DecimalFormat("#0.###");
				str = format.format(d);
			} 
			catch (Exception e) 
			{
			}
		}
		return str+" M";
	}
	
	/**
	 * WEB-INF/data/db 目录下获取备份文件
	 */
	public String list(String url)
	{
		List<DbInfo> infos=new ArrayList<DbInfo>();
		DbInfo info;
		File file=new File(url);
		File []files=file.listFiles();
		if(files == null || files.length == 0){
			return "{rowCount:0.result:[]}";
		}
		for(File temp : files)
		{
			info=new DbInfo();
			info.setName(temp.getName());
			info.setSize(this.getFileSize(temp));
			info.setCdate(DateUtil.getInstance().getFormatDate(temp.getName()));
			infos.add(info);
		}
		return "{rowCount:"+infos.size()+",result:"+JsonUtil.getInstance().objectToJson(DbInfo.class,infos)+"}";
	}
	
	/**
	 * 根据名字删除备份文件
	 */
	public boolean delete(String url,String name)
	{
		String path=url+"/"+name;
		File file=new File(path);
		if(!file.exists()){
			return false;
		}
		else{
			file.delete();
			return true;
		}
	}
	
	public static String getCommand(String type, String dbPath,String backexe)
	{
		InputStream inputStream = BackupManager.class.getClassLoader().getResourceAsStream("jdbc.properties");   
		  Properties p = new Properties();   
		  String host = "127.0.0.1";
		  String database = "pams";
		  String username = "root";
		  String password = "root";
		  try {   
		   p.load(inputStream);  
		   host = p.getProperty("jdbc.host");
		   database = p.getProperty("jdbc.database");
		   username = p.getProperty("jdbc.username");
		   password = p.getProperty("jdbc.password");
		  } catch (IOException e1) {   
		   e1.printStackTrace();   
		  }   
		return getCommand(type,host,database,username,password, dbPath,backexe);
	}
	
	public static String getCommand(String type, String host, String dbName,String userName, 
			String password, String dbPath,String backexe) 
	{
		String commandPath =backexe+"/" + type+ ".exe";
		StringBuilder sb = new StringBuilder();
		sb.append("cmd /c \"");
		sb.append(commandPath);
		sb.append("\" -h" + host);
		sb.append(" -u" + userName);
		sb.append(" -p" + password);
		sb.append(" " + dbName);
		sb.append(type.equals("mysqldump") ? " > " : " < ");
		sb.append(dbPath);
		return sb.toString();
	}
	/**
	 * 备份数据库
	 * 通过cmd命令行调用exe实现备份和还原
	 */
	public boolean backup(String url,String backexe)
	{
		File file = new File(url);
		if (!file.exists()) {
			file.mkdirs();
		}
		String str = getCommand("mysqldump", url + "\\" +DateUtil.getInstance().getFormateName()+".sql",backexe);
//		System.out.println("备份数据库："+str);
		try 
		{
			Process p = Runtime.getRuntime().exec(str);
			int i = p.waitFor();
			if (i == 0) 
			{
				return true;
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 还原数据库
	 */
	public boolean restore(String path,String backexe)
	{
		String str = getCommand("mysql",path,backexe);
//		System.out.println("还原数据库："+str);
		try 
		{
			Process p = Runtime.getRuntime().exec(str);
			int i = p.waitFor();
			if (i == 0) 
			{
				return true;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
}