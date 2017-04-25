package com.thanos.springboot.dao;

import com.thanos.springboot.bo.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by solarknight on 2017/4/25.
 */
@RepositoryRestResource
public interface UserRepository extends CrudRepository<User, Long> {
}
