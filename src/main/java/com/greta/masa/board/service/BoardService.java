package com.greta.masa.board.service;

import com.greta.masa.board.entity.BoardFree;
import com.greta.masa.board.entity.BoardInquiry;
import com.greta.masa.board.entity.BoardPrediction;
import com.greta.masa.board.repository.board.BoardFreeRepository;
import com.greta.masa.board.repository.board.BoardInquiryRepository;
import com.greta.masa.board.repository.board.BoardPredictionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    private BoardFreeRepository boardFreeRepogitory;

    @Autowired
    private BoardInquiryRepository boardInquiryRepogitory;

    @Autowired
    private BoardPredictionRepository boardPredictionRepository;

    //자유 게시판
    public void boardFreeWrite(BoardFree boardFree) {
        boardFreeRepogitory.save(boardFree);
    }

    public Page<BoardFree> boardFreeList(Pageable pageable) {
        return boardFreeRepogitory.findAll(pageable);
    }

    public Page<BoardFree> boardFreeSearchList(String searchKeyword, Pageable pageable){
        return boardFreeRepogitory.findByTitleContaining(searchKeyword, pageable);
    }

    public BoardFree boardFreeView(Long boardFreeId) {
        return boardFreeRepogitory.findById(boardFreeId).get();
    }

    public void boardFreeDelete(Long boardFreeId){
        boardFreeRepogitory.deleteById(boardFreeId);
    }

    //문의 게시판
    public void boardInquiryWrite(BoardInquiry boardInquiry) {
        boardInquiryRepogitory.save(boardInquiry);
    }

    public Page<BoardInquiry> boardInquiryList(Pageable pageable) {
        return boardInquiryRepogitory.findAll(pageable);
    }

    public Page<BoardInquiry> boardInquirySearchList(String searchKeyword, Pageable pageable){
        return boardInquiryRepogitory.findByTitleContaining(searchKeyword, pageable);
    }

    public BoardInquiry boardInquiryView(Long boardInquiryId) {
        return boardInquiryRepogitory.findById(boardInquiryId).get();
    }

    public void boardInquiryDelete(Long boardInquiryId){
        boardInquiryRepogitory.deleteById(boardInquiryId);
    }

    //예측 게시판
    public void boardPredictionWrite(BoardPrediction boardPrediction) {
        boardPredictionRepository.save(boardPrediction);
    }

    public Page<BoardPrediction> boardPredictionList(Pageable pageable) {
        return boardPredictionRepository.findAll(pageable);
    }

    public Page<BoardPrediction> boardPredictionSearchList(String searchKeyword, Pageable pageable){
        return boardPredictionRepository.findByTitleContaining(searchKeyword, pageable);
    }
    public BoardPrediction boardPredictionView(Long boardPredictionId) {
        return boardPredictionRepository.findById(boardPredictionId).get();
    }

    public void boardPredictionDelete(Long boardPredictionId){
        boardPredictionRepository.deleteById(boardPredictionId);
    }

}
