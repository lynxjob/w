package pams.timer;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @see 
 * 自定义计时器基类
 * 与计时器相关的任务都可以继承
 * */
@SuppressWarnings("unused")
public abstract class CTimerTask  extends TimerTask {
	
	private Timer timer = new Timer();
	
	private long delay = 0L;
	
	private long period = 0L;
	/**
	 * 开启计时器任务
	 * @param delay
	 * @param period
	 */
	public void startTimer(long delay, long period)
	{
		this.delay = delay;
		this.period = period;
		timer.schedule(this, delay, period);
	}
	/**
	 * 停止计时器任务
	 */
	public void stopTimer()
	{
		this.getTimer().cancel();
	}
	/**
	 * 获取计时器
	 * @return
	 */
	public Timer getTimer() {
		return timer;
	}
	/**
	 * 设置计时器
	 * @param timer
	 */
	public void setTimer(Timer timer) {
		this.timer = timer;
	}
}

