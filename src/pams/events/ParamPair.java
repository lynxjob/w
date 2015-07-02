package pams.events;

public class ParamPair {
	
	private String name;
	//下发命令
	private int start;
	private int end;
	//返回命令
	private int rtStart;
	private int rtEnd;
	
	public ParamPair(){}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getRtStart() {
		return rtStart;
	}

	public void setRtStart(int rtStart) {
		this.rtStart = rtStart;
	}

	public int getRtEnd() {
		return rtEnd;
	}

	public void setRtEnd(int rtEnd) {
		this.rtEnd = rtEnd;
	}
	
}
