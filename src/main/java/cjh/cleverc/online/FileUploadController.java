package cjh.cleverc.online;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cjh.cleverc.beans.UserBean;
import cjh.cleverc.services.fileupload.FileUploadService;
import cjh.cleverc.services.schedule.ScheduleManagements;
import cjh.cleverc.util.ProjectUtils;

@Controller
public class FileUploadController {
	@Autowired
	FileUploadService fileUploadService;
	@Autowired
	private ProjectUtils pu;
	@Autowired
	ScheduleManagements sdm;
	
	@PostMapping( "/upload" )
	public ModelAndView upload(@ModelAttribute UserBean ub) {
		//@RequestParam("file") MultipartFile[] files
		ModelAndView mav = new ModelAndView();
		System.out.println(ub.getUpFile().length);
		sdm.insDataPath(pu.setFile(ub.getUpFile()));
		mav.setViewName("mpage");
		mav.addObject("tempAddress",sdm.getPath());
		return mav;
	}
}
