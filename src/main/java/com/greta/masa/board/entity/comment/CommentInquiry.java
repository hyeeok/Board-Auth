package com.greta.masa.board.entity;

import com.greta.masa.auth.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "comment_inquiry")
@Getter
@Setter
public class CommentInquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "comment_inquiry_id")
    private Long commentInquiryId;

    @ManyToOne
    @JoinColumn(name = "board_inquiry_id", nullable = false)
    private BoardInquiry boardInquiryId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

}
