package pams.web.form;

public class LEDControl {
	static {
		System.out.println("");
		System.loadLibrary("ListenPlayDll");
	}

	public native static int AddProgram(int pno, int jno, int playTime);

	public native static int AddControl(int pno, int DBColor);

	public native static int SetSerialPortPara(int pno, int comno, int baud);

	public native static int AddLnTxtArea(int pno, int jno, int qno, int left,
			int top, int width, int height, String LnFileName, int PlayStyle,
			int Playspeed, int times);

	public native static int AddFileArea(int pno, int jno, int qno, int left,
			int top, int width, int height);

	public native static int AddFile(int pno, int jno, int qno, int mno,
			String fileName, int width, int height, int playstyle,
			int QuitStyle, int playspeed, int delay, int MidText);

	// ��ʱ
	public native static int AddTimerArea(int pno, int jno, int qno, int left,
			int top, int width, int height, int fontColor, String fontName,
			int fontSize, int fontBold, int mode, int format, int year,
			int week, int month, int day, int hour, int minute, int second);

	// ����ʱ��
	public native static int AddDClockArea(int pno, int jno, int qno, int left,
			int top, int width, int height, int fontColor, String fontName,
			int fontSize, int fontBold, int mode, int format, int spanMode,
			int hour, int minute);

	public native static int SetNetPara();

	public native static int SendControl(int PNO, int SendType, int hwd);

	public native static int SetOrderPara(int pno, String diskName);

	public native static int AddFileString(int pno, int jno, int qno, int mno,
			String str, String fontname, int fontsize, int fontcolor,
			boolean bold, boolean italic, boolean underline, int align,
			int width, int height, int playstyle, int QuitStyle, int playspeed,
			int delay, int MidText);

	public native static int SetTransMode(int TransMode, int ConType);

	public native static int SetNetworkPara(int pno, String ip);

	public native static void StartSend();

	public native static int SetProgramTimer(int pno, int jno, int TimingModel,
			int WeekSelect, int startSecond, int startMinute, int startHour,
			int startDay, int startMonth, int startWeek, int startYear,
			int endSecond, int endMinute, int endHour, int endDay,
			int endMonth, int endWeek, int endYear);

	public native static int AddLnTxtString(int pno, int jno, int qno,
			int left, int top, int width, int height, String text,
			String fontname, int fontsize, int fontcolor, boolean bold,
			boolean italic, boolean underline, int PlayStyle, int Playspeed,
			int times);

	public native static int AddQuitText(int pno, int jno, int qno, int left,
			int top, int width, int height, int FontColor, String fontName,
			int fontSize, boolean b, boolean c, boolean d, String text);

	public native static int AddDClockArea(int pno, int jno, int qno, int left,
			int top, int width, int height, int fontColor, String fontName,
			int fontSize, int fontBold, int Italic, int Underline, int year,
			int week, int month, int day, int hour, int minute, int second,
			int TwoOrFourYear, int HourShow, int format, int spanMode,
			int Advacehour, int Advaceminute);

	public native static int SetTest(int pno, int value);

	public native static int AdjustTime(int PNO);

	public native static int SetPower(int PNO, int power);

	public native static int SetHardPara(int PNO, int Sign, int Mirror,
			int ScanStyle, int LineOrder, int cls, int RGChange, int zhangKong);

	public native static int GetHardPara(int PNO, String FilePath);

	public native static int SearchController(String filePath, String IPAddress);

	public native static int ComSearchController(int PNO, int ComNo,
			int BaudRate, String filePath);

	public native static int SetRemoteNetwork(int pno, String macAddress,
			String ip, String gateway, String subnetmask);

	public native static int SetPowerTimer(int pno, int bTimer, int startHour1,
			int startMinute1, int endHour1, int endMinute1, int startHour2,
			int startMinute2, int endHour2, int endMinute2, int startHour3,
			int startMinute3, int endHour3, int endMinute3);

	public native static int SetBrightnessTimer(int pno, int bTimer,
			int startHour1, int startMinute1, int endHour1, int endMinute1,
			int brightness1, int startHour2, int startMinute2, int endHour2,
			int endMinute2, int brightness2, int startHour3, int startMinute3,
			int endHour3, int endMinute3, int brightness3);

	public native static int SendScreenPara(int pno, int DBColor, int width,
			int height);

	public native static int SetTimingLimit(int pno, int FSecond, int FMinute,
			int FHour, int FDay, int FMonth, int FWeek, int FYear);

	public native static int CancelTimingLimit(int pno);

	public native static int GenerateFile(int PNO, String buffer);

	public static void SendFileArea() {
		LEDControl jc = new LEDControl();
		jc.SetTransMode(3, 3);
		jc.SetSerialPortPara(1, 1, 9600);
		jc.StartSend();
		int j = jc.AddControl(1, 2);
		j = jc.AddProgram(1, 1, 0);
		String path = "c:\\1.rtf";
		jc.AddFileArea(1, 1, 1, 0, 0, 128, 32);
		jc.AddFile(1, 1, 1, 1, path, 320, 96, 32, 255, 20, 10, 1);
		jc.SendControl(1, 1, 0);
	}

	public static void SendQuietArea() {
		LEDControl jc = new LEDControl();
		jc.SetTransMode(3, 3);
		jc.SetSerialPortPara(1, 1, 9600);
		jc.StartSend();
		int j = jc.AddControl(1, 2);
		jc.AddProgram(1, 1, 0);
		String fontname = "����";
		String text = "ÿ������";
		jc.AddQuitText(1, 1, 1, 0, 0, 128, 32, 255, fontname, 24, false, false,
				false, text);
		jc.SendControl(1, 1, 0);
	}

	public static void SendMulTiText() {
		LEDControl jc = new LEDControl();
		jc.SetTransMode(3, 3);
		jc.SetSerialPortPara(1, 1, 9600);
		jc.StartSend();
		int j = jc.AddControl(1, 2);
		jc.AddProgram(1, 1, 0);
	    jc.AddFileArea(1, 1, 1, 0, 0, 128, 32);
		jc.AddFileString(1, 1, 1, 1, "��ֹ�ı�", "����", 12, 255, false, false,
				false, 1, 128, 32, 32, 255, 20, 1, 2);
		jc.SendControl(1, 1, 0);
	}

	public static void SendSingleLineText() {
		LEDControl jc = new LEDControl();
		jc.SetTransMode(3, 3);
		jc.SetSerialPortPara(1, 1, 9600);

		jc.StartSend();
		int j = jc.AddControl(1, 2);
		jc.AddProgram(1, 1, 0);
		jc.AddLnTxtString(1, 1, 1, 0, 0, 128, 32, "����ָ����", "����", 20, 255,
				false, false, false, 32, 10, 1);
		jc.SendControl(1, 1, 0);
	}

	public static void SendScreen() {
		LEDControl jc = new LEDControl();
		jc.SetTransMode(1, 3);
		//jc.SetSerialPortPara(1, 1, 9600);
		jc.SetNetworkPara(1, "192.168.3.99");
		jc.SendScreenPara(1, 2, 128, 32);
	}

	public static void main(String[] args) {
		/****
		 * ��������
		 **/

		//SendScreen();

		/*******
		 * 
		 * //�����ļ�
		 */
		// SendFileArea();

		/********* ���;�ֹ�ı� **********/
		// SendQuietArea();

		/********** �����ı� *************/
		// SendMulTiText();
		/********** ���͵����ı� ******************/
		 System.out.println("test");
		 SendSingleLineText();
		 System.out.println("test");
	}
}