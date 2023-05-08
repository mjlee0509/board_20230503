package com.icia.board.dto;

import lombok.Data;

@Data
public class PageDTO {
    // 페이징처리 Step 1-2-1
    private int page; // 현재 페이지 수
    private int maxPage; // 전체 페이지 수
    private int startPage; // 하단에 보여지는 시작페이지 번호
    private int endPage; // 하단에 보여지는 마지막 페이지 번호

}
