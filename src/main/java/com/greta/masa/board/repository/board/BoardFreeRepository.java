package com.greta.masa.board.repository.board;

import com.greta.masa.board.entity.BoardFree;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardFreeRepository extends JpaRepository<BoardFree,Long> {
    Page<BoardFree> findByTitleContaining(String searchKeyword, Pageable pageable);
}
