package com.many.miniproject1.scrap;

import com.many.miniproject1.post.Post;
import com.many.miniproject1.resume.Resume;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface ScrapJPARepository extends JpaRepository<Scrap, Integer> {

//    @Modifying
//    @Query("delete from Scrap s where s.post.id = :post_id and s.resume.user.id= :resume_user_id")
//    void deleteScrapByPostId(@Param("post_id") Integer postId, @Param("resume_user_id") Integer resumeUserId);

    @Query("""
            select s
            from Scrap s
            join fetch s.post p
            join fetch p.skillList ps
            where s.user.id = :user_id
            """)
    List<Scrap> findByPostIdJoinskills(@Param("user_id") Integer userId);

    @Query("""
            SELECT DISTINCT s
            from Scrap s
            JOIN FETCH s.resume r
            JOIN FETCH r.user ru
            JOIN FETCH r.skillList rs
            JOIN FETCH s.user u
            WHERE u.id = :user_id and r.id=:resume_id
            """)
    Optional<Scrap> findByResumeIdAndSkillAndUser(@Param("user_id") Integer userId, @Param("resume_id") Integer resumeId);

    @Query("""
            select s
            from Scrap s
            JOIN FETCH s.resume r
            JOIN FETCH r.skillList rs
            join FETCH r.user ru
            JOIN FETCH s.user u
            where u.id = :user_id
            """)
    List<Scrap> findByUserIdJoinSkillAndResume(@Param("user_id") Integer userId);

    @Query("""
            select s
            from Scrap s
            join fetch s.post p
            join fetch p.skillList ps
            where s.id =:scrap_id
                        """)
    Scrap findByIdJoinPostAndSkill(@Param("scrap_id") Integer scrapId);

}
