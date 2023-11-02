package com.mohamed.post.model;

import java.util.Date;

public record SignupResponse(String kind, String localId, String email,
                             String displayName, String token, Boolean registered,
                             String refreshToken, Date expirationDate) {
}
