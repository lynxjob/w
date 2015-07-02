package pams.vo;
/**
 * 大棚详细信息
 * @author 恶灵骑士
 *
 */
public class ShedInfo extends BaseInfo {
	private String name;
	private String des;
	private int shedTypeId=0;
	private int cropId;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public int getShedTypeId() {
		return shedTypeId;
	}
	public void setShedTypeId(int shedTypeId) {
		this.shedTypeId = shedTypeId;
	}
	public void setCropId(int cropId) {
		this.cropId = cropId;
	}
	public int getCropId() {
		return cropId;
	}
}
