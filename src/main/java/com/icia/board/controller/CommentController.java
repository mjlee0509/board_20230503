package com.icia.board.controller;

import com.icia.board.dto.CommentDTO;
import com.icia.board.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

//    @GetMapping("/save")
//    public String saveForm() {
//        return "commentPages/commentSave";
//    }

    //댓글기능구현 Step 2. Controller 작성. ResponEntity와 이걸 어떻게 리턴하는지 유심히 볼 것!
    @PostMapping("/save")
    public ResponseEntity save(@ModelAttribute CommentDTO commentDTO) {
//        int saveResult = commentService.save(commentDTO);
//        if (saveResult > 0) {
//            return "commentPages/commentList";
//        }
//        return "index";
        System.out.println("commentDTO = " + commentDTO);
        commentService.save(commentDTO);
        // 댓글기능구현 Step 3. 여기서 findAll()까지 같이 받아주자
            // 주의! id 받아와야 한다
        List<CommentDTO> commentDTOList = commentService.findAll(commentDTO.getBoardId());
        return new ResponseEntity<>(commentDTOList, HttpStatus.OK);   // 아..! 이걸로 리턴을 받아야했구나
    }

//    @GetMapping("/list")
//    public String list(Model model) {
//        List<CommentDTO> commentDTOList = commentService.findAll();
//        model.addAttribute("commentList", commentDTOList);
//        return "commentPages/commentList";
//
//    }

}
