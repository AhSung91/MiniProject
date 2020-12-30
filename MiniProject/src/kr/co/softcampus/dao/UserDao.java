package kr.co.softcampus.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.softcampus.beans.UserBean;
import kr.co.softcampus.mapper.UserMapper;

@Repository
public class UserDao {
	@Autowired
	private UserMapper userMapper;
	
	public String checkUserIdExist(String user_id) {
		
		return userMapper.checkUserIdExist(user_id);
	}
	
	public void addUserInfo(UserBean joinUserBean) {
		userMapper.addUserInfo(joinUserBean);
	}
	public UserBean getLoginUserInfo(UserBean tempLoginUserBean) {
		return userMapper.getLoginUserInfo(tempLoginUserBean);
	}
	
	//정보수정을 위해 로그인한 유저의 인덱스 번호를 받아서 진행
	public UserBean getModifyUserInfo(int user_idx) {
		return userMapper.getModifyUserInfo(user_idx);
		
	}
	public void modifyUserInfo(UserBean modifyUserBean) {
		userMapper.modifyUserInfo(modifyUserBean);
	}
}
