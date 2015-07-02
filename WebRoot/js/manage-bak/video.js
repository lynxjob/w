Ext.namespace('Long');
//视频监测

//全局变量定义
var m_iNowChanNo = -1;                           //当前通道号
var m_iLoginUserId = -1;                         //注册设备用户ID
var m_iChannelNum = -1;                          //模拟通道总数
var m_bDVRControl = null;                        //OCX控件对象
var m_iProtocolType = 0;                         //协议类型，0 – TCP， 1 - UDP
var m_iStreamType = 0;                           //码流类型，0 表示主码流， 1 表示子码流
var m_iPlay = 0;                                 //当前是否正在预览
var m_iRecord = 0;                               //当前是否正在录像
var m_iTalk = 0;                                 //当前是否正在对讲 
var m_iVoice = 0;                                //当前是否打开声音
var m_iAutoPTZ = 0;                              //当前云台是否正在自转
var m_iPTZSpeed = 4;                             //云台速度
var a_data = [];                                   //通道列表框数据集合
/*************************************************
  Function:     LogMessage
  Description:  写执行结果日志
  Input:        msg:日志
  Output:       无
  Return:       无
*************************************************/
var LogMessage = function(msg)
{
    var myDate = new Date(); 
    var szNowTime =  myDate.format('Y-m-d H:i:s');
    Ext.getDom('log').innerHTML = szNowTime + " --> " + msg + "<br>" + Ext.getDom('log').innerHTML; 
};
/*************************************************
Function:       ArrangeWindow
Description:    画面分割为几个窗口
Input:          x : 窗口数目            
Output:         无
return:         无               
*************************************************/
function ArrangeWindow(x)
{
    var iMaxWidth = document.getElementById("OCXBody").offsetWidth;
    var iMaxHeight = document.getElementById("OCXBody").offsetHeight;
    for(var i = 1; i <= 4; i ++)
    {
        if(i <= x)
        {
            document.getElementById("NetPlayOCX" + i).style.display = "";
        }
        else
        {
            document.getElementById("NetPlayOCX" + i).style.display = "none";   
        }
    }
    var d = Math.sqrt(x);
    var iWidth = iMaxWidth/d-5;
    var iHeight = iMaxHeight/d-5;
    for(var j = 1; j <= x; j ++)
    {
        document.getElementById("NetPlayOCX" + j).style.width = iWidth;
        document.getElementById("NetPlayOCX" + j).style.height = iHeight;
    }
}
/*************************************************
Function:       ChangeStatus
Description:    选中窗口时，相应通道的状态显示
Input:          iWindowNum :    选中窗口号       
Output:         无
return:         无               
*************************************************/
function ChangeStatus(iWindowNum)
{  
    m_bDVRControl = document.getElementById("HIKOBJECT" + iWindowNum);
    for(var i = 1; i <= 4; i ++)
    {
        if(i == iWindowNum)
        {
            document.getElementById("NetPlayOCX" + i).style.border = "1px solid #00F";
        }
        else
        {
            document.getElementById("NetPlayOCX" + i).style.border = "1px solid #EBEBEB";   
        }
    }
    LogMessage("当前选中窗口" + iWindowNum);
}
/*****************************
 * 按键处理函数
 * @param {} sKey
 ****************************/
var ButtonPress = function(sKey)
{
    try
    {
        switch (sKey)
        {
            case "getDevName":
            {
                if(m_iLoginUserId > -1){
                    var szDecName = m_bDVRControl.GetServerName();
	                if(szDecName == "")
	                {
	                    LogMessage("获取名称失败!");
	                    szDecName = "Embedded Net DVR"; 
	                }
	                else
	                {
	                    LogMessage("获取名称成功!");  
	                }
	                Ext.getCmp('DeviceName').setValue(szDecName);
                }
                else
                {
                    LogMessage("请注册设备!");
                }
                break;
            }
            case "getDevChan":
            {
                if(m_iLoginUserId > -1){
	                szServerInfo = m_bDVRControl.GetServerInfo();
	                var xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
	                xmlDoc.async="false"
	                xmlDoc.loadXML(szServerInfo)
	                m_iChannelNum = parseInt(xmlDoc.documentElement.childNodes[0].childNodes[0].nodeValue);
	                if(m_iChannelNum < 1)
	                {
	                    LogMessage("获取通道失败!");
	                }
	                else
	                {
	                    LogMessage("获取通道成功!"); 
	                    a_data.length = 0; //先清空下拉列表
	                    for(var i = 0; i < m_iChannelNum; i ++)
	                    {
	                        var szChannelName = m_bDVRControl.GetChannelName(i);
	                        if(szChannelName == "")
	                        {
	                            szChannelName = "通道" + (i + 1);
	                        }
	                        a_data.push(
	                        [
	                          i,
	                          szChannelName
	                        ]  
	                        );
	                    }
	                   Ext.getCmp('ChannelList').getStore().loadData(a_data);
	                }
                }
                else
                {
                    LogMessage("请注册设备!");
                }
                break;
            }
            case "Preview:start":
            {
                m_iNowChanNo = Ext.getCmp('ChannelList').value;
                if(m_iNowChanNo > -1)
                {
                    if(m_iPlay == 1)
                    {
                        m_bDVRControl.StopRealPlay();
                    }
                    var bRet = m_bDVRControl.StartRealPlay(m_iNowChanNo,m_iProtocolType,m_iStreamType);
                    if(bRet)
                    {
                        LogMessage("预览通道"+(m_iNowChanNo + 1) +"成功!");
                        m_iPlay = 1;
                    }
                    else
                    {
                        LogMessage("预览通道"+(m_iNowChanNo + 1) +"失败!");
                    }
                }
                else
                {
                    LogMessage("请选择通道号!");  
                }
                break;
            }
            case "Preview:stop":
            {
                
                if(m_bDVRControl.StopRealPlay())
                {
                    LogMessage("停止预览成功!");
                    m_iPlay = 0;
                }
                else
                {
                    LogMessage("停止预览失败!");
                }
                break;
            }
            case "CatPic:bmp":
            {
                if(m_iPlay == 1)
                {
                    if(m_bDVRControl.BMPCapturePicture("C:/OCXBMPCaptureFiles",1))
                    {
                        LogMessage("抓BMP图成功!");
                    }
                    else
                    {
                        LogMessage("抓BMP图失败!");
                    }
                }
                else
                {
                    LogMessage("请先预览!");
                }
                break;
            }
            case "CatPic:jpeg":
            {
                if(m_iPlay == 1)
                {
                    if(m_bDVRControl.JPEGCapturePicture((m_iNowChanNo + 1),1,0,"C:/OCXJPEGCaptureFiles",1))
                    {
                        LogMessage("抓JPEG图成功!");
                    }
                    else
                    {
                        LogMessage("抓JPEG图失败!");
                    }
                }
                else
                {
                    LogMessage("请先预览!");
                }
                break;
            }
            case "Record:start":
            {
                if(m_iPlay == 1)
                {
                    if(m_iRecord == 0)
                    {
                        if(m_bDVRControl.StartRecord("C:/OCXRecordFiles"))
                        {
                            LogMessage("开始录像成功!");
                            m_iRecord = 1;
                        }
                        else
                        {
                            LogMessage("开始录像失败!");
                        }
                    }
                }
                else
                {
                    LogMessage("请先预览!");
                }
                break;
            }
            case "Record:stop":
            {
                if(m_iRecord == 1)
                {
                    if(m_bDVRControl.StopRecord(1))
                    {
                        LogMessage("停止录像成功!");
                        m_iRecord = 0;
                    }
                    else
                    {
                        LogMessage("停止录像失败!");
                    }
                }
                break;
            }
            case "talk:start":
            {
                if(m_iLoginUserId > -1)
                {
                    if(m_iTalk == 0)
                    {
                        if(m_bDVRControl.StartTalk(1))
                        {
                            LogMessage("开始对讲成功!");
                            m_iTalk = 1;
                        }
                        else
                        {
                            LogMessage("开始对讲失败!");
                        }
                    }
                }
                else
                {
                    LogMessage("请注册设备!");
                }
                break;
            }
            case "talk:stop":
            {
                if(m_iTalk == 1)
                {
                    if(m_bDVRControl.StopTalk())
                    {
                        LogMessage("停止对讲成功!");
                        m_iTalk = 0;
                    }
                    else
                    {
                        LogMessage("停止对讲失败!");
                    }
                }
                break;
            }
            case "voice:start":
            {
                if(m_iPlay == 1)
                {
                    if(m_iVoice == 0)
                    {
                        if(m_bDVRControl.OpenSound(1))
                        {
                            LogMessage("打开声音成功!");
                            m_iVoice = 1;
                        }
                        else
                        {
                            LogMessage("打开声音失败!");
                        }
                    }
                }
                else
                {
                    LogMessage("请先预览!");
                }
                break;
            }
            case "voice:stop":
            {
                if(m_iVoice == 1)
                {
                    if(m_bDVRControl.CloseSound(1))
                    {
                        LogMessage("关闭声音成功!");
                        m_iVoice = 0;
                    }
                    else
                    {
                        LogMessage("关闭声音失败!");
                    }
                }
                break;
            }
            case "PTZ:stop":
            {
                if(m_iPlay == 1)
                {
                    if(m_bDVRControl.PTZCtrlStop(10,m_iPTZSpeed))
                    {
                        LogMessage("停止PTZ成功!");
                        m_iAutoPTZ = 0;
                    }
                    else
                    {
                        LogMessage("停止PTZ失败!");
                    }
                }
                break;
            }
            case "PTZ:leftup":
            {
                if(m_iPlay == 1)
                {
                    if(m_iAutoPTZ == 1)
                    {
                        m_bDVRControl.PTZCtrlStop(10,m_iPTZSpeed);
                        m_iAutoPTZ = 0;
                    }
                    if(m_bDVRControl.PTZCtrlStart(13,m_iPTZSpeed))
                    {
                        LogMessage("PTZ左上成功!");
                    }
                    else
                    {
                        LogMessage("PTZ左上失败!");
                    }
                }
                else
                {
                    LogMessage("请先预览!");
                }
                break;
            }   
            case "PTZ:rightup":
            {
                if(m_iPlay == 1)
                {
                    if(m_iAutoPTZ == 1)
                    {
                        m_bDVRControl.PTZCtrlStop(10,m_iPTZSpeed);
                        m_iAutoPTZ = 0;
                    }
                    if(m_bDVRControl.PTZCtrlStart(14,m_iPTZSpeed))
                    {
                        LogMessage("PTZ右上成功!");
                    }
                    else
                    {
                        LogMessage("PTZ右上失败!");
                    }
                }
                else
                {
                    LogMessage("请先预览!");
                }
                break;
            }   
            case "PTZ:up":
            {
                if(m_iPlay == 1)
                {
                    if(m_iAutoPTZ == 1)
                    {
                        m_bDVRControl.PTZCtrlStop(10,m_iPTZSpeed);
                        m_iAutoPTZ = 0;
                    }
                    if(m_bDVRControl.PTZCtrlStart(0,m_iPTZSpeed))
                    {
                        LogMessage("PTZ上成功!");
                    }
                    else
                    {
                        LogMessage("PTZ上失败!");
                    }
                }
                else
                {
                    LogMessage("请先预览!");
                }
                break;
            }   
            case "PTZ:left":
            {
                if(m_iPlay == 1)
                {
                    if(m_iAutoPTZ == 1)
                    {
                        m_bDVRControl.PTZCtrlStop(10,m_iPTZSpeed);
                        m_iAutoPTZ = 0;
                    }
                    if(m_bDVRControl.PTZCtrlStart(2,m_iPTZSpeed))
                    {
                        LogMessage("PTZ向左成功!");
                    }
                    else
                    {
                        LogMessage("PTZ向左失败!");
                    }
                }
                else
                {
                    LogMessage("请先预览!");
                }
                break;
            }   
            case "PTZ:right":
            {
                if(m_iPlay == 1)
                {
                    if(m_iAutoPTZ == 1)
                    {
                        m_bDVRControl.PTZCtrlStop(10,m_iPTZSpeed);
                        m_iAutoPTZ = 0;
                    }
                    if(m_bDVRControl.PTZCtrlStart(3,m_iPTZSpeed))
                    {
                        LogMessage("PTZ向右成功!");
                    }
                    else
                    {
                        LogMessage("PTZ向右失败!");
                    }
                }
                else
                {
                    LogMessage("请先预览!");
                }
                break;
            }   
            case "PTZ:rightdown":
            {
                if(m_iPlay == 1)
                {
                    if(m_iAutoPTZ == 1)
                    {
                        m_bDVRControl.PTZCtrlStop(10,m_iPTZSpeed);
                        m_iAutoPTZ = 0;
                    }
                    if(m_bDVRControl.PTZCtrlStart(16,m_iPTZSpeed))
                    {
                        LogMessage("PTZ右下成功!");
                    }
                    else
                    {
                        LogMessage("PTZ右下失败!");
                    }
                }
                else
                {
                    LogMessage("请先预览!");
                }
                break;
            }   
            case "PTZ:leftdown":
            {
                if(m_iPlay == 1)
                {
                    if(m_iAutoPTZ == 1)
                    {
                        m_bDVRControl.PTZCtrlStop(10,m_iPTZSpeed);
                        m_iAutoPTZ = 0;
                    }
                    if(m_bDVRControl.PTZCtrlStart(15,m_iPTZSpeed))
                    {
                        LogMessage("PTZ左下成功!");
                    }
                    else
                    {
                        LogMessage("PTZ左下失败!");
                    }
                }
                else
                {
                    LogMessage("请先预览!");
                }
                break;
            }   
            case "PTZ:down":
            {
                if(m_iPlay == 1)
                {
                    if(m_iAutoPTZ == 1)
                    {
                        m_bDVRControl.PTZCtrlStop(10,m_iPTZSpeed);
                        m_iAutoPTZ = 0;
                    }
                    if(m_bDVRControl.PTZCtrlStart(1,m_iPTZSpeed))
                    {
                        LogMessage("PTZ向下成功!");
                    }
                    else
                    {
                        LogMessage("PTZ向下失败!");
                    }
                }
                else
                {
                    LogMessage("请先预览!");
                }
                break;
            }   
            case "zoom:in":
            {
                if(m_iPlay == 1)
                {
                    if(m_iAutoPTZ == 1)
                    {
                        m_bDVRControl.PTZCtrlStop(10,m_iPTZSpeed);
                        m_iAutoPTZ = 0;
                    }
                    if(m_bDVRControl.PTZCtrlStart(4,m_iPTZSpeed))
                    {
                        LogMessage("焦距拉近成功!");
                    }
                    else
                    {
                        LogMessage("焦距拉近失败!");
                    }
                }
                else
                {
                    LogMessage("请先预览!");
                }
                break;
            }   
            case "zoom:out":
            {
                if(m_iPlay == 1)
                {
                    if(m_iAutoPTZ == 1)
                    {
                        m_bDVRControl.PTZCtrlStop(10,m_iPTZSpeed);
                        m_iAutoPTZ = 0;
                    }
                    if(m_bDVRControl.PTZCtrlStart(5,m_iPTZSpeed))
                    {
                        LogMessage("焦距拉远成功!");
                    }
                    else
                    {
                        LogMessage("焦距拉远失败!");
                    }
                }
                else
                {
                    LogMessage("请先预览!");
                }
                break;
            }
            case "focus:in":
            {
                if(m_iPlay == 1)
                {
                    if(m_iAutoPTZ == 1)
                    {
                        m_bDVRControl.PTZCtrlStop(10,m_iPTZSpeed);
                        m_iAutoPTZ = 0;
                    }
                    if(m_bDVRControl.PTZCtrlStart(6,m_iPTZSpeed))
                    {
                        LogMessage("聚焦拉近成功!");
                    }
                    else
                    {
                        LogMessage("聚焦拉近失败!");
                    }
                }
                else
                {
                    LogMessage("请先预览!");
                }
                break;
            }   
            case "focus:out":
            {
                if(m_iPlay == 1)
                {
                    if(m_iAutoPTZ == 1)
                    {
                        m_bDVRControl.PTZCtrlStop(10,m_iPTZSpeed);
                        m_iAutoPTZ = 0;
                    }
                    if(m_bDVRControl.PTZCtrlStart(7,m_iPTZSpeed))
                    {
                        LogMessage("聚焦拉远成功!");
                    }
                    else
                    {
                        LogMessage("聚焦拉远失败!");
                    }
                }
                else
                {
                    LogMessage("请先预览!");
                }
                break;
            }
            case "iris:in":
            {
                if(m_iPlay == 1)
                {
                    if(m_iAutoPTZ == 1)
                    {
                        m_bDVRControl.PTZCtrlStop(10,m_iPTZSpeed);
                        m_iAutoPTZ = 0;
                    }
                    if(m_bDVRControl.PTZCtrlStart(8,m_iPTZSpeed))
                    {
                        LogMessage("光圈大成功!");
                    }
                    else
                    {
                        LogMessage("光圈大失败!");
                    }
                }
                else
                {
                    LogMessage("请先预览!");
                }
                break;
            }   
            case "iris:out":
            {
                if(m_iPlay == 1)
                {
                    if(m_iAutoPTZ == 1)
                    {
                        m_bDVRControl.PTZCtrlStop(10,m_iPTZSpeed);
                        m_iAutoPTZ = 0;
                    }
                    if(m_bDVRControl.PTZCtrlStart(9,m_iPTZSpeed))
                    {
                        LogMessage("光圈小成功!");
                    }
                    else
                    {
                        LogMessage("光圈小失败!");
                    }
                }
                else
                {
                    LogMessage("请先预览!");
                }
                break;
            }
            case "getImagePar":
            {
                if(m_iPlay == 1)
                {
                    var szXmlInfo = m_bDVRControl.GetVideoEffect();
                    if(szXmlInfo != "")
                    {
                        var xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
                        xmlDoc.async="false"
                        xmlDoc.loadXML(szXmlInfo)   
                        Ext.getCmp("PicLight").setValue(xmlDoc.documentElement.childNodes[0].childNodes[0].nodeValue);
                        Ext.getCmp("PicContrast").setValue(xmlDoc.documentElement.childNodes[1].childNodes[0].nodeValue);
                        Ext.getCmp("PicSaturation").setValue(xmlDoc.documentElement.childNodes[2].childNodes[0].nodeValue);
                        Ext.getCmp("PicTonal").setValue(xmlDoc.documentElement.childNodes[3].childNodes[0].nodeValue);
                        LogMessage("获取图像参数成功!");
                    }
                    else
                    {
                        LogMessage("获取图像参数失败!");
                    }
                }
                else
                {
                    LogMessage("请先预览!");
                }
                break;
            }       
            case "setImagePar":
            {
                if(m_iPlay == 1)
                {
                    var iL = parseInt(Ext.getCmp("PicLight").value);
                    var iC = parseInt(Ext.getCmp("PicContrast").value);
                    var iS = parseInt(Ext.getCmp("PicSaturation").value);
                    var iT = parseInt(Ext.getCmp("PicTonal").value);
                    var bRet = m_bDVRControl.SetVideoEffect(iL,iC,iS,iT);
                    if(bRet)
                    {
                        LogMessage("设置图像参数成功!");
                    }
                    else
                    {
                        LogMessage("设置图像参数失败!");
                    }
                }
                else
                {
                    LogMessage("请先预览!");
                }
                break;
            }         
            default:
            {
                //Record:start   setPreset
                break;
            }
        }       //switch  
    }
    catch(err)
    {
        alert(err);
    }
}
/*************************************************
 * 视频监控类
 * @class Long.VideoMonitorPanel
 * @extends Ext.Panel
 ************************************************/
Long.VideoMonitorPanel = Ext.extend(Ext.Panel,{
    id:'videomonitorpanel',
    title:'视频监测',
    iconCls:'video_monitor_icon',
    initComponent : function(){    
    //视频日志显示区
    this.accordion= new Ext.Panel({
          region:'center',
          layout:'border',
          items:[
          {
            id:'videomonitor',
            title:'监控界面',
            region:'center',
            bodyStyle:'padding:8px 20px;background:#999',
            autoScroll:true,
            contentEl:'OCXBody'
          },
          {
            id:'logshow', 
            title:'日志(运行结果)',
            region:'south',
            height:130,
            autoScroll:true,
            contentEl:'log'
          }
          ]
      })
    var combox_store=new Ext.data.ArrayStore({
             fields:['channel_id','channel_name'],
             data:a_data
        })
    var img_store = new Ext.data.ArrayStore({
        fields:['id'],
        data:[['1'],['2'],['3'],['4'],['5'],['6'],['7'],['8'],['9'],['10']]
    })
   //主控区
   this.accordion1= new Ext.Panel({
      title:'控制平台',
      region:'east',
      width:350,
      autoScroll:true,
      layout:'vbox',
      bodyStyle:'padding:10px 0 5px 15px;',
      items:[{
      xtype:'fieldset',
      title:'基本控制',
      autoHeight:true,
      layout:{
        type:'table',
        columns:2
      },
      items:[
	        {border:false,items:[{xtype:'label',text:'设备名称:',style:'fontSize:13px;'},{id:'DeviceName',xtype:'textfield',style:'margin:0 0 5px 6px;',fieldLabel:'设备名称',name:'设备名称'}]}, 
	        {xtype:'button',text:'获取',name:'获取',style:'margin:0 0 0 20px',handler:function(){ButtonPress('getDevName');}},
	        {border:false,items:[{xtype:'label',text:'通道列表:',style:'fontSize:13px;float:left'},{id:'ChannelList',xtype:'combo',fieldLabel:'通道列表',name:'ChannelList',style:'margin:0 0 5px 6px;',
                width:131,store:combox_store,triggerAction:'all',displayField:'channel_name',mode:'local',
                valueField:'channel_id',emptyText:'----请选择通道----'
                }
             ]}, 	
	        {xtype:'button',text:'获取',name:'获取',style:'margin:0 0 0 20px',handler:function(){ ButtonPress('getDevChan');}},
	        {xtype:'button',text:'&Delta;开始预览',name:'开始预览',style:'marginLeft:60px;marginTop:10px',handler:function(){ButtonPress('Preview:start');}},
	        {xtype:'button',text:'&nabla;停止预览',name:'停止预览',style:'marginTop:10px',handler:function(){ ButtonPress('Preview:stop');}},
	        {xtype:'button',text:'抓BMP图',name:'抓BMP图',style:'marginLeft:60px;marginTop:10px',handler:function(){ ButtonPress('CatPic:bmp');}},
	        {xtype:'button',text:'抓JPEG图',name:'抓JPEG图',style:'marginTop:10px',handler:function(){ ButtonPress('CatPic:jpeg');}},
	        {xtype:'button',text:'开始录像',name:'开始录像',style:'marginLeft:60px;marginTop:10px',handler:function(){ButtonPress('Record:start');}},
	        {xtype:'button',text:'停止录像',name:'停止录像',style:'marginTop:10px',handler:function(){ButtonPress('Record:stop');}},
	        {xtype:'button',text:'开始对讲',name:'开始对讲',style:'marginLeft:60px;marginTop:10px',handler:function(){ButtonPress('talk:start');}},
	        {xtype:'button',text:'停止对讲',name:'停止对讲',style:'marginTop:10px',handler:function(){ButtonPress('talk:stop');}},
	        {xtype:'button',text:'打开声音',name:'打开声音',style:'marginLeft:60px;marginTop:10px',handler:function(){ButtonPress('voice:start');}},
	        {xtype:'button',text:'关闭声音',name:'关闭声音',style:'marginTop:10px',handler:function(){ButtonPress('voice:stop');}}
            ]
         },
         {
            xtype:'fieldset',
            title:'云台控制',
            autoHeight:true,
            layout:{
               type:'table',
               columns:6
            },
            items:[
            {xtype:'button',text:'左上',name:'左上',handler:function(){ButtonPress('PTZ:leftup');}},
            {xtype:'button',text:'上',name:'上',style:'marginLeft:10px',handler:function(){ButtonPress('PTZ:up');}},
            {xtype:'button',text:'右上',name:'右上',style:'marginLeft:10px',handler:function(){ButtonPress('PTZ:rightup');}},
            {xtype:'button',text:'+',name:'+',style:'marginLeft:10px',handler:function(){ButtonPress('zoom:in');}},
            {xtype:'label',text:'焦距',name:'焦距',style:'marginLeft:10px;font-size:12px'},
            {xtype:'button',text:'-',name:'-',style:'marginLeft:10px',handler:function(){ButtonPress('zoom:out');}},
            {xtype:'button',text:'左',name:'左',style:'marginTop:1px',handler:function(){ButtonPress('PTZ:left');}},
            {xtype:'button',text:'停',name:'停',style:'marginLeft:10px;marginTop:5px;',handler:function(){ButtonPress('PTZ:stop');}},
            {xtype:'button',text:'右',name:'右',style:'marginLeft:10px;marginTop:5px',handler:function(){ButtonPress('PTZ:right');}}, 
            {xtype:'button',text:'+',name:'+',style:'marginLeft:10px;marginTop:5px',handler:function(){ButtonPress('focus:in');}},
            {xtype:'label',text:'焦点',name:'焦点',style:'marginLeft:10px;marginTop:5px;font-size:12px'},
            {xtype:'button',text:'-',name:'-',style:'marginLeft:10px;marginTop:5px',handler:function(){ButtonPress('focus:out');}},
            {xtype:'button',text:'左下',name:'左下',handler:function(){ButtonPress('PTZ:leftdown');}},
            {xtype:'button',text:'下',name:'下',style:'marginLeft:10px',handler:function(){ButtonPress('PTZ:down');}},
            {xtype:'button',text:'右下',name:'右下',style:'marginLeft:10px',handler:function(){ButtonPress('PTZ:rightdown');}},
            {xtype:'button',text:'+',name:'+',style:'marginLeft:10px',handler:function(){ButtonPress('iris:in');}},
            {xtype:'label',text:'光圈',name:'光圈',style:'marginLeft:10px;font-size:12px'},
            {xtype:'button',text:'-',name:'-',style:'marginLeft:10px',handler:function(){ButtonPress('iris:out');}}
            ]
      },{
        xtype:'fieldset',
        title:'图像参数',
        layout:'table',
        layoutConfig:{
          columns:3
        },
        autoHeight:true,
        items:[
        {xtype:'label',text:'亮     度:',style:'fontSize:13px;'},
        { id:'PicLight',name:'PicLight',xtype:'combo',fieldLabel:'亮     度',colspan:2,store:img_store,triggerAction:'all',displayField:'id',
            valueField:'id',mode:'local',style:'margin:2px 0 0 5px',emptyText:'---------请选择亮度---------'
        },
        {xtype:'label',text:'对比度:',style:'fontSize:13px;'},
        {id:'PicContrast',name:'PicContrast',xtype:'combo',fieldLabel:'对比度',store:img_store,triggerAction:'all',displayField:'id',mode:'local',
            valueField:'id',style:'marginLeft:5px',emptyText:'---------请选择对比度---------'
        },
        {xtype:'button',text:'获取',name:'获取',style:'marginLeft:12px;marginTop:8px',handler:function(){ButtonPress('getImagePar');}},
        {xtype:'label',text:'饱和度:',style:'fontSize:13px;'},
        {id:'PicSaturation',name:'PicSaturation',xtype:'combo',fieldLabel:'饱和度',store:img_store,triggerAction:'all',displayField:'id',mode:'local',
            valueField:'id',style:'marginLeft:5px',emptyText:'---------请选择饱和度---------'
        },
        {xtype:'button',text:'设置',name:'设置',style:'marginLeft:12px;marginTop:8px',handler:function(){ButtonPress('setImagePar');}},
        {xtype:'label',text:'色    调:',style:'fontSize:13px;'},
        {id:'PicTonal',name:'PicTonal',xtype:'combo',fieldLabel:'色    调',colspan:2,store:img_store,triggerAction:'all',displayField:'id',
            valueField:'id',mode:'local',style:'margin:0 0 2px 5px',emptyText:'---------请选择色调---------'
        }]
      }]
    });
    //大棚导航树    
   this.shedTreePanel = new Long.ShedTree({
	    id:'video_shedtree',
	    region:'west',
	    listeners:{
	        'click':function(node,event){
	            Ext.getCmp('videomonitor').setTitle('监控界面['+node.text+']');
                //注册用户
	            Ext.Ajax.request({
	                url:'video!load',
	                params:{
	                    shedId : node.id
	                },
                    async : false,
	                success:function(response){
	                    var result = Ext.util.JSON.decode(response.responseText);
                         if(result.success){
                            //使用全局变量接收数据
                            szDevIp = result.data['property1'];
	                        szDevPort = result.data['property2'];
	                        szDevUser = result.data['property3'];
	                        szDevPwd = result.data['property4'];
                         }else{
                            Ext.ux.Toast.msg('系统提示',result.msg);
                         }
	                },
	                failure:function(response){
	                    Ext.ux.Toast.msg('系统提示','无法访问后台!');
	                }
	            });
	            m_iLoginUserId = m_bDVRControl.Login(szDevIp,szDevPort,szDevUser,szDevPwd);
	            if(m_iLoginUserId == -1)
	            {
	                LogMessage("注册失败！");
	                return;
	            }
	            else
	            {
	                LogMessage("注册成功！");
	                for(var i = 2; i <= 4; i ++)
	                {
	                    document.getElementById("HIKOBJECT" + i).SetUserID(m_iLoginUserId);
	                }
	            }
	        }
	    }
    });   
                   
    Ext.applyIf(this, {
        border:false,
        layout:'border',
        closable:true,
        items:[this.shedTreePanel,this.accordion,this.accordion1]
    });
    Long.VideoMonitorPanel.superclass.initComponent.call(this);
    this.addEvents('afterrender');
    },
    afterRender:function(){
        Long.VideoMonitorPanel.superclass.afterRender.call(this);
        if(!Ext.isIE){
            Ext.Msg.alert('系统提示','由于本系统摄像头组件采用ActiveX控件,</br>只支持IE浏览器,故请切换至IE浏览器使用!');
            return;
        }
        if(document.getElementById("HIKOBJECT1").object == null){
            alert("请先下载控件并注册！");
            m_bDVRControl = null;
        }   
        else
	    {
            m_bDVRControl = document.getElementById("HIKOBJECT1");
            ChangeStatus(1);
            ArrangeWindow(4);
	    }
    }
});