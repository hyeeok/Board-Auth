package com.greta.masa.board.entity;

import com.greta.masa.auth.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "board_prediction")
@Getter
@Setter
public class BoardPrediction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "board_prediction_id")
    private Long boardPredictionId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

}
