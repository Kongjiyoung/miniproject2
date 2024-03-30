package com.many.miniproject1.apply;

import com.many.miniproject1.post.Post;
import com.many.miniproject1.resume.Resume;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;


@NoArgsConstructor
@Table(name = "apply_tb")
@Data
@Entity // 테이블 생성하기 위해 필요한 어노테이션
public class Apply { // 지원 테이블
    @Id // PK 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment 전략
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Resume resume; // 이력서 아이디
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post; // 채용공고 아이디

    private String isPass= "검토중"; // 불합격, 합격, 검토중
    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public Apply(Integer id, Resume resume, Post post, String isPass, Timestamp createdAt) {
        this.id = id;
        this.resume = resume;
        this.post = post;
        this.isPass = isPass!= null ? isPass : "검토중";
        this.createdAt = createdAt;
    }
}
