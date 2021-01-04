package kr.co.softcampus.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import kr.co.softcampus.beans.ContentBean;

public interface BoardMapper {
	//새로운 시퀀스 값을 Bean에 있는 content_idx에 담기위한 쿼리문
	@SelectKey(statement = "select content_seq.nextval from dual",keyProperty = "content_idx", 
			   before=true, resultType = int.class)
	
	//위에 선언한 쿼리문의 idx값을 넣어준다
	//null이 허용되는 컬럼에 null을 넣을때 myBatis에선 null값인정을 안해준다 그럴땐 type를 정확히 명시하면 제대로 진행이 된다(jdbcType=VARCHER)
	@Insert("insert into content_table(content_idx, content_subject, content_text, " +
			"content_file, content_writer_idx, content_board_idx, content_date) "+
			"values (#{content_idx}, #{content_subject}, #{content_text}, #{content_file, jdbcType=VARCHAR}, "+
			"#{content_writer_idx}, #{content_board_idx}, sysdate)")
	void addContentInfo(ContentBean writeContentBean);
	
	//작성한 글의 목록을 보여준다
	@Select("select board_info_name "+
			"from board_info_table " +
			"where board_info_idx = #{board_info_idx}")
	String getBoardInfoName(int board_info_idx);
	
	//게시글 리스트를 보여준다
	@Select("select a1.content_idx, a1.content_subject, a2.user_name as content_writer_name, " + 
			"       to_char(a1.content_date, 'YYYY-MM-DD') as content_date " + 
			"from content_table a1, user_table a2 " + 
			"where a1.content_writer_idx = a2.user_idx " + 
			"and a1.content_board_idx = #{board_info_idx} " + 
			"order by a1.content_idx desc")
	List<ContentBean> getContentList(int board_info_idx,RowBounds rowBounds);	
	
	
	//글 읽기 페이지에 필요한 값들을 가져온다
	@Select("select a2.user_name as content_writer_name,\r\n" + 
			"        to_char(a1.content_date, 'YYYY-MM-DD') as content_date, " + 
			"        a1.content_subject, a1.content_text, a1.content_file, a1.content_writer_idx " + 
			"from content_table a1, user_table a2 " + 
			"where a1.content_writer_idx = a2.user_idx " + 
			"and content_idx = #{content_idx}")
	ContentBean getContentInfo(int content_idx);
	
	//update쿼리문
	@Update("update content_table " + 
			"set content_subject = #{content_subject}, content_text = #{content_text}, " + 
			"content_file = #{content_file, jdbcType = VARCHAR} " +
			"where content_idx = #{content_idx}")
	void modifyContentInfo(ContentBean modifyContentBean);
	
	//글삭제 쿼리문
	@Delete("delete from content_table where content_idx = #{content_idx}")
	void deleteContentInfo(int content_idx);
	
	//전체 글의 개수
	@Select("select count(*) from content_table where content_board_idx = #{content_board_idx}")
	int getContentCnt(int content_board_idx);
	
}
