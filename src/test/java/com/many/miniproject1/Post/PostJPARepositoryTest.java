package com.many.miniproject1.Post;


import com.many.miniproject1.post.Post;
import com.many.miniproject1.post.PostJPARepository;
import com.many.miniproject1.post.PostRequest;
import com.many.miniproject1.post.PostService;
import com.many.miniproject1.resume.*;
import com.many.miniproject1.user.User;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class PostJPARepositoryTest {
    @Autowired
    private PostJPARepository postJPARepository;
    @Autowired
    private EntityManager em;

    @Test
    public void findById_test() {
        // given
        PostRequest.UpdateDTO reqDTO = new PostRequest.UpdateDTO();
        reqDTO.setId(1);

        // when
        Optional<Post> post = postJPARepository.findById(reqDTO.getId());

        // then
        post.ifPresent(value -> Assertions.assertThat(value.getTitle()).isEqualTo("데이터 분석가"));
    }
}
