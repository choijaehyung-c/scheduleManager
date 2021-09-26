package cjh.cleverc.mapper;

import java.util.List;

import cjh.cleverc.beans.AccessInfo;
import cjh.cleverc.beans.UserBean;

/* mapper인터페이스와 mapper.xml 위치가 다를경우 
 * mybatis-spring=>org.mybatis.spring=>
 * SqlSessionFactoryBean.class=>SqlSessionFactoryBean=>
 * configLocation를 xml를통해 위치 설정해주면됨*/
/**/
public interface Mapper {
	void isUserId(AccessInfo ai);
	void insAccessHistory(AccessInfo ai);
	void getAccess(AccessInfo ai);
	void insMember(UserBean ub);
	void getUserInfo(UserBean ub);
	void getAccessHistory(AccessInfo ai);
	void getThisAccessHistory(AccessInfo ai);
	int forceLogout(AccessInfo ai);
	void getBrowser(AccessInfo ai);
}
