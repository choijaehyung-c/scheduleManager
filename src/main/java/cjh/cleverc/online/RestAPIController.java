package cjh.cleverc.online;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.annotations.JsonAdapter;

import cjh.cleverc.beans.AccessInfo;
import cjh.cleverc.beans.TeamBean;
import cjh.cleverc.beans.TeamDetailsBean;
import cjh.cleverc.beans.UserBean;
import cjh.cleverc.services.fileupload.FileUploadService;
import cjh.cleverc.services.friends.FriendsRelation;


//page 바디 특정 부분
@RestController
@RequestMapping("/schedule")
public class RestAPIController {
	
	@Autowired
	FriendsRelation fr;
	@Autowired
	FileUploadService fileUploadService;
	
	@PostMapping("/teamList")
	public List<TeamBean> teamList() {
		System.out.println("in teamList");
		TeamBean tb = new TeamBean();
		return fr.getTeamList(tb);
	}
	
	@PostMapping("/teamMemberList")
	public List<TeamDetailsBean> teamMemberList(@RequestBody TeamDetailsBean td){
		System.out.println("in teamMemberList");
		return fr.getTeamMemberList(td);
	}
	
	@PostMapping("/addTeam")
	public List<TeamBean> addTeam(@RequestBody List<TeamDetailsBean> td){
		System.out.println("in addTeam");
		return fr.addTeam(td.get(0));
	}

	@PostMapping("/FriendsList")
	public List<TeamBean> FriendsList(){
		TeamBean tb = new TeamBean();
		return fr.FriendsList(tb);
	}
	
	@PostMapping("/FriendsList2")
	public List<TeamBean> FriendsList2(@RequestBody String data){
		TeamBean tb = new TeamBean();
		tb.setTeCode(data);
		return fr.FriendsList2(tb);
	}
	
	@PostMapping("/addFriends")
	public String addFriends(@RequestBody String data){
		System.out.println("asd");
		data = data.substring(0, data.length()-1);
		return fr.addFriends(data);
	}
	
	@PostMapping("/requestFriends")
	public List<UserBean> requestFriends() {
		return fr.requestFriendsList("");
	}
	
	@PostMapping("/confirmFriends")
	public String confirmFriends(@RequestBody UserBean ub) {
		return fr.confirmFriendRequest(ub);
	}
	
	@PostMapping("/inviteTeam")
	public String inviteTeam(@RequestBody TeamBean tb) {
		fr.sendEmail(tb);
		System.out.println("발송완료");
		return null;
	}

	@PostMapping("/searchFriend")
	public List<UserBean> searchFriend(@RequestBody UserBean ub){
		return fr.searchFriend(ub);
	}
	
	@PostMapping("/fileUpload")
	public Map<String,String> fileUpload(@ModelAttribute UserBean ub) {
		System.out.println(ub.getMultitext().toString());
		//System.out.println(ub.getFile1()[1].getContentType());
		// fileUploadService.restore(ub.getUpFile());
		return null;
	}

	/*
	 * List<TeamBean> list = new ArrayList<TeamBean>() ; tb.setMmId("asdasd");
	 * tb.setTeName("cc"); tb.setTeCode("210723001"); list.add(tb);
	 */
	//데이터 바인드 잭슨데이터바인드
	/*
	 * //응답필요없을때 리턴x
	 * 
	 * @PutMapping public void addFriends2() {
	 * 
	 * } //삭제-리턴x
	 * 
	 * @DeleteMapping public void addFriends3() {
	 * 
	 * }
	 */
}
