package util.test;

import org.junit.Test;


public class StrToInt {
	@Test
	public void strToint(){
		String strDatas = "12,13,14,15";
		for(String strData : strDatas.split(",")){
			int data = Integer.parseInt(strData);
			System.out.println(data);
		}
	}
}
