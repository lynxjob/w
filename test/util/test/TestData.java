package util.test;

import java.util.Date;
import java.text.SimpleDateFormat;

public class TestData {
	public static void main(String[] args)  throws Exception{
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-M-d HH:mm:ss");
		Date date1=new Date();
		Date date2=sdf.parse("2013-1-24 00:00:00");
		System.out.println(jisuan(date1,date2));
	}
	public static int jisuan(Date date1, Date date2) throws Exception {
		long cha = date1.getTime() - date2.getTime();
		double result = cha * 1.0 / (1000 * 60);
		return (int)result;
	}
}
