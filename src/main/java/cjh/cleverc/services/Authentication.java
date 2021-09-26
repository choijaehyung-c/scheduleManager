package cjh.cleverc.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import cjh.cleverc.beans.AccessInfo;
import cjh.cleverc.beans.UserBean;
import cjh.cleverc.util.Encryption;
import cjh.cleverc.util.ProjectUtils;

@Service
public class Authentication {
	@Autowired
	AuthDao dao;
	@Autowired
	Encryption enc;
	@Autowired
	Gson gson;
	@Autowired
	private ProjectUtils pu;

	private ModelAndView mav = null;

	public ModelAndView accessCtl(AccessInfo ai) {
		mav = new ModelAndView();
		try {
			//해당 아이디 로그인이 어딘가에서 된상태(db에 로그인상태)
			if(dao.getAccessSum(ai)) {
				// 그 로그인이 다른브라우저에 되어 있을경우 강제종료후 로그인
				if(!(ai.getBrowser()+ai.getPublicIp()+ai.getPrivateIp()).equals(dao.getLastBrowser(ai))) {	 
					if(dao.forceLogout(ai)) {
						System.out.print("강제로그아웃성공");
						loginProcess(mav,ai);
						System.out.println("다른브라우저로그인성공");
					}
				
				}else {
					
					if(pu.getAttribute("userSs")==null){//서버 로그인이 되어있지만 세션이 만료된 경우
						dao.forceLogout(ai);
						loginProcess(mav,ai);
					}else {//서버 로그인이 되어있고 세션이 살아 있는 경우(같은 브라우저,새탭 페이지안바뀐 로그인버튼)
						mav.setViewName("redirect:/");
					}
					
					
				}
			//해당 아이디가 로그인이 안되어있을경우(db에 로그아웃상태)
			}else{
				loginProcess(mav,ai);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	private void loginProcess(ModelAndView mav,AccessInfo ai) {
		try {	
			if(pu.getAttribute("userSs")==null){
				boolean tf = false;
				if(dao.isUserId(ai)) {
					System.out.println("아이디검증성공");
					if(enc.matches(ai.getUserPwd(),dao.isAccess(ai))){
						System.out.println("로그인성공");
						if(tf = dao.insAccessHistory(ai)) {
							System.out.println(dao.getAccessSum(ai)+"기록성공");
							mav.setViewName("redirect:/");
							//mav.addObject("user",ai.getUserId());
							//pu.setAttribute("userSs",enc.aesEncode(ai.getUserId(),"session"));
							pu.setAttribute("userSs",enc.aesEncode(ai.getUserId(),"session"));
							List<UserBean> userInfo= dao.getUserInfo(ai);
							pu.setAttribute("userName",enc.aesDecode(userInfo.get(0).getUserName(),ai.getUserId()));
							pu.setAttribute("userEmail",enc.aesDecode(userInfo.get(0).getUserEmail(),ai.getUserId()));
							pu.setAttribute("browser",enc.aesEncode((ai.getBrowser()+ai.getPublicIp()+ai.getPrivateIp()),"session"));
						}
					}
				}
				if(!tf){
					mav.setViewName("access");
					mav.addObject("message","alert('로그인실패');");
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ModelAndView accessOutCtl(AccessInfo ai) {
		mav = new ModelAndView();
		try {
			if(pu.getAttribute("userSs") != null) {
				ai.setUserId(enc.aesDecode((String)pu.getAttribute("userSs"),"session"));
				if(dao.getThisAccessSum(ai)) {
					dao.insAccessHistory(ai);
				}
				pu.removeAttribute("userSs");
				pu.removeAttribute("browser");
				mav.setViewName("redirect:/");
				mav.addObject("message","alert('로그아웃 되었습니다.');");
				System.out.println("로그아웃ctl성공");
			}else{
				mav.setViewName("redirect:/");
				mav.addObject("message","alert('이미 로그아웃 되었습니다.');");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	public ModelAndView joinCtl(UserBean ub) {
		mav = new ModelAndView();
		mav.setViewName("join");
		mav.addObject("message","alert('가입실패 잠시 후 다시 시도하세요.');");
		try {
			ub.setUserName(enc.aesEncode(ub.getUserName(),ub.getUserId()));
			ub.setUserEmail(enc.aesEncode(ub.getUserEmail(),ub.getUserId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		ub.setUserPwd(enc.encode(ub.getUserPwd()));
		if(dao.insMember(ub)) {
			mav.setViewName("access");
			mav.addObject("message", "alert('가입성공');");
		}
		return mav;
	}

	public String isDupCheck(AccessInfo ai) {
		return dao.isUserId(ai)?"true":"false";
	}

	public ModelAndView start() {
		mav = new ModelAndView();
		AccessInfo ai = new AccessInfo();
		try {
			//브라우저에 일단 세션이 남아 있는 경우
			if(pu.getAttribute("userSs")!=null){
				ai.setUserId(enc.aesDecode((String)pu.getAttribute("userSs"),"session"));
				//남아 있는 세션이(해당아이디가) DB에 로그인 되어 있는상태 => 마이페이지로
				if(dao.getAccessSum(ai) && dao.getLastBrowser(ai).equals(enc.aesDecode((String)pu.getAttribute("browser"),"session"))) {
					mav.setViewName("mpage");
					mav.addObject("user",ai.getUserId());
				//남아 있는 세션이(해당아이디가) DB에선 이미 로그아웃된경우 =>해당브라우저에 남아있던 세션도 죽임(꼭 새로고침 안해줘도됨 인터넷창 닫으면 어차피 세션 사라짐)
				}else{
					pu.removeAttribute("userSs");
					pu.removeAttribute("browser");
					mav.setViewName("access");
				}
			//브라우저에 해당사이트 할당된 세션이 없음
			}else {
				mav.setViewName("access");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

}
