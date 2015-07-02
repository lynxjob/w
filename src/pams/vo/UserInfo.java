package pams.vo;
/**
 * @author cjl
 * 用户web传值
 */
public class UserInfo extends BaseInfo
{
	private String name;
	private String password;
	private Integer age;
	private String tel;
	private Integer state;
	private Integer shedId=0;
	private int[] shedIds;
	
	public String getName() 
	{
		return name;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	
	public String getPassword() 
	{
		return password;
	}
	public void setPassword(String password) 
	{
		this.password = password;
	}

	public Integer getAge() 
	{
		return age;
	}
	public void setAge(Integer age)
	{
		this.age = age;
	}

	public String getTel() 
	{
		return tel;
	}
	public void setTel(String tel)
	{
		this.tel = tel;
	}
	
	public Integer getState() 
	{
		return state;
	}
	public void setState(Integer state) 
	{
		this.state = state;
	}
	public void setShedId(Integer shedId) {
		this.shedId = shedId;
	}
	public Integer getShedId() {
		return shedId;
	}
	public void setShedIds(int[] shedIds) {
		this.shedIds = shedIds;
	}
	public int[] getShedIds() {
		return shedIds;
	}
	
}