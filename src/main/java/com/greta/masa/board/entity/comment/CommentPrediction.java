package com.greta.masa.board.entity;

import com.greta.masa.auth.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "comment_prediction")
@Getter
@Setter
public class CommentPrediction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "comment_prediction_id")
    private Long commentPredictionId;

    @ManyToOne
    @JoinColumn(name = "board_prediction_id", nullable = false)
    private BoardPrediction boardPredictionId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

}
