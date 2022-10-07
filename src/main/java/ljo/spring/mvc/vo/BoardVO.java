package ljo.spring.mvc.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class BoardVO {
	private String bno;
	private String title;	
	private String userid;
	private String regdate;
	private String views;
	private String contents;
	
}
