package pams.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;
/**
 * 图片文件上传
 * @author 恶灵骑士
 *
 */
public class UploadAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	//上传文件属性字段,用File类型封装
	private File upload;
	//struts2中要求定义文件字段+FileName和+ContentType的两个字段来封装
	//文件名和文件类型,并且struts File标签中已封装相应属性(xxx->xxxFileName->xxxContentType)
	private String uploadFileName;
	private String uploadContentType;
	private String savePath;
	private String jsonString;
	
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public String getUploadContentType() {
		return uploadContentType;
	}
	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	@SuppressWarnings("deprecation")
	public String getSavePath() {
		return ServletActionContext.getRequest().getRealPath(savePath);
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	
	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}
	public String getJsonString() {
		return jsonString;
	}
	public String upload(){
		FileOutputStream fos=null;
		FileInputStream fis=null;
		try {
			//以服务器的文件保存地址和原文件的名称建立上传文件输出流
			fos = new FileOutputStream(this.getSavePath()+"\\"+this.getUploadFileName());
			//以上传文件建立一个文件上传输入流
			fis = new FileInputStream(this.getUpload());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//将上传文件内容写入服务器
		byte []buffer = new byte[1024];
		int len=0;
		try {
			while((len=fis.read(buffer))>0){
				fos.write(buffer,0,len);
			}
			System.out.println("--------------文件上传完毕!-----------------");
			this.jsonString = "{success:true,url:'images/upload/"+uploadFileName+"'}";
			System.out.println(this.jsonString);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("--------------文件上传失败!-----------------");
			this.jsonString="{success:false,msg:'文件上传失败!'}";
		}
		return "upload";
	}

}
