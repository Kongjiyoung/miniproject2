package com.many.miniproject1.scrap;

import com.many.miniproject1.offer.OfferResponse;
import com.many.miniproject1.resume.Resume;
import com.many.miniproject1.skill.Skill;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScrapResponse {

    @Data
    public static class MainResumeScrapDTO{
        private int userId;
        private int resumeId;

        public MainResumeScrapDTO(Scrap scrap) {
            this.userId = scrap.getUser().getId();
            this.resumeId = scrap.getResume().getId();
        }
    }

    @Data
    public static class PostScrapSaveDTO{
        private int userId;
        private int postId;

        public PostScrapSaveDTO(Scrap scrap) {
            this.userId = scrap.getUser().getId();
            this.postId = scrap.getPost().getId();
        }
    }

    @Data
    public static class ScarpPostDTO {
        private Integer id;
        private Integer companyId;
        private String title;
        private String career;
        private String pay;
        private String workCondition;
        private String workStartTime;
        private String workEndTime;
        private String deadline;
        private String task;
        private String profile;
        private String workingArea;
        private String isPass;
        private Timestamp createdAt;
    }

    @Data
    public static class ScarpPostSkillDTO {
        private Integer id;
        private Integer companyId;
        private String title;
        private String career;
        private String pay;
        private String workCondition;
        private String workStartTime;
        private String workEndTime;
        private String deadline;
        private String task;
        private String profile;
        private String workingArea;
        private List<String> skill;
        private Timestamp createdAt;
//

        public ScarpPostSkillDTO(ScrapResponse.ScarpPostDTO post, List<String> skills) {
            this.id = post.getId();
            this.companyId = post.getCompanyId();
            this.title = post.getTitle();
            this.career = post.getCareer();
            this.pay = post.getPay();
            this.workCondition = post.getWorkCondition();
            this.workStartTime = post.getWorkStartTime();
            this.workEndTime = post.getWorkEndTime();
            this.deadline = post.getDeadline();
            this.task = post.getTask();
            this.profile = post.getProfile();
            this.workingArea = post.getWorkingArea();
            this.skill = skills;
            this.createdAt = post.getCreatedAt();
        }
    }

    @Data
    public static class ScrapPostDetailDTO {
        private Integer id;
        private Integer companyId;
        private String companyName;
        private String title;
        private String career;
        private String pay;
        private String workCondition;
        private String workStartTime;
        private String workEndTime;
        private String deadline;
        private String task;
        private String profile;
        private String workingArea;
        private List<PostSkillDTO> skillList;

        public ScrapPostDetailDTO(Scrap scrap) {
            this.id = scrap.getId();
            this.companyId = scrap.getPost().getUser().getId();
            this.companyName = scrap.getPost().getUser().getCompanyName();
            this.title = scrap.getPost().getTitle();
            this.career = scrap.getPost().getCareer();
            this.pay = scrap.getPost().getPay();
            this.workCondition = scrap.getPost().getWorkCondition();
            this.workStartTime = scrap.getPost().getWorkStartTime();
            this.workEndTime = scrap.getPost().getWorkEndTime();
            this.deadline = scrap.getPost().getDeadline();
            this.task = scrap.getPost().getTask();
            this.profile = scrap.getPost().getProfile();
            this.workingArea = scrap.getPost().getWorkingArea();
            this.skillList = scrap.getPost().getSkillList().stream().map(skill -> new ScrapPostDetailDTO.PostSkillDTO(skill)).toList();
        }

        @Data
        public static class PostSkillDTO {
            private Integer id;
            private String skill;

            public PostSkillDTO(Skill skill) {
                this.id = skill.getId();
                this.skill = skill.getSkill();
            }
        }
    }

    @Data
    public static class ScrapResumeDetailDTO {
        private Integer id;
        private Resume resume;
        private Integer personId;
        private String title;
        private String profile;
        private String portfolio;
        private String introduce;
        private String career;
        private String simpleIntroduce;
        private Timestamp createdAt;
        private String email;
        private String name;
        private String tel;
        private String address;
        private Date birth;
        private String isPass;
        private List<ResumeSkillDTO> skillList;

        public ScrapResumeDetailDTO(Scrap scrap) {
            this.id = scrap.getId();
            this.resume = getResume();
            this.personId = scrap.getResume().getUser().getId();
            this.title = scrap.getResume().getTitle();
            this.profile = scrap.getResume().getUser().getProfile();
            this.portfolio = scrap.getResume().getPortfolio();
            this.introduce = scrap.getResume().getIntroduce();
            this.career = scrap.getResume().getCareer();
            this.simpleIntroduce = scrap.getResume().getSimpleIntroduce();
            this.email = scrap.getResume().getUser().getEmail();
            this.name = scrap.getResume().getUser().getName();
            this.tel = scrap.getResume().getUser().getTel();
            this.address = scrap.getResume().getUser().getAddress();
            this.birth = scrap.getResume().getUser().getBirth();
            this.isPass = getIsPass();
            this.skillList=scrap.getResume().getSkillList().stream().map(skill -> new ScrapResumeDetailDTO.ResumeSkillDTO(skill)).toList();
        }

        @Data
        public static class ResumeSkillDTO{
            private Integer id;
            private String skill;

            public ResumeSkillDTO(Skill skill){
                this.id = skill.getId();
                this.skill=skill.getSkill();
            }
        }
    }


    @Data
    public static class ScrapPostListDTO {
        private Integer id;
        private String companyName;
        private String title;
        private String career;
        private String task;
        private String profile;
        private String workingArea;
        private List<PostSkillDTO> skillList;

        public ScrapPostListDTO(Scrap scrap) {
            this.id = scrap.getId();
            this.companyName = scrap.getUser().getCompanyName();
            this.title = scrap.getPost().getTitle();
            this.career = scrap.getPost().getCareer();
            this.task = scrap.getPost().getTask();
            this.profile = scrap.getPost().getProfile();
            this.workingArea = scrap.getPost().getWorkingArea();
            this.skillList=scrap.getPost().getSkillList().stream().map(skill -> new PostSkillDTO(skill)).toList();
        }

        @Data
        public static class PostSkillDTO{
            private Integer id;
            private String skill;

            public PostSkillDTO(Skill skill){
                this.id = skill.getId();
                this.skill=skill.getSkill();
            }
        }
    }

    @Data
    public static class ScrapResumeListDTO {
        private Integer id;
        private Resume resume;
        private Integer personId;
        private String title;
        private String profile;
        private String career;
        private String simpleIntroduce;
        private List<ResumeSkillDTO> skillList;

        public ScrapResumeListDTO(Scrap scrap) {
            this.id = scrap.getId();
            this.resume = getResume();
            this.personId = scrap.getResume().getUser().getId();
            this.title = scrap.getResume().getTitle();
            this.profile = scrap.getResume().getUser().getProfile();
            this.career = scrap.getResume().getCareer();
            this.simpleIntroduce = scrap.getResume().getSimpleIntroduce();
            this.skillList=scrap.getResume().getSkillList().stream().map(skill -> new ResumeSkillDTO(skill)).toList();
        }

        @Data
        public static class ResumeSkillDTO{
            private Integer id;
            private String skill;

            public ResumeSkillDTO(Skill skill){
                this.id = skill.getId();
                this.skill=skill.getSkill();
            }
        }
    }
}


