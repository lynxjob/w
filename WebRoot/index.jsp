<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="pams.model.User" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
  <head>
    <title>精准农业自动化管理系统—主页</title>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/> 
	<link rel="stylesheet" type="text/css" href="ext/resources/css/ext-all.css"/>
	<link rel="stylesheet" type="text/css" href="css/icons.css"/>
	<link rel="stylesheet" type="text/css" href="css/style.css"/>
	<link rel="stylesheet" type="text/css" href="css/style.css"/>
	<link rel="stylesheet" type="text/css" href="ext/ux/css/Portal.css"/>
	<link rel="stylesheet" type="text/css" href="ext/ux/css/notification.css"/>
	<link rel="stylesheet" type="text/css" href="ext/ux/css/file-upload.css"/>
	<link rel="shortcut icon" href="images/favicon.ico" />
	<!-- SYSTEM -->
	<script type="text/javascript" src="ext/ext-base.js"></script>
	<script type="text/javascript" src="ext/ext-all.js"></script>
	<script type="text/javascript" src="ext/ext-lang-zh_CN.js"></script>
	<!-- UX -->
	<script type="text/javascript" src="ext/ux/Toast.js"></script>
	<script type="text/javascript" src="ext/ux/Portal.js"></script>
    <script type="text/javascript" src="ext/ux/PortalColumn.js"></script>
    <script type="text/javascript" src="ext/ux/Portlet.js"></script>
	<script type="text/javascript" src="ext/ux/CheckColumn.js"></script>
	<script type="text/javascript" src="ext/ux/TabCloseMenu.js"></script>
	<script type="text/javascript" src="ext/ux/SearchField.js"></script>
	<script type="text/javascript" src="ext/ux/Notification.js"></script>
	<script type="text/javascript" src="ext/ux/themeChange.js"></script>
	<!-- CORE -->
	<script type="text/javascript" src="js/core/core.js"></script>
	<script type="text/javascript" src="js/core/combo.js"></script>
	<script type="text/javascript" src="js/core/util.js"></script>
	<script type="text/javascript" src="js/core/import.js"></script>
	<script type="text/javascript" src="js/core/scriptmgr.js"></script>
	<script type="text/javascript" src="auth!loadButtons"></script>
	<script type="text/javascript" src="js/index.js"></script> 
	<!-- IE9.0 -->
	<script type="text/javascript">
		if ((typeof Range !== "undefined") && !Range.prototype.createContextualFragment) {  
			       Range.prototype.createContextualFragment = function (html) {  
			           var frag = document.createDocumentFragment(),  
			           div = document.createElement("div");  
			           frag.appendChild(div);  
			           div.outerHTML = html;  
			           return frag;  
			         };  
			     }  			
	</script>
	<!-- EXTRA -->
	<script type="text/javascript" for="HIKOBJECT1" event="SelectWindow()">
		ChangeStatus(1);
	</script>
	<script type="text/javascript" for="HIKOBJECT2" event="SelectWindow()">
		ChangeStatus(2);
	</script>
	<script type="text/javascript" for="HIKOBJECT3" event="SelectWindow()">
		ChangeStatus(3);
	</script>
	<script type="text/javascript" for="HIKOBJECT4" event="SelectWindow()">
		ChangeStatus(4);
	</script>
</head>
<body >
	<div id="top">
		<img src="images/logo.png" height="45">
		<span style="position: absolute;left: 58px;top: 4px;"><b><font color="#1877a9" size="3px">精准农业<br/>&nbsp;&nbsp;示范项目</font></b></span>
		<div id="top-name" >
			   <span style="font-weight: bold;font-size:16px;position: absolute;top: 5px;right: 600px;">精准农业示范项目</span>
			   <span style="font-size:13px;position: absolute;top: 3px;right: 10px;">
			    	<img src="images/icons/clock.png"/>&nbsp;<span id="rDate"></span>  <span id="rTime"></span>
			   </span> 
		 </div>			
		<span id="top-info">欢迎您,
			<b style="text-indent: 30px;"><%=((User)session.getAttribute("user")).getName() %></b> 【<a href="###" onclick="passwordReset()">修改密码</a>,<a href="###" onclick="logout()">退出</a>】
		</span>
	</div>
	<!--摄像头控件 -->
	 <div id="OCXBody">
        <div class="smallocxdiv" id="NetPlayOCX1">
          <object classid="CLSID:CAFCF48D-8E34-4490-8154-026191D73924" codebase="../codebase/NetVideoActiveX23.cab#version=2,3,19,1" id="HIKOBJECT1" width="100%" height="100%" name="HIKOBJECT1" ></object>
        </div>
        <div class="smallocxdiv" id="NetPlayOCX2">
          <object classid="CLSID:CAFCF48D-8E34-4490-8154-026191D73924" standby="Waiting..." id="HIKOBJECT2" width="100%" height="100%" name="HIKOBJECT2" ></object>
        </div>
        <div class="smallocxdiv" id="NetPlayOCX3">
          <object classid="CLSID:CAFCF48D-8E34-4490-8154-026191D73924" standby="Waiting..." id="HIKOBJECT3" width="100%" height="100%" name="HIKOBJECT3" ></object>
        </div>
        <div class="smallocxdiv" id="NetPlayOCX4">
          <object classid="CLSID:CAFCF48D-8E34-4490-8154-026191D73924" standby="Waiting..." id="HIKOBJECT4" width="100%" height="100%" name="HIKOBJECT4" ></object>
        </div>
      </div>
      <!-- 摄像头日志记录 -->
      <div id="log">
      </div>
      <!-- 环境监测数据记录 -->
      <div id="daqDataWin">
      </div>
      <!-- 控制中心异常信息记录 -->
      <div id="monitorLog"></div>
      <!-- 控制中心操作记录 -->
      <div id="monitorOper">
      </div>
  </body> 
</html>
