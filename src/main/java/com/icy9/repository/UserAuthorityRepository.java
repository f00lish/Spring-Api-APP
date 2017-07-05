package com.icy9.repository;

import com.icy9.entity.UserAuthority;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by f00lish on 2017/7/4.
 * Qun:530350843
 */
public interface UserAuthorityRepository extends CrudRepository<UserAuthority, Long> {

    List<UserAuthority> findByUsername(String username);
}
