package cjh.cleverc.services.friends;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import cjh.cleverc.beans.AccessInfo;
import cjh.cleverc.beans.TeamBean;
import cjh.cleverc.beans.TeamDetailsBean;
import cjh.cleverc.beans.UserBean;
import cjh.cleverc.mapper.Friends;
import cjh.cleverc.util.Encryption;
import cjh.cleverc.util.ProjectUtils;

@Service
public class FriendsRelation implements Friends{
@Autowired 
Encryption enc;
@Autowired
ProjectUtils pu;
@Autowired
SqlSessionTemplate sql;
@Autowired
DataSourceTransactionManager tx;
@Autowired
JavaMailSenderImpl javaMail;

private DefaultTransactionDefinition def;
private TransactionStatus status;

	@Override
	public List<TeamBean> getTeamList(TeamBean tb){
		
		try {
			tb.setMmId(enc.aesDecode((String)pu.getAttribute("userSs"),"session") );
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return sql.selectList("getTeamList", tb);
	}
	@Override
	public List<TeamDetailsBean> getTeamMemberList(TeamDetailsBean td){
		List<TeamDetailsBean> memList = sql.selectList("getTeamMemberList",td);
		for(int i = 0 ; i < memList.size() ; i++) {
			try {
				memList.get(i).setMmName(enc.aesDecode(memList.get(i).getMmName(), memList.get(i).getMmId()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		return memList;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<TeamBean> addTeam(TeamDetailsBean td) {
			this.setTransactionConf(TransactionDefinition.PROPAGATION_REQUIRED,TransactionDefinition.ISOLATION_READ_COMMITTED,false);
			
			try {
				td.setMmId(enc.aesDecode((String)pu.getAttribute("userSs"),"session"));
				td.setTypeCode("L");
			} catch (Exception e) {
				e.printStackTrace();
			}
		
			
			boolean tran = false;
			int index = getTodayTeamCode() + 1;
			String strIndex = index+"";
			String zero = "";
			
			
			for(int i = 0 ; i < 3-strIndex.length() ; i++) {
				zero = "0"+zero;
			}
			
			strIndex = zero+strIndex;
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
			Calendar cal = Calendar.getInstance();
			td.setTeCode(sdf.format(cal.getTime())+strIndex);
			try {
				if(insertTeam(td)) {
					if(insertTeamDetail(td)) {
						tran = true;	
					}
				}
			}catch(Exception e) {
				System.out.println("Exception");
			}
			
			setTransactionResult(tran);
			System.out.println(tran);
			TeamBean tb = new TeamBean();
			return this.getTeamList(tb);
			
	}
	
	public List<TeamBean> FriendsList(TeamBean tb){
		try {
			tb.setMmId(enc.aesDecode((String)pu.getAttribute("userSs"),"session") );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sql.selectList("FriendsList",tb);
	}
	
	public String addFriends(String data) {
		this.setTransactionConf(TransactionDefinition.PROPAGATION_REQUIRED,TransactionDefinition.ISOLATION_READ_COMMITTED,false);
		String message = "request fail한글";
		boolean tran = false;
		UserBean ub = new UserBean();
		ub.setUserId(data);
		boolean tftf = true;
		TeamBean tb = new TeamBean();
		List <TeamBean> alreadyFriends = FriendsList(tb);
		
		for(int i = 0 ; i < alreadyFriends.size() ; i++) {
			if(alreadyFriends.get(i).getMmId().equals(data)) {
				message = "fail : alreadyFriends";
				tftf= false;
			}
		}
		
		if(tftf) {
		if(data.equals(ub.getUserId())) {
			message = "request fail한글";
		}else {
			if(isUserId2(ub)==1) {
				try {
					ub.setUserId(enc.aesDecode((String)pu.getAttribute("userSs"),"session"));
					ub.setFriend(data);
					if(insertReqFriend(ub)) {
						message = "request success";
						tran = true;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else {
				System.out.println("요청 실패");
			}
		}
		setTransactionResult(tran);
		}
		return message;
	}
	
	public List<UserBean> requestFriendsList(String data) {
		
		try {
			data = enc.aesDecode((String)pu.getAttribute("userSs"),"session");
		} catch (Exception e) {
			data = "1";
			e.printStackTrace();
		}
		
		
		return sql.selectList("requestFriendsList", data);
	}
	public String confirmFriendRequest(UserBean ub) {
		System.out.println(ub.getFriend());
		System.out.println( ub.getConfirm() );
		String message = "request fail";
		
		try {
			ub.setUserId(enc.aesDecode((String)pu.getAttribute("userSs"),"session") );
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(updateConfirm(ub)){
			message = "request success";
		}else{
			System.out.println("요청 실패");
		}
		
		return message;
	}
	public List<TeamBean> FriendsList2(TeamBean tb){
		try {
			tb.setMmId(enc.aesDecode((String)pu.getAttribute("userSs"),"session") );
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<TeamBean> tbList = sql.selectList("FriendsList",tb);
		
		List<TeamBean> newList = new ArrayList<TeamBean>();
		
		for(int i = 0 ; i < tbList.size() ; i++) {
			tbList.get(i).setTeCode(tb.getTeCode());
			
			if(!convertToBoolean(isTeamMember(tbList.get(i)))) {
				newList.add(tbList.get(i));
			}
			
		}
		return newList;
	}
	
	public void sendEmail(TeamBean tb) {
		
		List<TeamDetailsBean> list=tb.getTdb();
		list.get(0).setEMail("chlwogudc@naver.com");
		list.get(1).setEMail("servermailcc@gmail.com");
		
		for(int i = 0 ; i < list.size(); i++) {
			MimeMessage mail = javaMail.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mail,"UTF-8");
			String contents = "팀"+tb.getTeName()+"에서 "+tb.getTdb().get(i).getMmId()+"님을 팀원으로 초대합니다.\n"
					+ "초대 링크 : http://192.168.43.20/Invite?id="+tb.getTdb().get(i).getMmId()+"&team="+tb.getTeName();
			try {
				helper.setFrom("servermailcc@gmail.com");
				helper.setTo(list.get(i).getEMail());
				helper.setSubject("invitation");
				helper.setText(contents);
				javaMail.send(mail);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}

	}
	
	
	@Override
	public int isTeamMember(TeamBean tb) {
		return sql.selectOne("isTeamMember",tb);
	}
	@Override
	public boolean updateConfirm(UserBean ub) {
		return convertToBoolean(sql.update("updateConfirm", ub));
	}

	
	@Override
	public int isUserId2(UserBean ub) {
		return sql.selectOne("isUserId2",ub);
	}
	
	@Override
	public boolean insertReqFriend(UserBean ub) {
		return convertToBoolean(sql.insert("insertReqFriend", ub));
	}
	
	@Override
	public int getTodayTeamCode() {
		return sql.selectOne("getTodayTeamCodeList");
	}	
	
	@Override
	public boolean insertTeam(TeamDetailsBean td) {
		return convertToBoolean(sql.insert("insertTeam", td));
	}
	
	@Override
	public boolean insertTeamDetail(TeamDetailsBean td) {
		return convertToBoolean(sql.insert("insertTeamDetail", td));
	}

	private void setTransactionConf(int propagation,int isolationLevel, boolean isRead) {
		def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(propagation);
		def.setIsolationLevel(isolationLevel);
		def.setReadOnly(isRead);
		status = tx.getTransaction(def);
	}
	
	private void setTransactionResult(boolean isCheck) {
		if(isCheck) {
			tx.commit(status);
		}else {
			tx.rollback(status);
		}

	}
	
	/* Utility */
	private boolean convertToBoolean(int value) {	
		return (value > 0)? true:false;
	}
	
	public List<UserBean> searchFriend(UserBean ub) {
		List<UserBean> dbList= searchFriendDB();
		List<UserBean> searchList = new ArrayList<UserBean>();
		
		
		for(int i = 0 ; i < dbList.size() ; i++) {
			
			try {
				dbList.get(i).setUserId(dbList.get(i).getUserId());
				dbList.get(i).setUserName(enc.aesDecode(dbList.get(i).getUserName(),dbList.get(i).getUserId()) );
				dbList.get(i).setUserEmail(enc.aesDecode(dbList.get(i).getUserEmail(),dbList.get(i).getUserId()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if((dbList.get(i).getUserId()+dbList.get(i).getUserName()+dbList.get(i).getUserEmail()).contains(ub.getSearch())) {
				searchList.add(dbList.get(i));
			}
		}
		for(int i = 0 ; i < searchList.size() ; i++) {
			System.out.println(searchList.get(i));
		}
		
		
		
		return searchList;
	}
	
	public List<UserBean> searchFriendDB(){
		return sql.selectList("searchFriendDB");
	}
	
}



/* DATASOURCE TRANSACTION MANAGER
 * 선언적 트랜잭션 : AOP 방식 
 * @Transactional 어노테이션
 * 관련 메서드는 public 접근 제한자 사용(상황에따라 default까지 사용가능)
 * 
 * 명시적 트랜잭션 : Programmer Transaction
 *
 * 
 * 
 * propacation 전파방식
 * ins1 -> ins2 -> del3
 * tran <- tran <- tran
 * 
 * -REQUIRED-
 * ins1{
 * 	ins2{}
 * }
 * 
 * isolation 격리수준
 * -READ_COMMITED-
 * PHY REPOSITORY 2 3 5
 * CCC INS(1) 1 2 3 5
 * ZZZ SEL ---> 2 3 5
 * 
 * 
 * -READ_UNCOMMITED-
 * PHY REPOSITORY 2 3 5
 * CCC INS(1) 1 2 3 5
 * ZZZ SEL ---> 1 2 3 5
 * 
 * -READ_UNCOMMITED 예시-
 * USER1 시간조회 좌석조회 좌석선택(A1) 결제 : COMMIT
 * USER2 시간조회 좌석조회 좌석선택(USER1 COMMIT전에도 A1 : X) 결제 : COMMIT
 * => 임시 테이블 놓고 조회하는걸로도 대체 가능(READ_COMMITED).
 * */

/*  
 * PROPAGATION
 *  REQUIRED : 이미 시작된 트랜잭션이 있으면 참여 없으면 새로 시작.(DEFAULT,종속)
 *  SUPPORTS : 이미 시작된 트랜잭션이 있는 경우 참여, 없다면 없이 진행.
 * 	MANDATORY : 이미 시작된 트랜잭션이 있는 경우 참여, 없다면 예외 발생.
 * 	REQUIRED_NEW : 모두 새로운 트랜잭션.(설정하지않으면 기본)
 * 	NOT_SUPPORTED : 트랜잭션 사용하지 않음.
 * */
/* ISOLATION 
 * 동시에 여러 트랜잭션이 진행될 때 특정한 트랜잭션의 결과를 다른 트랜잭션에 노출시키는 방법
 * READ_COMMITED (DEFAULT,커밋된것만 읽음)
 * READ_UNCOMMITED : 가장 낮은 수준의 격리 수준
 * SERIALIZATION : 가장 강력한 격리 수준
 * 
 * */ 
/* READ ONLY
 * TRUE : SELECT
 *  */
