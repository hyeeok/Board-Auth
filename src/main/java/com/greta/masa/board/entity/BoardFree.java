package com.greta.masa.board.entity;

import com.greta.masa.auth.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "board_free")
@Getter
@Setter
public class BoardFree {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "board_free_id")
    private Long boardFreeId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Transient // 데이터베이스와 매핑하지 않는 임시 필드
    private String nickName; // 닉네임 필드 추가

}
