package pams.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pams.events.AbstractEvent;
import pams.events.RelayEvent;
import pams.socket.TcpManager;
import pams.socket.service.TcpCommunictor;






public class RelayServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3574776924558995726L;
	
	public static TcpCommunictor cmnct = new TcpCommunictor();
	
	public void doGet(
			HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		doPost(request,response);
	}
	
	public void doPost(
			HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String id = request.getParameter("id");
		String status = request.getParameter("status");
		String type = request.getParameter("type");
		
		PrintWriter out = response.getWriter();
		String rs = null;
		/*if(cps.getComm().isOpen())
		{
			AbstraceCommEvent relayEvent = new RelayEvent(type, id, status);
			cps.writePort(relayEvent);
			byte[] rData = cps.readPort();
			rs = (String)relayEvent.resolveData(rData);
		}else {
			JSONStringer jsonstringer = new JSONStringer();
			try {
				jsonstringer.object()
					        .key("rs")
					        .value("close")
					        .endObject();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			rs = jsonstringer.toString();
			
		}*/
		if(TcpManager.clientBuffer.getClient("relay") != null)
		{
			AbstractEvent relayEvent = new RelayEvent(type, id, status);
			byte[] rData = cmnct.getData("relay", relayEvent);
			rs = (String)relayEvent.resolveData(rData);
		}else {
			rs = "{rs:'close'}";			
		}
		out.println(rs);
		out.flush();
		out.close();
	}

}
