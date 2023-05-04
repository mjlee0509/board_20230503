package com.icia.board.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

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
    private MultipartFile boardFile;


}
