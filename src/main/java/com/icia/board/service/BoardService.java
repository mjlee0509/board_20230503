package com.icia.board.service;

import com.icia.board.dto.BoardDTO;
import com.icia.board.dto.BoardFileDTO;
import com.icia.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {
    @Autowired
    public BoardRepository boardRepository;
    public void save(BoardDTO boardDTO) throws IOException {
        // 파일이 있을때 or 없을때 구분해야 함
        if(boardDTO.getBoardFile().isEmpty()) {
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
            */
            System.out.println("파일유");
            boardDTO.setFileAttached(1);
            BoardDTO dto = boardRepository.save(boardDTO);
            // 원본파일 이름 가져오기
            String originalFileName = boardDTO.getBoardFile().getOriginalFilename();
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
            boardDTO.getBoardFile().transferTo(new File(savePath));
            boardRepository.saveFile(boardFileDTO);
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

    public BoardFileDTO findFile(Long id) {
        return boardRepository.findFile(id);
    }
}
