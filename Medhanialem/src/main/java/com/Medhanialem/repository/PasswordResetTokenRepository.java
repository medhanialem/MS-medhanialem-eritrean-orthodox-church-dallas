package com.Medhanialem.repository;

import org.springframework.data.repository.CrudRepository;

import com.Medhanialem.jwtauthentication.model.PasswordResetTokenEntity;

public interface PasswordResetTokenRepository  extends CrudRepository<PasswordResetTokenEntity, Long>{

	PasswordResetTokenEntity findByToken(String token);

}
