package com.natorigatto.course.entities;

import com.natorigatto.course.entities.enums.UserRole;

public record RegisterDTO(String name, String email, String phone, String login, String password, UserRole userRole) {
}
