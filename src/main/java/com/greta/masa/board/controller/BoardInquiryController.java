package com.greta.masa.board.controller;

import com.greta.masa.auth.entity.User;
import com.greta.masa.auth.entity.UserSession;
import com.greta.masa.board.entity.BoardFree;
import com.greta.masa.board.entity.BoardInquiry;
import com.greta.masa.board.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BoardInquiryController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/board/inquiry/write")
    public String boardWriteForm(HttpServletRequest request, Model model) {
        // 세션에서 사용자 정보 가져오기
        HttpSession session = request.getSession();
        UserSession userSession = (UserSession) session.getAttribute("userSession");

        // 세션이 없을 경우 로그인 페이지로 리다이렉트
        if (userSession == null) {
            model.addAttribute("message", "로그인하셔야 이용할 수 있습니다.");
            model.addAttribute("searchUrl", "/login");
            return "util/message";
        }
        // 세션이 있는 경우에는 글 작성 폼을 보여줌
        return "board/inquiry/BoardInquiryWrite";
    }

    @PostMapping("/board/inquiry/writepro")
    public String boardWritePro(BoardInquiry boardInquiry, Model model, HttpSession session) {
        // 세션에서 사용자 정보 가져오기
        UserSession userSession = (UserSession) session.getAttribute("userSession");

        if (userSession != null) {
            // User 엔터티의 객체 생성
            User user = new User();
            // User 엔터티의 userId 설정
            user.setUserId(userSession.getUserId());
            // BoardInquiry 엔터티의 user 필드에 User 객체 설정
            boardInquiry.setUser(user);

            // 글 작성
            boardService.boardInquiryWrite(boardInquiry);

            model.addAttribute("message", "글 작성이 완료되었습니다.");
            model.addAttribute("searchUrl", "/board/inquiry");

            return "util/message";
        } else {
            // 세션에 사용자 정보가 없을 경우에 대한 처리
            model.addAttribute("message", "잘못된 접근입니다.");
            model.addAttribute("searchUrl", "/login"); // 로그인 페이지로 이동하도록 설정하거나 다른 처리를 하십시오.

            return "util/message";
        }
    }

    @GetMapping("/board/inquiry")
    public String boardInquiry(Model model,
                               @PageableDefault(page = 0, size = 25, sort = "boardInquiryId", direction = Sort.Direction.DESC) Pageable pageable,
                               @RequestParam(value = "searchKeyword", required = false) String searchKeyword){

        Page<BoardInquiry> list = null;

        if(searchKeyword == null){
            list = boardService.boardInquiryList(pageable);
        }else{
            list = boardService.boardInquirySearchList(searchKeyword, pageable);
        }

        int nowPage = list.getPageable().getPageNumber() + 1;
        int endPage = list.getTotalPages();

        int groupSize = 5; // 페이지 그룹 크기 설정
        int nowPageGroup = (nowPage - 1) / groupSize;
        int startPageGroup = nowPageGroup * groupSize + 1;
        int endPageGroup = Math.min(startPageGroup + groupSize - 1, endPage);

        model.addAttribute("list",list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("startPageGroup", startPageGroup);
        model.addAttribute("endPageGroup", endPageGroup);

        return "board/inquiry/BoardInquiryList";
    }

    @GetMapping("/board/inquiry/view")
    public String boardView(@RequestParam("boardInquiryId") Long boardInquiryId, Model model, HttpSession session) {

        UserSession userSession = (UserSession) session.getAttribute("userSession");

        BoardInquiry boardInquiry = boardService.boardInquiryView(boardInquiryId);

        model.addAttribute("userSession", userSession);
        model.addAttribute("boardInquiry", boardInquiry);

        return "board/inquiry/BoardInquiryView";
    }

    @GetMapping("/board/inquiry/delete")
    public String boardDelete(@RequestParam("boardInquiryId") Long boardInquiryId, Model model) {

        boardService.boardInquiryDelete(boardInquiryId);

        model.addAttribute("message", "삭제 완료하였습니다.");
        model.addAttribute("searchUrl", "/board/inquiry");
        return "util/message";
    }

    @GetMapping("/board/inquiry/modify/{boardInquiryId}")
    public String boardModify(@PathVariable("boardInquiryId") Long boardInquiryId, Model model) {
        model.addAttribute("boardInquiry", boardService.boardInquiryView(boardInquiryId));
        return "board/inquiry/BoardInquiryModify";
    }

    @PostMapping("/board/inquiry/update/{boardInquiryId}")
    public String boardUpdate(@PathVariable("boardInquiryId") Long boardInquiryId, BoardInquiry boardInquiry, Model model) {
        BoardInquiry boardTemp = boardService.boardInquiryView(boardInquiryId);
        boardTemp.setTitle(boardInquiry.getTitle());
        boardTemp.setContent(boardInquiry.getContent());

        boardService.boardInquiryWrite(boardTemp);

        model.addAttribute("message", "수정 완료하였습니다.");
        model.addAttribute("searchUrl", "/board/inquiry");

        return "util/message";
    }
}