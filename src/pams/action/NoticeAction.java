package pams.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import pams.model.Notice;
import pams.model.User;
import pams.service.Impl.NoticeManager;
import pams.util.JsonUtil;
import pams.vo.NoticeInfo;

import com.opensymphony.xwork2.ModelDriven;
/**
 * @author cjl
 * 公告Action
 */
public class NoticeAction extends BaseAction implements ModelDriven<NoticeInfo>
{
	private static final long serialVersionUID = 1L;
	private NoticeManager noticeManager;
	private NoticeInfo noticeInfo=new NoticeInfo();
	
	@Resource(name="noticeManager")
	public void setNoticeManager(NoticeManager noticeManager) 
	{
		this.noticeManager = noticeManager;
	}

	@Override
	public NoticeInfo getModel() 
	{
		return noticeInfo;
	}
	
	public String list()
	{
		Long rowCount=this.noticeManager.getTotal();
		List<Notice> notices=this.noticeManager.list(noticeInfo.getStart(),noticeInfo.getLimit(),noticeInfo.getOrderBy());
		this.jsonString="{rowCount:"+rowCount+",result:"+JsonUtil.getInstance().objectToJson(Notice.class,notices)+"}";
		return "list";
	}
	
	public String save()
	{
		User user = (User)this.getSessionAtrribute("user");
		
		Notice notice=new Notice();
		notice.setTitle(noticeInfo.getTitle());
		notice.setContent(noticeInfo.getContent().substring(1));
		notice.setLevel(noticeInfo.getLevel());
		notice.setCdate(new Date());
		notice.setCreatorId(user.getId());
		notice.setCreatorName(user.getName());
		this.noticeManager.save(notice);
		this.jsonString="{success:true,msg:'添加系统公告完成！'}";
		return "save";
	}
	
	public String update()
	{
		Notice notice=this.noticeManager.get(this.noticeInfo.getId());
		notice.setTitle(noticeInfo.getTitle());
		notice.setContent(noticeInfo.getContent());
		notice.setLevel(noticeInfo.getLevel());
		this.noticeManager.update(notice);
		this.jsonString="{success:true,msg:'修改系统公告完成！'}";
		return "update";
	}
	
	public String delete()
	{
		this.noticeManager.delete(noticeInfo.getIds());
		this.jsonString="{success:true,msg:'删除系统公告完成！'}";
		return "delete";
	}
	
	public String get()
	{
		Notice notice=noticeManager.get(noticeInfo.getId());
		this.jsonString="{success:true,notice:"+JsonUtil.getInstance().noticeToJson(notice)+"}";
		return "get";
	}
}