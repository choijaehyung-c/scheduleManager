package cjh.cleverc.mapper;

import java.util.List;

import cjh.cleverc.beans.TeamBean;
import cjh.cleverc.beans.TeamDetailsBean;
import cjh.cleverc.beans.UserBean;

public interface Friends {
	public List<TeamBean> getTeamList(TeamBean tb);
	public List<TeamDetailsBean> getTeamMemberList(TeamDetailsBean td);
	public int getTodayTeamCode();
	public List<TeamBean> addTeam(TeamDetailsBean td);
	public boolean insertTeam(TeamDetailsBean td);
	public boolean insertTeamDetail(TeamDetailsBean td);
	public List<TeamBean> FriendsList(TeamBean tb);
	public int isUserId2(UserBean ub);
	public boolean insertReqFriend(UserBean ub);
	public List<UserBean> requestFriendsList(String data);
	public boolean updateConfirm(UserBean ub);
	public int isTeamMember(TeamBean tb);
	public List<UserBean> searchFriendDB();
}
