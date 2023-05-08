package com.icia.board.controller;

import com.icia.board.dto.BoardDTO;
import com.icia.board.dto.BoardFileDTO;
import com.icia.board.dto.CommentDTO;
import com.icia.board.service.BoardService;
import com.icia.board.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/board")       // <--공통주소는 밖으로 빼주도록 하자
public class BoardController {

    @Autowired
    private BoardService boardService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/save")
    public String saveForm() {

        return "boardPages/boardSave";
    }
    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO) throws IOException {
        // 여기서도 예외 떠넘기기 시전; 그러면 servlet에서 알아서 처리하겠지ㅇㅇ
        boardService.save(boardDTO);
        return "redirect:/board/list";
    }
    @GetMapping("/list")
    public String findAll(Model model) {
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        return "boardPages/boardList";
    }
    @GetMapping
    public String detail(@RequestParam("id") Long id, Model model) {
        //조회할 때 조회수 1씩 증가하는 메서드
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        // 5-2. 파일이 있는 경우에만 파일 이름 가져오기
        if(boardDTO.getFileAttached()==1) {
            List<BoardFileDTO> boardFileDTO = boardService.findFile(id);
            model.addAttribute("boardFileList", boardFileDTO);
            System.out.println("boardFileDTO = " + boardFileDTO);
        }
        // 댓글기능구현 Step 5. 댓글을 작성하지 않았을 때에도 댓글 리스트를 화면에 띄우기
        List<CommentDTO> commentDTOList = commentService.findAll(id);
        if (commentDTOList.size() == 0) {
            model.addAttribute("commentList", null);
        } else {
            model.addAttribute("commentList", commentDTOList);
        }
        return "boardPages/boardDetail";
    }
    @GetMapping("/delete-check")
    public String deleteCheck(@RequestParam("id") Long id, Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        return "boardPages/deleteCheck";
    }
    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        boardService.delete(id);
        return "redirect:/board/list";
    }
    @GetMapping("/update")
    public String updateForm(@RequestParam("id") Long id, Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        return "boardPages/boardUpdate";
    }
    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model) {
        boardService.update(boardDTO);
//        return "rdirect:/board?id="+boardDTO.getId(); // <-- 이렇게 하니까 수정했더니 조회수가 올라가네?
        BoardDTO dto = boardService.findById(boardDTO.getId());
        model.addAttribute("board", dto);
        return "boardPages/boardDetail";
    }




}
