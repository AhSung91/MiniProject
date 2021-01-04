package kr.co.softcampus.service;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.softcampus.beans.ContentBean;
import kr.co.softcampus.beans.PageBean;
import kr.co.softcampus.beans.UserBean;
import kr.co.softcampus.dao.BoardDao;

@Service
@PropertySource("/WEB-INF/properties/option.properties")	//properties에 저장된 위치에 업로드한 파일들을 저장하기위한 source
//파라미터로 넘어오는 데이터 및 파일 데이터를 저장하는 service
public class BoardService {
//BoardController에서 주입받은 값을 전해 받아서 DAO에 넘겨준다
	
	@Value("${path.upload}")	//글목록을 위한 property값
	private String path_upload;
	
	@Value("${page.listcnt}")
	private int page_listcnt;
	
	@Value("${page.paginationcnt}")
	private int page_paginationcnt;
	
	//Dao에 전달하기 위해 매개변수를 선언
	@Autowired
	private BoardDao boardDao;
	
	@Resource(name = "loginUserBean")
	private UserBean loginUserBean;
	
	private String saveUploadFile(MultipartFile upload_file) {
		String file_name = System.currentTimeMillis() + "_"+ upload_file.getOriginalFilename();
		
		try {
			upload_file.transferTo(new File(path_upload + "/" + file_name));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return file_name;
	}
	public void addContentInfo(ContentBean writeContentBean) {
		
		MultipartFile upload_file = writeContentBean.getUpload_file();
		
		if (upload_file.getSize() > 0) {
			String file_name = saveUploadFile(upload_file);
			writeContentBean.setContent_file(file_name);
		}
		writeContentBean.setContent_writer_idx(loginUserBean.getUser_idx());
		
		boardDao.addContentInfo(writeContentBean);
	}
	public String getBoardInfoName(int board_info_idx) {
		return boardDao.getBoardInfoName(board_info_idx);
	}
	public List<ContentBean> getContentList(int board_info_idx,int page){
		//글목록을 보여주기 위한 변수들
		int start = (page -1) * page_listcnt;
		
		//RowBounds클래스는 객체를 생성할때 두개를 생성하는데 어디서부터(start) 몇개(page_listcnt)를 가져올지를 생성한다
		RowBounds rowBounds = new RowBounds(start,page_listcnt);
		
		
		return boardDao.getContentList(board_info_idx,rowBounds);
		
		
	}
	
	//글 하나의 정보를 가져온다
	public ContentBean getContentInfo(int content_idx) {
		return boardDao.getContentInfo(content_idx);
	}
	
	public void modifyContentInfo(ContentBean modifyContentBean) {
		//첨부된 파일을 가져온다
		MultipartFile upload_file = modifyContentBean.getUpload_file();
		
		if (upload_file.getSize() > 0) {
			String file_name = saveUploadFile(upload_file);
			modifyContentBean.setContent_file(file_name);
		}
		boardDao.modifyContentInfo(modifyContentBean);
	}
	
	public void deleteContentInfo(int content_idx) {
		
		boardDao.deleteContentInfo(content_idx);
	}
	public PageBean getContentCnt(int content_board_idx, int currentPage) {
		
		int content_cnt = boardDao.getContentCnt(content_board_idx);
		
		PageBean pageBean = new PageBean(content_cnt, currentPage, page_listcnt ,page_paginationcnt);
		
		return pageBean;
	}
}
