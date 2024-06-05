package com.williamblondel.infinitrade.repository;

import com.williamblondel.infinitrade.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}