package com.greta.masa.board.entity;

import com.greta.masa.auth.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "board_inquiry")
@Getter
@Setter
public class BoardInquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "board_inquiry_id")
    private Long boardInquiryId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

}
