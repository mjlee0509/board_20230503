package com.icia.board.service;

import com.icia.board.dto.BoardDTO;
import com.icia.board.dto.BoardFileDTO;
import com.icia.board.dto.PageDTO;
import com.icia.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class BoardService {
    @Autowired
    public BoardRepository boardRepository;
    public void save(BoardDTO boardDTO) throws IOException {
        // 파일이 있을때 or 없을때 구분해야 함
        if(boardDTO.getBoardFile().get(0).isEmpty()) {  // <-- 여기도 get(0) 추가: 0번째 인덱스에 파일이 있는가?
            // 파일 없음 -> 기존의 방식대로
            System.out.println("파일무");
            boardDTO.setFileAttached(0);
            boardRepository.save(boardDTO);
        } else {
            // 파일 있음
            /*
            1. 파일첨부 여부 값을 1로 세팅하고 DB에 제목 등 내용을 board_table에 저장
            2. 파일의 이름을 가져와서 DTO 필드값에 세팅
            3. 저장용 파일 이름을 만들고 DTO 필드값에 세팅
            4. 로컬에 파일 저장
            5. board_file_table에 저장 처리
            !!!. 다중파일인 경우 원본파일 이름 가져오기부터 for문으로 돌린다
            */
            System.out.println("파일유");
            boardDTO.setFileAttached(1);
            BoardDTO dto = boardRepository.save(boardDTO);
            for (MultipartFile boardFile: boardDTO.getBoardFile()) {
                // 원본파일 이름 가져오기
                String originalFileName = boardFile.getOriginalFilename();  // <-- boardFile.get~~~ ...
                System.out.println("originalFileName = " + originalFileName);
                // 저장용파일 이름 만들기
                // 난수생성 방법 1 : 1970년 1월 1일부터 현재까지 몇 밀리초가 지났는지 (1683173790025)
                System.out.println(System.currentTimeMillis());
                // 난수생성 방법 2 : 일정한 규칙에 의거한 난수생성(8ff04f1a-45d6-4215-92cc-45af0cc13252)
                System.out.println(UUID.randomUUID().toString());
                String storedFileName = System.currentTimeMillis() + "-" + originalFileName;
                System.out.println("storedFileName = " + storedFileName);
                // 저장을 위해 BoardFileDTO 세팅
                BoardFileDTO boardFileDTO = new BoardFileDTO();
                boardFileDTO.setOriginalFileName(originalFileName);
                boardFileDTO.setBoardId(dto.getId());
                boardFileDTO.setStoredFileName(storedFileName);
                // 로컬에 파일 저장
                String savePath = "D:\\springFramework_img\\" + storedFileName;  // 맨위에 \\ 붙이는거 까먹지말자
                // 저장처리
                // transferTo 빨간줄 ==> throw : 예외 짬시키기
                boardFile.transferTo(new File(savePath));  // <-- boardFile.transferTo ~~~
                boardRepository.saveFile(boardFileDTO);
            }
        }
    }

    public List<BoardDTO> findAll() {
        List<BoardDTO> boardDTOList = boardRepository.findAll();
        return boardDTOList;
    }

    public BoardDTO findById(Long id) {
        BoardDTO boardDTO = boardRepository.findById(id);
        return boardDTO;
    }

    public void delete(Long id) {
        boardRepository.delete(id);
    }

    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    public void update(BoardDTO boardDTO) {
        boardRepository.update(boardDTO);
    }

    public List<BoardFileDTO> findFile(Long id) {
        return boardRepository.findFile(id);
    }

    public List<BoardDTO> pagingList(int page) {
        int pageLimit = 3; // 한 페이지에 보여줄 글 갯수
        int pagingStart = (page-1) * pageLimit;
        Map<String, Integer> pagingParams = new HashMap<>();
        pagingParams.put("start", pagingStart);
        pagingParams.put("limit", pageLimit);
        List<BoardDTO> boardDTOList = boardRepository.pagingList(pagingParams);
        return boardDTOList;
    }

    public PageDTO pagingParam(int page) {
        /*
          한 페이지에 3개씩, 하단에는 3페이지씩 띄워보자.
          단, 맨 끝 페이지의 수는 필요한 페이지의 수에 따라 유동적이어야 한다 (페이징처리에서 가장 중요한 부분임)
        */
        int pageLimit = 3;
        int blockLimit = 3;

        // 페이징처리 Step 2-1. 전체 글 개수 조회
        int boardCount = boardRepository.boardCount();

        // 페이징처리 Step 2-2. 전체 페이지 갯수 계산
        /*
            i. boardCount / 3 의 결과는 버림 처리된 int임
            ii. 그러므로 이것을 double로 형변환한 후
            iii. Math.ceil을 통해 올림처리 하고
            iv. int로 다시 형변환해주는 과정임
        */
        int maxPage = (int)(Math.ceil((double)boardCount/pageLimit));

        // 페이징처리 Step 2-3. 시작페이지&마지막페이지 값 계산
            // 시작페이지 (1, 4, 7, ... )
        int startPage = (((int)(Math.ceil((double) page / blockLimit))) - 1) * blockLimit + 1;
            // 마지막 페이지 (3, 6, 9, ... )
        int endPage = startPage + blockLimit - 1;

        // 페이징처리 Step 2-4. 현재 페이지 개수가 계산된 endPage보다 적을 떄에는 endPage 값을 maxPage 값과 같게 세팅
        if(endPage > maxPage) {
            endPage = maxPage;
        }

        // 페이징처리 Step 2-5. 이 결과를 DTO에 담아서 가져가기
        PageDTO pageDTO = new PageDTO();
        pageDTO.setPage(page);
        pageDTO.setMaxPage(maxPage);
        pageDTO.setEndPage(endPage);
        pageDTO.setStartPage(startPage);
        return pageDTO;
    }

    public List<BoardDTO> searchList(int page, String type, String q) {
        int pageLimit = 3;
        int pagingStart = (page-1)*pageLimit;
        Map<String, Object> pagingParams = new HashMap<>();
        pagingParams.put("start", pagingStart);
        pagingParams.put("limit", pageLimit);
        pagingParams.put("q", q);
        pagingParams.put("type", type);
        List<BoardDTO> boardDTOList = boardRepository.searchList(pagingParams);
        return boardDTOList;
    }

    public PageDTO pagingSearchParam(int page, String type, String q) {
        int pageLimit = 3;
        int blockLimit = 3;
        Map<String, Object> pagingParams = new HashMap<>();
        pagingParams.put("q", q);
        pagingParams.put("type", type);
        int boardCount = boardRepository.boardSearchCount(pagingParams);  // <-- 페이징처리 메서드에서 여기만 바뀌었다
        int maxPage = (int) (Math.ceil((double)boardCount / pageLimit));
        int startPage = (((int)(Math.ceil((double) page / blockLimit))) - 1) * blockLimit + 1;
        int endPage = startPage + blockLimit - 1;
        if (endPage > maxPage) {
            endPage = maxPage;
        }
        PageDTO pageDTO = new PageDTO();
        pageDTO.setPage(page);
        pageDTO.setMaxPage(maxPage);
        pageDTO.setEndPage(endPage);
        pageDTO.setStartPage(startPage);
        return pageDTO;
    }
}
