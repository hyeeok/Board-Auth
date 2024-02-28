package com.greta.masa.board.repository.board;

import com.greta.masa.board.entity.BoardFree;
import com.greta.masa.board.entity.BoardPrediction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardPredictionRepository extends JpaRepository<BoardPrediction,Long> {
    Page<BoardPrediction> findByTitleContaining(String searchKeyword, Pageable pageable);
}
