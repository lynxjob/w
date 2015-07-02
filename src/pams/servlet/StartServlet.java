package pams.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import pams.socket.TcpManager;
import pams.socket.TcpServer;




public class StartServlet extends HttpServlet {

	private static final long serialVersionUID = -645247427681342469L;
	

	public void init() throws ServletException {
		
		
		//开启串口服务器-数据采集仪监听线程
		TcpServer daqServer = new TcpServer(5678);
		TcpServer realyServer = new TcpServer(5679);
		if(daqServer.getServer() != null)
		{
			new Thread(daqServer).start();
		}
		if(realyServer.getServer()!=null){
			new Thread(realyServer).start();
		}
		new Thread(new TcpManager()).start();
	}
}
