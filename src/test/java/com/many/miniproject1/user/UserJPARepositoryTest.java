package com.many.miniproject1.user;

import jakarta.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import java.sql.Date;

import static org.assertj.core.api.Assertions.*;


@DataJpaTest
public class UserJPARepositoryTest {
    @Autowired
    private UserJPARepository userJPARepository;
    @Autowired
    private EntityManager em;

    @Test
    public void findById_test() {
        // given
        int id = 1;

        // when
        Optional<User> userOP = userJPARepository.findById(id);

        if (userOP.isPresent()) {
            User user = userOP.get();
        }

        // then



    }
    public void findByUsernameAndPassword_test () {
        // given
        String username = "captain_kong";
        String password = "1234";
        // when
        User user = userJPARepository.findByUsernameAndPassword(username, password).get();
        // then
        assertThat(user.getAddress()).isEqualTo("부산광역시");
    }
    @Test
    public void save_test () {
        //given
        User user = User.builder()
                .role("person")
                .username("ssar2")
                .name("최주호")
                .email("ssar2@nate.com")
                .birth(Date.valueOf("1876-02-01"))
                .tel("010-1234-5678")
                .address("부산시 부산진구")
                .password("1234")
                .build();

        // when
        userJPARepository.save(user);

        // then
        assertThat(user.getRole()).isEqualTo("person");
    }
}
