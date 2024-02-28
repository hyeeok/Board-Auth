package com.greta.masa;

import com.greta.masa.board.entity.BoardFree;
import com.greta.masa.board.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/")
    public String main(Model model,
                       @PageableDefault(page = 0, size = 10, sort = "boardFreeId", direction = Sort.Direction.DESC) Pageable pageable,
                       @RequestParam(value = "searchKeyword", required = false) String searchKeyword){

        Page<BoardFree> list = null;

        if(searchKeyword == null){
            list = boardService.boardFreeList(pageable);
        }else{
            list = boardService.boardFreeSearchList(searchKeyword, pageable);
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

        return "Main";
    }
}
