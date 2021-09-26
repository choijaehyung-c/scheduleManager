package cjh.cleverc.beans;




import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UserBean {
		private String userId;//jsp 넘어오는 값하고 동일하게
		private String userPwd;
		private String userName;
		private String userEmail;
		private String friend;
		private String confirm;
		private String search;
		private MultipartFile[] upFile;
		private String multitext;
}
