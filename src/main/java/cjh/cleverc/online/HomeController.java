package cjh.cleverc.online;


import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cjh.cleverc.beans.*;
import cjh.cleverc.services.Authentication;
import cjh.cleverc.util.Encryption;
import cjh.cleverc.util.ProjectUtils;


@Controller
public class HomeController {
	
	@Autowired
	private Authentication auth;
	@Autowired
	Encryption enc;
	private ModelAndView mav;
	//private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	
	//@RequestMapping,@GetMapping,@PostMapping->핸들러맵핑 리스트 등록
	
	@RequestMapping(value = "/", method = {RequestMethod.POST,RequestMethod.GET})//동시에 가능.
	public ModelAndView sendAccessForm(@ModelAttribute AccessInfo ai) {
		 mav = auth.start();
		 return mav;
	}
	@GetMapping("/JoinForm") // = @RequestMapping(value = "/JoinForm", method = RequestMethod.GET)
	public String sendJoinForm() {
		return "join";
	}
	@GetMapping("/ScheduleForm")
	public String sendScheduleForm() {
		return "newDashboard";
	}
	/*
	 * @GetMapping("/mainForm") // = @RequestMapping(value = "/JoinForm", method =
	 * RequestMethod.GET) public ModelAndView sendmainForm() { mav = auth.start();
	 * return mav; }
	 */
	@PostMapping("/Access")
	public ModelAndView Access(@ModelAttribute AccessInfo ai) {
		mav = auth.accessCtl(ai);
		return mav;
	}
	@PostMapping("/Join")
	public ModelAndView memberJoin(@ModelAttribute UserBean ub) {
		mav = auth.joinCtl(ub);
		return mav;
	}
	@PostMapping("/Logout")
	public ModelAndView Logout(@ModelAttribute AccessInfo ai) {
		mav = auth.accessOutCtl(ai);
		return mav;
	}
	//@ModelAttribute = 포스트,폼 자동주입
	@PostMapping("/IsDup")
	@ResponseBody    //텍스트타입(제이슨) 데이터 전달 할때
	public String isDupCheck(@ModelAttribute AccessInfo ai) {
		return auth.isDupCheck(ai);
	}
	
	@GetMapping("/Invite")
	public String invitePage(String id,String team,Model model){
		model.addAttribute("id",id);
		model.addAttribute("team",team);
		return "invite";
	}
	
	
	
	
	
	/*
	 * @PostMapping("/Access2") // = @RequestMapping(value = "/JoinForm", method =
	 * RequestMethod.GET) public String Access2(@RequestParam("code")
	 * ArrayList<String> list) {//같은 name으로 넘어올때 ArrayList 사용가능.
	 * System.out.println(list.get(0)+":"+list.get(1)); return "access";
	 * }@PostMapping("/Access3") // = @RequestMapping(value = "/JoinForm", method =
	 * RequestMethod.GET) public String Access3(@ModelAttribute userBean ub) {//name
	 * 이름과 bean필드이름이 같다면 자동 주입.
	 * System.out.println(ub.getUserId()+":"+ub.getUserPwd()); return "access"; }
	 * 
	 * @PostMapping("/Access4") // = @RequestMapping(value = "/JoinForm", method =
	 * RequestMethod.GET) public String Access4(@ModelAttribute userBean ub) {
	 * System.out.println(ub.getUserId()+":"+ub.getUserPwd()+":"+ub.getInfo().get(0)
	 * +":"+ub.getInfo().get(1)); return "access"; }
	 */
}
