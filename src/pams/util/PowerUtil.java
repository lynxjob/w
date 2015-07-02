package pams.util;
/**
 * @author cjl
 * 菜单和按钮，权限管理
 */
public class PowerUtil 
{
	/**
	 * 具体菜单面板定义数组
	 */
	public static final String []menus={
		"{id:'Long.VideoMonitorPanel_videomonitorpanel',iconCls:'video_monitor_icon',text:'视频监测',leaf:true}",
		"{id:'Long.EnviroMonPanel_enviromonpanel',iconCls:'enviromon_icon',text:'环境监测',leaf:true}",
		"{id:'Long.MonitorPanel_monitorpanel',iconCls:'monitor_icon',text:'控制中心',leaf:true}",
		"{id:'Long.ExpertSystemPanel_expertsystempanel',iconCls:'expertsystem_icon',text:'LED控制',leaf:true}",
		"{id:'Long.QuerySystemPanel_querysystempanel',iconCls:'querysystem_icon',text:'查询平台',leaf:true}",
		"{id:'Long.ShedPanel_shedpanel',iconCls:'shed_icon',text:'大棚管理',leaf:true}",
		"{id:'Long.DeviceManagePanel_devicemanagepanel',iconCls:'device_icon',text:'设备管理',leaf:true}",
		"{id:'Long.CropGrid_cropgrid',iconCls:'assist_icon',text:'作物管理',leaf:true}",
		"{id:'Long.AssistSystemPanel_assistsystempanel',iconCls:'assist_icon',text:'辅助系统',leaf:true}",
		"{id:'Long.RoleGrid_rolegrid',iconCls:'role_icon',text:'角色信息',leaf:true}",
		"{id:'Long.UserPanel_userpanel',iconCls:'user_icon',text:'用户账户',leaf:true}",
		"{id:'Long.NoticeGrid_noticegrid',iconCls:'notice_icon',text:'系统公告',leaf:true}",
		"{id:'Long.LogGrid_loggrid',iconCls:'log_icon',text:'系统日志',leaf:true}",
		"{id:'Long.BackupGrid_backupgrid',iconCls:'db_icon',text:'数据备份',leaf:true}"
		}; 
	/**
	 * 导航菜单数组
	 */
	public static final String []navMenus = {
		"视频监测","环境监测","控制中心","LED控制","查询平台","大棚管理","设备管理","作物管理","辅助系统",
		"角色信息",
		"用户账户","系统公告","系统日志","数据备份"
	};
}