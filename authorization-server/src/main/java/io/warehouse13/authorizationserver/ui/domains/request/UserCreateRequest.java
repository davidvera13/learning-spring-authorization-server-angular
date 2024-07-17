package io.warehouse13.authorizationserver.ui.domains.request;

import lombok.*;

import java.util.List;


public record UserCreateRequest (String username, String password, List<String> roles) {}
