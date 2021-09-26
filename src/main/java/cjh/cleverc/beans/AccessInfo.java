package cjh.cleverc.beans;

import java.util.Date;
import lombok.Data;

@Data
public class AccessInfo {
	private String userId;
	private String userPwd;
	private Date date;
	private int method;
	private String publicIp;
	private String privateIp;
	private String browser;
}
