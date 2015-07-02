package pams.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pams.model.DaqData;
import pams.model.Device;
import pams.model.Factor;
import pams.model.Notice;
import pams.model.User;
import pams.web.form.RoleForm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
/**
 * @author cjl
 * gson 2.0
 * 单例,Object转Json，Json转Object
 */
public class JsonUtil
{
	private static JsonUtil jsonUtil=new JsonUtil();
	//Gson中日期格式为 yyyy-MM-dd HH:mm:ss
	private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	
	private JsonUtil()
	{	
	}
	
	public static JsonUtil getInstance()
	{
		return jsonUtil;
	}
	/**
	 *List对象转换为 json字符串
	 *通用object转json
	 */
	public <T> String objectToJson(Class<T> entityClass,List<T> objects)
	{
		Type type = new TypeToken<List<T>>(){}.getType();
		String jsonString = gson.toJson(objects,type);
		return jsonString;
	}
	
	/**
	 * json转List<User>
	 * 专用
	 */
	public List<User> jsonToUsers(String jsonString)
	{
		Type type = new TypeToken<List<User>>(){}.getType();
		List<User> users =gson.fromJson(jsonString,type);
		return users;
	}
	
	/**
	 * json转List<RoleForm>
	 * 专用
	 */
	public List<RoleForm> jsonToRoleForms(String jsonString)
	{
		Type type = new TypeToken<List<RoleForm>>(){}.getType();
		List<RoleForm> roles =gson.fromJson(jsonString,type);
		return roles;
	}
	/**
	 * json转List<Factor>
	 * @param jsonString
	 * @return
	 */
	public List<Factor> jsonToFactors(String jsonString){
		List<Factor> factors = new ArrayList<Factor>();
		String [] datas = jsonString.split("_");
		for(String data : datas){
			Factor factor = gson.fromJson(data, Factor.class);
			factors.add(factor);
		}
		return factors;
	}
	/**
	 * notice对象转化为json
	 * 专用
	 */
	public String noticeToJson(Notice notice)
	{
		return gson.toJson(notice);
	}
	/**
	 * 通用Object转json
	 */
	public String objectToJson(Object o){
		return gson.toJson(o);
	}
	/**
	 *Device转json
	*/
	public String deviceToJson(Device device){
		StringBuffer sb = new StringBuffer();
		if(device == null){
			return "";
		}
		sb.append("{success:true,data:{");
		if(device.getProperty1()!=null){
			sb.append("property1:'"+device.getProperty1()+"'");
		} 
		if(device.getProperty2()!=null){
			sb.append(",property2:'"+device.getProperty2()+"'");
		}
		if(device.getProperty3()!=null){
			sb.append(",property3:'"+device.getProperty3()+"'");
		}
		if(device.getProperty4()!=null){
			sb.append(",property4:'"+device.getProperty4()+"'");
		}
		sb.append("}}");
		return sb.toString();
	}
	/**
	 * enviro转json
	 */
	public String enviroToJson(DaqData daqData,Factor factor){
		System.out.println("JsonString解析开始...");
		if(factor==null){
			factor = new Factor();
			factor.setCoc(1);
			factor.setDadiwendu(1);
			factor.setDaqishidu(1);
			factor.setDaqiwendu(1);
			factor.setGuanghe(1);
			factor.setTurangshuifen(1);
		}
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("sampleDate:'"+DateUtil.getInstance().getDateString3(new Date()));
		sb.append("',coc:"+daqData.getCoc()+",fcoc:"+factor.getCoc());
		sb.append(",guanghe:"+daqData.getGuanghe()+",fguanghe:"+factor.getGuanghe());
		sb.append(",daqiwendu:"+(daqData.getDaqiwendu())+",fdaqiwendu:"+(factor.getDaqiwendu()));
		sb.append(",daqishidu:"+(daqData.getDaqishidu())+",fdaqishidu:"+(factor.getDaqishidu()));
		sb.append(",dadiwendu:"+daqData.getDadiwendu()+",fdadiwendu:"+factor.getDadiwendu());
		sb.append(",turangshuifen:"+daqData.getTurangshuifen()+",fturangshuifen:"+factor.getTurangshuifen());
		sb.append("}");
		System.out.println("JsonString解析结束："+sb.toString());
		return sb.toString();
	}
}