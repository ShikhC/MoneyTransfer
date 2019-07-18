package com.moneytransfer.demo.data.models.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {
    private String name;
    private List<String> accountIds;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isActive;

}
