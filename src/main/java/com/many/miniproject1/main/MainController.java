package com.many.miniproject1.main;

import com.many.miniproject1.post.Post;
import com.many.miniproject1.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class MainController {

    private final HttpSession session;
    private final MainService mainService;

    private Integer postChoose = 0;
    private Integer resumeChoose = 0;

    //메인 구직 공고
    @GetMapping("/company/main")
    public String resumeForm(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        Boolean isCompany = false;
        //기업인지 개인인지 구분
        if (sessionUser != null) {
            String role = sessionUser.getRole();
            System.out.println(role);

            if (role.equals("company")) {
                isCompany = true;
            }
        }
        request.setAttribute("isMatchingCompany", isCompany);
        request.setAttribute("sessionuser", sessionUser);

        return "company/main";
    }

    @GetMapping("/resumes/{id}")
    public String resumeDetailForm(@PathVariable int id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        // 로그인을 하지 않으면 세션유저가 없어서 주석을 걸어놓음
//        if (sessionUser != null) {
//            String role = sessionUser.getRole();
//            Boolean isCompany = false;
//            if (role.equals("company")) {
//                isCompany = true;
//            }
//            request.setAttribute("isMatchingCompany", isCompany);
//        }
        request.setAttribute("sessionuser", sessionUser);
        return "company/resume-detail";
    }

    @PostMapping("/resumes/{id}/offer")
    public String companyResumeOffer(@PathVariable int id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        // 로그인을 하지 않으면 세션유저가 없어서 주석을 걸어놓음
//        Integer companyId = sessionUser.getId();
        request.setAttribute("sessionuser", sessionUser);

        return "redirect:/resumes/" + id;
    }

    @PostMapping("/resumes/{id}/scrap")
    public String companyResumeScrap(@PathVariable int id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        // 로그인을 하지 않으면 세션유저가 없어서 주석을 걸어놓음
//        Integer companyId = sessionUser.getId();

        return "redirect:/resumes/" + id;
    }

    //메인 채용 공고
    @GetMapping({"/person/main", "/"})
    public String postForm(HttpServletRequest request) {
        List<Post> postList = mainService.getPostList();

        request.setAttribute("postList", postList);
        // 목적: 개인 회원 로그인/비회원 로그인 시 공고들이 보임
        User sessionUser = (User) session.getAttribute("sessionUser");


        request.setAttribute("sessionuser", sessionUser);

        return "person/main";
    }

    @GetMapping("/posts/{id}")
    public String postDetailForm(@PathVariable int id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        // 목적: 로그인 하지 않아도 회사에서 올린 공고가 보임
        Post post = mainService.getPostDetail(id);

        Boolean isCompany = false;
        // 로그인을 하지 않으면 세션유저가 없어서 주석을 걸어놓음
        //기업인지 개인인지 구분
        if (sessionUser != null) {
            String role = sessionUser.getRole();
            System.out.println(role);

            if (role.equals("company")) {
                isCompany = true;
            }
        }

        request.setAttribute("isMatchingCompany", isCompany);
        request.setAttribute("sessionuser", sessionUser);
        request.setAttribute("post", post);
        return "person/post-detail";
    }

    // 지원하기 버튼 안 보임
    @PostMapping("/posts/{id}/apply")
    public String personPostApply(@PathVariable int id, @RequestParam("resumeChoice") Integer resumeChoice) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        // 로그인을 하지 않으면 세션유저가 없어서 주석을 걸어놓음
//        Integer personId = sessionUser.getId();

        return "redirect:/posts/" + id;
    }

    @PostMapping("/posts/{id}/scrap")
    public String personPostScrap(@PathVariable int id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        // 로그인을 하지 않으면 세션유저가 없어서 주석을 걸어놓음
//        Integer personId = sessionUser.getId();

        return "redirect:/posts/" + id;
    }

    //맞춤 공고 - 기업이 보는 매칭 이력서

    /**
     * TODO: company/matching의 검색 버튼을 누르면 스트링을 인터저로 변환하지 못 해서 생기는 에러가 뜨는데 로직을 날려서 그런 것인지 원래 있던 문제인지 몰라서 남겨둠.
     *  그 문제는 company/match로 넘어가는 과정에서 터지는 것이다.
     *  /person/matching도 마찬가지이니 담당자는 반드시 체크할 것!!!
     */
    @GetMapping("/company/matching")
    public String matchingResumeForm(HttpServletRequest request) {
        //공고 가져오기
        User sessionUser = (User) session.getAttribute("sessionUser");
        // 로그인을 하지 않으면 세션유저가 없어서 주석을 걸어놓음
//        Integer userId = sessionUser.getId();

        //Boolean isCompany
        return "company/matching";
    }

    @PostMapping("/company/match")
    public String matchingPost(@RequestParam("postChoice") Integer postChoice) {
        postChoose = postChoice;
        return "redirect:/company/matching";
    }

    //맞춤 공고 - 개인이 보는 매칭 공고
    @GetMapping("/person/matching")
    public String matchingPostForm(HttpServletRequest request) {
        //공고 가져오기
        User sessionUser = (User) session.getAttribute("sessionUser");

        return "person/matching";
    }

    @PostMapping("/person/match")
    public String matchingResume(@RequestParam("resumeChoice") Integer resumeChoice) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        resumeChoose = resumeChoice;
        return "redirect:/person/matching";
    }
}



