package com.mohamed.post.model;

import java.util.Date;

public record LoginResponse (String email, String token, String localId, Date expirationDate){
}
