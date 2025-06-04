package com.TaskLogger.logger.entity;

import com.TaskLogger.logger.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "daily_entry")
public class DailyEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Override
    public String toString() {
        return "DailyEntry{" +
                "id=" + id +
                ", date=" + date +
                ", userId=" + (user != null ? user.getId() : null) +
                '}';
    }

}
