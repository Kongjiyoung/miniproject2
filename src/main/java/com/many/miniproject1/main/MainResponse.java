package com.many.miniproject1.main;

import com.many.miniproject1.post.Post;
import com.many.miniproject1.skill.Skill;
import com.many.miniproject1.user.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

public class MainResponse {

    @Data
    public static class PostSkillDTO{
        private Integer id;
        private String profile;
        private String title;
        private String task;
        private String career;
        private String workingArea;
        private List<SkillDTO> skillList;

        @Builder
        public PostSkillDTO(Post post, List<Skill> skillList) {
            this.id = post.getId();
            this.profile = post.getProfile();
            this.title = post.getTitle();
            this.task = post.getTask();
            this.career = post.getCareer();
            this.workingArea = post.getWorkingArea();

            this.skillList = skillList.stream().map(skill -> { // 이 skill은 skillList의 각 객체 Skill 엔티티이다.
                return new SkillDTO(skill);
            }).collect(Collectors.toList());
        }
        @Data
        public static class SkillDTO{
            private Integer id;
            private String skill;

            public SkillDTO(Skill skill) {
                this.id = skill.getId();
                this.skill = skill.getSkill();
            }
        }
    }



    @Data
    public static class ResumeSkillDTO {
        int resumeId;
        int score;

        public ResumeSkillDTO(int resumeId, int i) {
            this.resumeId = resumeId;
            this.score = i;
        }

    }
}
