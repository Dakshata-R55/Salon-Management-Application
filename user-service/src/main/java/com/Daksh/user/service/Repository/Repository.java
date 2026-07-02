package com.Daksh.user.service.Repository;

import com.Daksh.user.service.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repository extends JpaRepository<User, Long> {

}
