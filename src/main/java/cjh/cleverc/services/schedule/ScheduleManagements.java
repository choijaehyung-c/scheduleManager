package cjh.cleverc.services.schedule;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;

import cjh.cleverc.mapper.ScheduleMapper;
import cjh.cleverc.util.ProjectUtils;

@Service
public class ScheduleManagements implements ScheduleMapper {
	@Autowired
	SqlSessionTemplate sql;
	@Autowired
	ProjectUtils pu;
	
	public void insDataPath(String[] path) {
		pu.setTransactionConf(TransactionDefinition.PROPAGATION_REQUIRED,TransactionDefinition.ISOLATION_READ_COMMITTED,false);
		
		int tfCount = 0;
		
		for(int i = 0 ; i < path.length ; i++) {
			tfCount += insertDataPath(path[i]);
		}
		
		System.out.println(tfCount);
		pu.setTransactionResult(tfCount==path.length);
	}
	
	
	private boolean convertToBoolean(int value) {	
		return (value > 0)? true:false;
	}


	@Override
	public int insertDataPath(String data) {
		return sql.insert("insertDataPath",data);
	}
	
	public String getPath() {
		return sql.selectOne("getPath");
	}
	
	
}
