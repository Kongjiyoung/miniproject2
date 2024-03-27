package com.many.miniproject1.post;

import com.many.miniproject1._core.errors.exception.Exception403;
import com.many.miniproject1._core.errors.exception.Exception404;
import com.many.miniproject1.resume.Resume;
import com.many.miniproject1.skill.Skill;
import com.many.miniproject1.skill.SkillJPARepository;
import com.many.miniproject1.skill.SkillRequest;
import com.many.miniproject1.skill.SkillResponse;
import com.many.miniproject1.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import java.util.List;


@RequiredArgsConstructor
@Service
public class PostService {
    private final PostJPARepository postJPARepository;
    private final PostQueryRepository postQueryRepository;
    private final SkillJPARepository skillJPARepository;

    @Transactional
    public Post save(PostRequest.PostSaveDTO reqDTO, User sessionUser) {
        Post post = postJPARepository.save(reqDTO.toEntity(sessionUser));

        List<Skill> skills = new ArrayList<>();
        for (String skillName : reqDTO.getSkill()) {
            SkillResponse.SaveDTO skill = new SkillResponse.SaveDTO();
            skill.setSkill(skillName);
            skills.add(skill.toEntity());
        }

        List<Skill> skillList = skillJPARepository.saveAll(skills);
        return post;
    }

    public List<Post> getResumeList(Integer userId) {
        return postJPARepository.findByUserIdJoinSkillAndUser(userId);
    }

    @Transactional
    public Post updatePost(int postId, int sessionUserId, PostRequest.UpdateDTO reqDTO) {
        // 1. 이력서 찾기
        Post post = postJPARepository.findById(postId)
                .orElseThrow(() -> new Exception404("공고를 찾을 수 없습니다."));

        // 2. 권한 처리
        if (sessionUserId != post.getUser().getId()) {
            throw new Exception403("공고를 수정할 권한이 없습니다");
        }

        // 3. 이력서 업데이트
        post.setTitle(reqDTO.getTitle());
        post.setCareer(reqDTO.getCareer());
        post.setPay(reqDTO.getPay());
        post.setWorkStartTime(reqDTO.getWorkStartTime());
        post.setWorkEndTime(reqDTO.getWorkEndTime());
        post.setDeadline(reqDTO.getDeadline());
        post.setTask(reqDTO.getTask());
        post.setWorkingArea(reqDTO.getWorkingArea());
        post.setWorkCondition(reqDTO.getWorkCondition());

        // 4. 스킬 업데이트
        List<Skill> skills = skillJPARepository.findSkillsByPostId(post.getId());
        for (Skill skill : skills) {
            skillJPARepository.deleteSkillsByPostId(post.getId());
        }
        List<Skill> skills1 = new ArrayList<>();
        for (String skillName : reqDTO.getSkills()) {
            SkillRequest.UpdatePostSkillsDTO skill = new SkillRequest.UpdatePostSkillsDTO();
            skill.setPost(post);
            skill.setSkill(skillName);
            skills1.add(skill.toEntity());
        }

        List<Skill> skillList = skillJPARepository.saveAll(skills1);
        return post;
    }
}
