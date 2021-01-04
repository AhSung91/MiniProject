package kr.co.softcampus.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.softcampus.beans.ContentBean;
import kr.co.softcampus.dao.BoardDao;

@Service	//메인화면 서비스
public class MainService {
	//게시판 별로 5개의 글만 보여준다
	
	@Autowired
	private BoardDao boardDao;
	
	public List<ContentBean> getMainList(int board_info_idx){
		RowBounds rowBounds = new RowBounds(0,5);
		
		return boardDao.getContentList(board_info_idx, rowBounds);
	}
}
