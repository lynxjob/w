package pams.init;

import java.util.Calendar;
import java.util.Date;
public class test {
   @SuppressWarnings("deprecation")
public static void main(String[] args) {
	   
	   Calendar c=Calendar.getInstance();
	System.out.println(c.get(Calendar.YEAR));
	System.out.println(c.get(Calendar.MONTH)+1);
	System.out.println(c.get(Calendar.DAY_OF_WEEK)+1);
}
}
