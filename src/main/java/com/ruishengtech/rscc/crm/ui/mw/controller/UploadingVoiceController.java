package com.ruishengtech.rscc.crm.ui.mw.controller;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ruishengtech.framework.core.ApplicationHelper;
import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.rscc.crm.ui.mw.command.ForAgentCommand;
import com.ruishengtech.rscc.crm.ui.mw.condition.UploadingVoiceCondition;
import com.ruishengtech.rscc.crm.ui.mw.model.FSQueue;
import com.ruishengtech.rscc.crm.ui.mw.model.MWFsHost;
import com.ruishengtech.rscc.crm.ui.mw.model.UploadingVoice;
import com.ruishengtech.rscc.crm.ui.mw.service.FSQueueService;
import com.ruishengtech.rscc.crm.ui.mw.service.UploadingVoiceService;
import com.ruishengtech.rscc.crm.ui.mw.sys.FsServerManager;
import com.ruishengtech.rscc.crm.ui.mw.util.FTPTool;
import com.ruishengtech.rscc.crm.ui.sysConfig.SysConfigManager;

/**
 * Created by yaoliceng on 2014/11/3.
 */
@Controller
@RequestMapping("system/uploadingvoice")
public class UploadingVoiceController {
	
		@Autowired
		private FSQueueService fsQueueService;
	
		@Autowired
		private UploadingVoiceService uploadingVoiceService;
	
	    @Resource(name = "propertyConfigurer")
	    private SpringPropertiesUtil propertiesUtil;
	
	    @Autowired
	    private FsServerManager fsServerManager;
	
		@RequestMapping
		public String Batch(HttpServletRequest request,
				HttpServletResponse response, Model model, String str)  {
			
			model.addAttribute("iframecontent","config/uploadingvoice");
			return "iframe";
			
//	 	   	return "config/uploadingvoice";
		}

 	  /**
 	     * 加载标题
 	     */
 	    @RequestMapping("menushow")
 	    @ResponseBody
 	    public String menushow(HttpServletRequest request, HttpServletResponse response, MWFsHost mwFsHost)
 	            throws IOException {
 	        JSONObject jsob = new JSONObject();
 	        	jsob.put("titleshow", "<i class='fa fa-lg fa-fw fa-wrench'></i>系统配置&nbsp;&nbsp;>&nbsp;<span>语音管理</span>");
 	        return jsob.toString();
 	    }
 	    
 	   /**
 		 * 数据请求
 		 * 
 		 * @param request
 		 * @param response
 		 * @param model
 		 * @return
 		 */
 		@RequestMapping("data")
 		@ResponseBody
 		public String data(HttpServletRequest request,
 				HttpServletResponse response, UploadingVoiceCondition uploadingVoiceCondition) {
 			PageResult<UploadingVoice> pageResult = uploadingVoiceService.page(uploadingVoiceCondition);		//获取符合查询条件的数据
 			List<UploadingVoice> list = (List<UploadingVoice>) pageResult.getRet();
 			
 			JSONArray jsonArray = new JSONArray();
 			
 			JSONObject jsonObject = new JSONObject();
 			for (int i = 0; i < list.size(); i++) {	
 				JSONObject jsob = new JSONObject(list.get(i));
 				jsonArray.put(jsob);
 			}

 			jsonObject.put("aaData", jsonArray);
 			jsonObject.put("iTotalRecords", pageResult.getiTotalRecords());
 			jsonObject.put("iTotalDisplayRecords",pageResult.getiTotalDisplayRecords());
 			return jsonObject.toString();

 		}
 		
 		@RequestMapping("get")
 		public String getScopExt(HttpServletRequest request,
 				HttpServletResponse response, Long id, Model model)
 				throws SocketException {
 				if (id != null) {
 					UploadingVoice item =uploadingVoiceService.get(id);
 					 model.addAttribute("item",item);
 				}
 	        return "config/uploadingvoice_save";
 		}
 		
 		/**
 		 * 上传(添加语音)
 		 * 
 		 * @param request
 		 * @param response
 		 * @return
 		 * @throws Exception
 		 * @throws IllegalStateException
 		 */
 		@RequestMapping("save")
 		@ResponseBody
 		public String save(HttpServletRequest request,
 				HttpServletResponse response, MultipartFile file,
 				UploadingVoice uploadingVoice ,BindingResult result) throws Exception {
 			if (file != null) {
 				//读取配置文件中的保存路径
 				String localFilePath = ApplicationHelper.getSysProperty("sys.upload");
 				File serverFiles = new File(localFilePath);
 				// 不存在则创建
 				if (!serverFiles.exists()) { 
 					serverFiles.mkdirs();
 				}
 				String fileName = file.getOriginalFilename(); // 获得页面传过来文件名
 				String fileNameSuffix = fileName.substring(fileName.lastIndexOf("."),fileName.length()); // 得到后缀如--[.mp3]
 				//文件路径为 /+当前毫秒数+后缀
 				String filePath = localFilePath +"/"+ System.currentTimeMillis()+ fileNameSuffix ;
 				// 路径+文件名（包含后缀）
 				File localFile = new File(filePath);
 				file.transferTo(localFile);
 				uploadingVoice.setVoice(filePath);

                uploadingVoiceService.saveOrUpdate(uploadingVoice);
                FTPTool ftpTool =new FTPTool(propertiesUtil.getProperty("ftpip"), Integer.valueOf(propertiesUtil.getProperty("ftpport")),
                        propertiesUtil.getProperty("ftpusername"), propertiesUtil.getProperty("ftppassword"), true);
                ftpTool.connect();
                ftpTool.put(localFile.getAbsolutePath(), filePath);

                fsServerManager.sendAgentCommond(ForAgentCommand.upload, "path="+filePath,null);
            }

// 			SysCacheManager.getInstance().loadDebQuestion();
// 	        SysCacheManager.getInstance().loadValibleQuestion();
 			return new JSONObject().put("success", true).toString();
 		}
 		
 		/**
 		 * 试听
 		 * @param request
 		 * @param response
 		 * @throws SocketException
 		 */
 		@RequestMapping("audition")
 		public String getAudition( HttpServletRequest request,
 				HttpServletResponse response, Long id,String recording, Model model)
 				throws SocketException {
 			
 			// 			Ranges = "bytes";
 	    	try {
// 				if (id != null) {
// 					CDR record = recordService.getById(CDR.class, id);
// 				//获得录音文件路径
// 				model.addAttribute("urls",record.getPlayUrl(propertiesUtil.getProperty("ngnix")));
// 				
// 				//存放录音信息播放页面显示
// 				record.setRecord_file(record.getRecord_file().substring(1));
// 				model.addAttribute("r", record);
// 				return "record/record_player";
// 				}else{
//

                UploadingVoice ret = uploadingVoiceService.getById(UploadingVoice.class, id);
//                model.addAttribute("urls", "http://"+propertiesUtil.getProperty("ngnix")+""+ret.getVoice());
                model.addAttribute("urls", "http://"+SysConfigManager.getInstance().getDataMap().get("nginx").getSysVal()+""+ret.getVoice());
 				return "config/record_player";
// 				}
 			} catch (Exception e) {
 				model.addAttribute("urls", "false");
 				return "config/record_player";
 			}
 		}

	
	/**
	 * 删除
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("remove")
	@ResponseBody
	public String remove(HttpServletRequest request, Long id) throws Exception {

		//  获取所有的技能组信息
		List<FSQueue> queues = uploadingVoiceService.getAllById(FSQueue.class);
		
		UploadingVoice ret = uploadingVoiceService.getById(UploadingVoice.class, id);
		
		if(queues != null){
			for (FSQueue qu : queues) {
				JSONObject jsob = new JSONObject(qu.getConfig());
				if(ret.getVoice().equals(StringUtils.trimToEmpty(jsob.get("moh-sound").toString()))){
					jsob.put("moh-sound", "/usr/local/freeswitch/sounds/moh/aw.wav");
					jsob.put("welcome-music", "/usr/local/freeswitch/sounds/moh/aw.wav");
					qu.setConfig(jsob.toString());
					fsQueueService.saveOrUpdate(qu);
				}
			}
		}
		
		uploadingVoiceService.remove(id);

        FTPTool ftpTool =new FTPTool(propertiesUtil.getProperty("ftpip"), Integer.valueOf(propertiesUtil.getProperty("ftpport")),
                propertiesUtil.getProperty("ftpusername"), propertiesUtil.getProperty("ftppassword"), true);
        ftpTool.connect();
        ftpTool.delete(ret.getVoice());

		return new JSONObject().put("success", true).toString();
	}

	@RequestMapping("checkVoiceName")
	@ResponseBody
	public void isRepeat(HttpServletRequest request, HttpServletResponse response, String voiceName, Long id)
			throws IOException {
		
		UploadingVoice voice = uploadingVoiceService.getVoicesByName(voiceName);
		
		if (voice == null) {
			response.getWriter().print(true); // 直接打印true 通过
		}else{
			if (id==null) {
				response.getWriter().print(false);
			}else{
				if (id.equals(voice.getId())) {
					response.getWriter().print(true);
				}else{
					response.getWriter().print(false);
				}
			}
		}
	}

}
