package org.baeldung.repository;

import org.baeldung.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Integer> {

    int countByStatus(int status);

    Optional<User> findOneByName(String name);

    Stream<User> findAllByName(String name);

    @Async
    CompletableFuture<User> findOneByStatus(Integer status);

    @Query("SELECT u FROM User u WHERE u.status = 1")
    Collection<User> findAllActiveUsers();

    @Query(value = "SELECT * FROM USERS u WHERE u.status = 1", nativeQuery = true)
    Collection<User> findAllActiveUsersNative();

    @Query("SELECT u FROM User u WHERE u.status = ?1")
    User findUserByStatus(Integer status);

    @Query("SELECT u FROM User u WHERE u.status = ?1 and u.name = ?2")
    User findUserByStatusAndName(Integer status, String name);

    @Query("SELECT u FROM User u WHERE u.status = :status and u.name = :name")
    User findUserByStatusAndNameNamedParams(@Param("status") Integer status, @Param("name") String name);

    @Query("SELECT u FROM User u WHERE u.status = :status and u.name = :name")
    User findUserByUserStatusAndUserName(@Param("status") Integer userStatus, @Param("name") String userName);

    @Query("SELECT u FROM User u WHERE u.name like ?1%")
    User findUserByNameLike(String name);

    @Query("SELECT u FROM User u WHERE u.name like :name%")
    User findUserByNameLikeNamedParam(@Param("name") String name);

    @Query(value = "SELECT * FROM users u WHERE u.name LIKE ?1%", nativeQuery = true)
    User findUserByNameLikeNative(String name);

    @Query(value = "SELECT u FROM User u")
    List<User> findAllUsers(Sort sort);

    @Query(value = "SELECT * FROM Users ORDER BY id \n-- #pageable\n", countQuery = "SELECT count(*) FROM Users", nativeQuery = true)
    Page<User> findAllUsersWithPagination(Pageable pageable);

    @Modifying
    @Query("update User u set u.status = :status where u.name = :name")
    int updateUserSetStatusForName(@Param("status") Integer status, @Param("name") String name);

}
