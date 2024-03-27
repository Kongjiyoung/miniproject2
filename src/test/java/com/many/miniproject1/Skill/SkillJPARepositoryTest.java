package com.many.miniproject1.Skill;

import com.many.miniproject1.post.PostRequest;
import com.many.miniproject1.resume.Resume;
import com.many.miniproject1.resume.ResumeJPARepository;
import com.many.miniproject1.skill.Skill;
import com.many.miniproject1.skill.SkillJPARepository;
import com.many.miniproject1.user.User;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class SkillJPARepositoryTest {
    @Autowired
    private SkillJPARepository skillJPARepository;
    @Autowired
    private EntityManager em;

    @Test
    public void save_test() {
        // given
        Resume resume = Resume.builder().id(1).build();
        List<Skill> skills = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Skill skill = Skill.builder()
                    .skill("JAVA")
                    .resume(resume)
                    .build();
            skills.add(skill);
        }
        // when
        skillJPARepository.saveAll(skills);

        // then
        System.out.println("skills = " + skillJPARepository.findAll());
    }

    @Test
    public void findSkillsByPostId_test() {
        // given
        PostRequest.UpdateDTO reqDTO = new PostRequest.UpdateDTO();
        reqDTO.setId(1);

        // when
        List<Skill> skills = skillJPARepository.findSkillsByPostId(reqDTO.getId());

        // then
        Assertions.assertThat(skills.getFirst().getSkill()).isEqualTo("JAVA");
    }
}
