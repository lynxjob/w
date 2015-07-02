package pams.web.form;


import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class XMLOper1 {
	private static XMLOper1 xmloper = null;
	private XMLOper1(){}
	
	public static XMLOper1 getInstance(){
		if(xmloper==null){
			xmloper =  new XMLOper1();
		}
		return xmloper;
	}
	public void createXml(String relay_status[],String dir) throws Exception {
		//实例化解析器  
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();   
		DocumentBuilder builder = factory.newDocumentBuilder(); 
		//创建Document对象 
		Document doc = builder.newDocument();
		//创建XML文件所需的各种对象并序列化  
		Element root = doc.createElement("root"); 
		Text relayStatusText;
		String[] flashStatus=new String[14];
		
		/*System.out.print("继电器状态");
		for(int j=0;j<16;j++){
			System.out.print(relay_status[j]);
		}
		System.out.println("");
		*/
		//把控制和动画不符合的状态调整过来
		/*String[]  temp=new String[4];
		temp[0]=relay_status[6];
		relay_status[6]=relay_status[9];
		relay_status[9]=temp[0];
		
		temp[1]=relay_status[14];
		relay_status[14]=relay_status[10];
		
		temp[2]=relay_status[15];
		relay_status[15]=relay_status[11];
		
		//temp[3]=relay_status[10];
		relay_status[10]=relay_status[12];
		
		relay_status[11]=relay_status[13];
		relay_status[12]=temp[1];
		relay_status[13]=temp[2];*/
		
		statustoFlash(relay_status,flashStatus);
		
		System.out.println("");
		System.out.print("动画状态");
		
		for(int i=1;i<14;i++)
		{
		//System.out.print(flashStatus[i]);
		Element relayStatus=doc.createElement("ddate");
		/*if(relay_status[i].equals("1"))
			relayStatusText=doc.createTextNode("0");
		else
			relayStatusText=doc.createTextNode("1");*/
		
		//Text relayStatusText=doc.createTextNode(relay_status[i]);
		relayStatusText=doc.createTextNode(flashStatus[i]);
		relayStatus.appendChild(relayStatusText);
		root.appendChild(relayStatus);
		}
		System.out.println("");
		doc.appendChild(root); 
		doc2XmlFile(doc,dir+"/"+"data.xml");
	}

	public void statustoFlash(String status[],String flashStatus[]){

		for(int i=1;i<14;i++){
			flashStatus[i]="0";
		}
		
		for(int i=1;i<4;i++)
		{
		if(status[2*(i-1)].equals("0"))
			flashStatus[i]="1";
		
		if(status[2*(i-1)+1].equals("0"))
			flashStatus[i]="0";
		}
		for(int j=4;j<16;j++)
		{
			if(status[j].equals("0")){
				flashStatus[j]="1";		
			}
			else
			{
				flashStatus[j]="0";	
			}
		}
		
		/*if(status[9].equals("0"))
			flashStatus[4]="1";
		else
			flashStatus[4]="0";
		
		if(status[7].equals("0"))
			flashStatus[5]="1";
		else
			flashStatus[5]="0";
		
		
		if(status[8].equals("0"))
			flashStatus[6]="1";
		else
			flashStatus[6]="0";
		
		if(status[6].equals("0"))
			flashStatus[7]="1";
		else
			flashStatus[7]="0";
		
		
		if(status[14].equals("0"))
			flashStatus[8]="1";
		else
			flashStatus[8]="0";
		
		if(status[15].equals("0"))
			flashStatus[9]="1";
		else
			flashStatus[9]="0";
		
		if(status[10].equals("0"))
			flashStatus[10]="1";
		else
			flashStatus[10]="0";
		
		
		if(status[11].equals("0"))
			flashStatus[11]="1";
		else
			flashStatus[11]="0";
		
		if(status[12].equals("0"))
			flashStatus[12]="1";
		else
			flashStatus[12]="0";
		
		if(status[13].equals("0"))
			flashStatus[13]="1";
		else
			flashStatus[13]="0";*/
		/*for(int i=1;i<14;i++){
			flashStatus[i]="0";
		}
			if(status[0].equals("0"))
				flashStatus[1]="1";
			if(status[1].equals("0"))
				flashStatus[1]="0";
			
			if(status[2].equals("0"))
				flashStatus[2]="1";
			if(status[3].equals("0"))
				flashStatus[2]="0";
			
			if(status[4].equals("0"))
				flashStatus[3]="1";
			if(status[5].equals("0"))
				flashStatus[3]="0";
			
			for(int j=6;j<16;j++){
				if(status[j].equals("0"))
					flashStatus[j-2]="1";
				else
					flashStatus[j-2]="0";
		    }*/
	}
	
	public boolean doc2XmlFile(Document document, String filename) {   
		boolean flag = true;   
		try {    
			TransformerFactory tFactory = TransformerFactory.newInstance();    
			Transformer transformer = tFactory.newTransformer();    /** 编码 */    
			// transformer.setOutputProperty(OutputKeys.ENCODING, "GB2312");    
			DOMSource source = new DOMSource(document); 
			File file = new File(filename);
			if(!file.exists()){
				file.createNewFile();
			}
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);  
			} 
		catch (Exception ex) 
			{    
			flag = false;    
			ex.printStackTrace();   
			}   
		return flag;
		}
	
}


