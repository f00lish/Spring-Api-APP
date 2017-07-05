package com.icy9.repository;

import com.icy9.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

/**
 * Created by f00lish on 2017/7/4.
 * Qun:530350843
 */
public interface UserRepository extends CrudRepository<User, Long> {

    Collection<User> findAll();

    User findByUsername(String username);

    String findPasswordByUsername(String username);

    Page<User> findAll(Pageable p);

    Page<User> findByUsernameContaining(String searchPhrase, Pageable p);
}
