package cjh.cleverc.services;

import java.util.List;

import org.apache.ibatis.session.ResultHandler;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import cjh.cleverc.beans.AccessInfo;
import cjh.cleverc.beans.UserBean;

/* Dao에 인터페이스를 implements로 상속시 오버라이드로 재정의해 인터페이스로 xml접근 => 접근제한자 default 사용하기어려움(Dao위치)
 * 하지만 Dao 인터페이스 implements를 안받아도 mapper 연동가능
 * Dao랑 인터페이스는 별개
 * 인터페이스는 xml를 위한것
 * 
 * sqlSession : selectone 이외에는 list 로 리턴
 * 리절트타입 명시만 잘해주면 역시 자동주입
 * */

@Repository //싱글톤db
class AuthDao{
	
	@Autowired
	SqlSessionTemplate sqlSession;
	
	boolean isUserId(AccessInfo ai) {
		return convertToBoolean(sqlSession.selectOne("isUserId", ai));
	}

	boolean insAccessHistory(AccessInfo ai) {
		return convertToBoolean(sqlSession.insert("insAccessHistory", ai));
	}

	String isAccess(AccessInfo ai) {
		return sqlSession.selectOne("getAccess",ai);
	}
	String getLastBrowser(AccessInfo ai) {
		return sqlSession.selectOne("getBrowser",ai)==null?"x":sqlSession.selectOne("getBrowser",ai);
	}
	
	boolean getAccessSum(AccessInfo ai) {
		return convertToBoolean(sqlSession.selectOne("getAccessHistory", ai));
	}
	
	
	  boolean getThisAccessSum(AccessInfo ai) { return
	  convertToBoolean(sqlSession.selectOne("getThisAccessHistory", ai)); }
	 
	boolean forceLogout(AccessInfo ai) {
		return convertToBoolean(sqlSession.insert("forceLogout", ai));
	}
	
	boolean insMember(UserBean ub) {
		return convertToBoolean(sqlSession.insert("insMember", ub));
	}
	List<UserBean> getUserInfo(AccessInfo ai) {
		return sqlSession.selectList("getUserInfo", ai);
	}
	
	/* Utility */
	private boolean convertToBoolean(int value) {	
		return (value > 0)? true:false;
	}
}
