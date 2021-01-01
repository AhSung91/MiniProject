package kr.co.softcampus.mapper;

import org.apache.ibatis.annotations.Insert;

import kr.co.softcampus.beans.ContentBean;

public interface BoardMapper {
	//null이 허용되는 컬럼에 null을 넣을때 myBatis에선 null값인정을 안해준다 그럴땐 type를 정확히 명시하면 제대로 진행이 된다(jdbcType=VARCHER)
	@Insert("insert into content_table(content_idx, content_subject, content_text, " +
			"content_file, content_writer_idx, content_board_idx, content_date) "+
			"values (content_seq.nextval, #{content_subject}, #{content_text}, #{content_file, jdbcType=VARCHAR}, "+
			"#{content_writer_idx}, #{content_board_idx}, sysdate)")
	void addContentInfo(ContentBean writeContentBean);
	
}
