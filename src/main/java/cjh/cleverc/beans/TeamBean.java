package cjh.cleverc.beans;

import java.util.List;

import lombok.Data;

//@NoArgsConstructor 잭슨하고 호환상 생성자없을시 롬복에러 날수도있음 에러시 인설트
@Data
public class TeamBean {
	private String teCode;
	private String teName;
	private String mmId;
	private List<TeamDetailsBean> tdb;
}
