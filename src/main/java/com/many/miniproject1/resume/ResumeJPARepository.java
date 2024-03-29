package com.many.miniproject1.resume;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ResumeJPARepository extends JpaRepository<Resume, Integer> {

    @Query("""
            select distinct r
            from Resume r
            join fetch r.skillList s
            join fetch r.user u
            where r.id = :id
            """)
    Resume findByIdJoinSkillAndUser(@Param("id") int id);

    @Query("select r from Resume r join fetch r.skillList s where r.id = :id")
    Resume findByIdJoinSkill(@Param("id") int id);

    List<Resume> findByUserId(Integer userId);

    @Query("""
            select distinct r
            from Resume r
            join fetch r.skillList s
            join fetch r.user u
            where u.id = :id
            """)
    List<Resume> findByuserIdJoinSkillAndUser(@Param("id") int id);

    @Query("""
            select r
            from  Resume r
            join User u on r.user.id = u.id
            where r.user.id =:resume_user_id AND u.id=:user_id
            """)
    List<Resume> findByResumeUserIdAndSessionUserId(@Param("resume_user_id") Integer resumeUserId, @Param("user_id") Integer userId);
}
