package pams.socket;

public class TcpManager implements Runnable {
	private boolean isRunning = true;
	public static ClientBuffer clientBuffer = new ClientBuffer();
	
	public void run() {
		
		while(isRunning)
		{
			clientBuffer.checkClients();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public boolean isRunning() {
		return isRunning;
	}

}
