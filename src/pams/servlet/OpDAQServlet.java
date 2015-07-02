package pams.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pams.socket.service.TcpCommunictor;



public class OpDAQServlet extends HttpServlet {
	
	/**
	 *查询采集仪数据
	 *DAQ数据采集
	 */
	private static final long serialVersionUID = 8943270120233475257L;
	
	public static TcpCommunictor cmnct = new TcpCommunictor();
	
	public void doGet(
			HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		doPost(request,response);
	}
	
	public void doPost(
			HttpServletRequest request,
			HttpServletResponse response) throws IOException{
	
		String srouter = request.getParameter("router");
		int router = Integer.valueOf(srouter);
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String rs = null;
//		DAQData rDatas = null;
		if(router == 13)
		{
			cmnct.stopTimer();
		}else if(router == 12)
		{
			cmnct.startTimer();
		}else{
			float jyl = (float) (Math.random()*100);
			float dqwd = (float)(Math.random()*100);
			float gz = (float)(Math.random()*100);
			rs = "[{降雨量:"+jyl+",大气温度:"+dqwd+",光照强度:"+gz+"}]";
			
			/*if(TcpManager.clientBuffer.getClient("daq") == null)
			{
				rs="采集仪串口未开启!";
			}else if(cmnct.getRdt() != null)
			{
				rDatas = (DAQData)cmnct.getRdt().daqBuffer.getLastData();
			    rs = "[{jyl:"+rDatas.getJiangyu()+",dqwd:"+rDatas.getDaqiwendu()+",gz:"+rDatas.getGuanghe()+"}]";
			} else {
				rs = "定时器没有开启!";
			}*/
		}
		out.println(rs);
		out.flush();
		out.close();
	}

}
