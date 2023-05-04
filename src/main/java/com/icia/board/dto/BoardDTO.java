package com.icia.board.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

@Data
public class BoardDTO {
    private Long id;
    private String boardWriter;
    private String boardPass;
    private String boardTitle;
    private String boardContents;
    private Timestamp boardCreatedDate;
    private int boardHits;
    private int fileAttached;
    private List<MultipartFile> boardFile;  // <-- 여러개가 들어올 경우 List로 받을 수 있다


}
