package com.many.miniproject1.main;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class MainRequest {

    @Data
    public static class PostChoiceDTO {
        private Integer postChoice;
    }

    @Data
    public static class ResumeChoiceDTO {
        private Integer resumeChoice;
    }

    @Data
    public static class SearchDTO {
        private String keyword;
        private String title;
        private String career;
        private List<String> skills = new ArrayList<>();
    }
}
