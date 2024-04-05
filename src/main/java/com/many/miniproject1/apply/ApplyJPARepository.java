package com.many.miniproject1.apply;

import com.many.miniproject1.post.Post;
import com.many.miniproject1.resume.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ApplyJPARepository extends JpaRepository<Apply, Integer> {

    @Modifying
    @Query("delete from Apply a where a.id = :applyId")
    void deleteApplyPostById(@Param("applyId") Integer applyId);

    @Query("""

            select a 
            from Apply a 
            join fetch a.resume r 
            join fetch a.post p 
            join fetch r.user ru 
            join fetch p.user pu 
            where p.user.id = :user_id
            """)
    List<Apply> findByUserIdJoinPost(@Param("user_id") int userId);

    @Query("""
            SELECT DISTINCT a
            FROM Apply a
            JOIN FETCH a.resume r
            JOIN FETCH r.skillList s
            JOIN FETCH r.user ru
            JOIN FETCH a.post p
            JOIN p.user pu
            WHERE a.id = :id
            """)
    Apply findByResumeIdJoinSkillAndCompany(@Param("id") Integer id);


    @Query("""
            SELECT a
            FROM Apply a
            JOIN FETCH a.post p
            JOIN FETCH p.skillList s
            WHERE a.resume.user.id = :userId
            """)
    List<Apply> findAllAppliesWithPostsAndSkills(@Param("userId") Integer userId);


    @Query("""

            select distinct a from Apply a join fetch a.post p join fetch p.user pu join fetch p.skillList join fetch a.resume r join fetch r.user ru where p.id= :postId and ru.id=:resumeUserId
            """)
    Optional<Apply> findByPostIdJoinPostAndSkillAndUser(@Param("postId") Integer postId, @Param("resumeUserId") Integer resumeUserId);

    @Modifying
    @Query("""
            delete from Apply a 
            where a.resume.id = :resume_id
                    """)
    void deleteByResumeId(@Param("resume_id") Integer resumeId);

    @Modifying
    @Query("""
            delete from Apply a 
            where a.post.id = :post_id
                    """)
    void deleteByPostId(@Param("post_id") Integer postId);

    boolean existsByResumeAndPost(Resume resume, Post post);

}
