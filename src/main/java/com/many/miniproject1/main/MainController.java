package com.many.miniproject1.main;

import com.many.miniproject1.apply.ApplyResponse;
import com.many.miniproject1.offer.Offer;
import com.many.miniproject1.offer.OfferRequest;
import com.many.miniproject1.post.Post;
import com.many.miniproject1.resume.Resume;
import com.many.miniproject1.apply.Apply;
import com.many.miniproject1.scrap.Scrap;
import com.many.miniproject1.scrap.ScrapResponse;
import com.many.miniproject1.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequiredArgsConstructor
public class MainController {

    private final HttpSession session;
    private final MainService mainService;


    //맞춤 공고 - 기업이 보는 매칭 이력서

    /**
     * TODO: company/matching의 검색 버튼을 누르면 스트링을 인터저로 변환하지 못 해서 생기는 에러가 뜨는데 로직을 날려서 그런 것인지 원래 있던 문제인지 몰라서 남겨둠.
     *  그 문제는 company/match로 넘어가는 과정에서 터지는 것이다.
     *  /person/matching도 마찬가지이니 담당자는 반드시 체크할 것!!!
     */


    //메인 구직 공고
    @GetMapping("/company/main")
    public String resumeForm(HttpServletRequest request, @RequestParam(value = "search", defaultValue = "") String search) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        Boolean isCompany = false;
        //기업인지 개인인지 구분
        if (sessionUser != null) {
            String role = sessionUser.getRole();

            if (role.equals("company")) {
                isCompany = true;
            }
        }

        request.setAttribute("isMatchingCompany", isCompany);
        request.setAttribute("sessionuser", sessionUser);

        if (search != null){
            List<Resume> resumeList = mainService.resumeSearchForm(search);
            request.setAttribute("resumeList", resumeList);
            return "company/main";
        }
        List<Resume> resumeList = mainService.resumeForm();
        request.setAttribute("resumeList", resumeList);

        return "company/main";
    }

    @GetMapping("/resumes/{id}")
    public String resumeDetailForm(@PathVariable Integer id, HttpServletRequest request) {

        // 현재 로그인한 사용자가 회사인 경우에만 해당 회사가 작성한 채용 공고 목록 가져오기
        User sessionUser = (User) session.getAttribute("sessionUser");

        boolean isCompany = false;
        if (sessionUser != null) {
            String role = sessionUser.getRole();
            if (role.equals("company")) {
                isCompany = true;
            }
            Integer companyId = sessionUser.getId();
            List<Post> postList = mainService.getPostsByCompanyId(companyId);
            request.setAttribute("postList", postList);
        }

        MainResponse.MainResumeDetailDTO resume = mainService.getResumeDetail(id);
        Map<String, Object> responseBody = new HashMap<>();
        request.setAttribute("resume", resume);
        request.setAttribute("isMatchingCompany", isCompany);
        request.setAttribute("sessionuser", sessionUser);

        return "company/resume-detail";
    }

    @PostMapping("/resumes/{id}/offer")
    public String companyResumeOffer(@PathVariable Integer id, MainRequest.PostChoiceDTO postChoice) {
        mainService.sendPostToResume(id, postChoice.getPostChoice());
        return "redirect:/resumes/" + id;
    }

    @PostMapping("/resumes/{id}/scrap")
    public String companyResumeScrap(@PathVariable Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        mainService.resumeScrap(id, sessionUser.getId());
        return "redirect:/resumes/" + id;
    }
    // YSH
    // ┳━┳ ノ( ゜-゜ノ)

    //메인 채용 공고
    @GetMapping({"/person/main", "/"})
    public String postForm(
            boolean toSearch,
            MainRequest.SearchDTO requestDTO,
            HttpServletRequest request) {

        // 목적: 개인 회원 로그인/비회원 로그인 시 공고들이 보임
        User sessionUser = (User) session.getAttribute("sessionUser");

        //기업인지 개인인지 구분
        Boolean isCompany = false;
        if (sessionUser != null) {
            String role = sessionUser.getRole();

            if (role.equals("company")) {
                isCompany = true;
            }
        }
        List<Post> postList;
        if (!toSearch) {
            postList = mainService.postForm();
        } else {
            postList = mainService.postForm(requestDTO);
        }


        request.setAttribute("postList", postList);

        return "person/main";
    }

    @GetMapping("/posts/{id}")
    public String postDetailForm(@PathVariable Integer id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        //기업인지 개인인지 구분
        boolean isCompany = false;
        if (sessionUser != null) {
            String role = sessionUser.getRole();
            if (role.equals("company")) {
                isCompany = true;
            }
            Integer companyId = sessionUser.getId();
            List<Resume> resumeList = mainService.findByUserIdResume(companyId);
            request.setAttribute("resumeList", resumeList);
        }

        MainResponse.PostDetailDTO post = mainService.getPostDetail(id);
        Map<String, Object> responseBody = new HashMap<>();
        request.setAttribute("post", post);
        request.setAttribute("isMatchingCompany", isCompany);
        request.setAttribute("sessionuser", sessionUser);
        return "person/post-detail";
    }

    // 지원하기 버튼 안 보임
    @PostMapping("/posts/{id}/apply")
    public String personPostApply(@PathVariable int id, MainRequest.ResumeChoiceDTO resumeChoice) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        mainService.personPostApply(id, resumeChoice.getResumeChoice());
        return "redirect:/posts/" + id;
    }

    @PostMapping("/posts/{id}/scrap")
    public String personPostScrap(@PathVariable int id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        // 로그인을 하지 않으면 세션유저가 없어서 주석을 걸어놓음
//        Integer personId = sessionUser.getId();
        ScrapResponse.PostScrapSaveDTO respDTO = mainService.personPostScrap(sessionUser.getId(), id);
        return "redirect:/posts/" + id;
    }


    /**
     * TODO: company/matching의 검색 버튼을 누르면 스트링을 인터저로 변환하지 못 해서 생기는 에러가 뜨는데 로직을 날려서 그런 것인지 원래 있던 문제인지 몰라서 남겨둠.
     *  그 문제는 company/match로 넘어가는 과정에서 터지는 것이다.
     *  /person/matching도 마찬가지이니 담당자는 반드시 체크할 것!!!
     */
    @GetMapping("/company/matching")
    public String matchingResumeForm(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        List<Post> posts = mainService.findByUserIdPost(sessionUser.getId());
        request.setAttribute("posts", posts);
        Integer postChoice = (Integer) session.getAttribute("postChoice");
        if (postChoice != null) {
            List<Resume> resumeList = mainService.matchingResume(postChoice);
            request.setAttribute("resumeList", resumeList);
        }
        return "company/matching";
    }

    //맞춤 공고 - 개인이 보는 매칭 공고
    @GetMapping("/person/matching")
    public String matchingPostForm(HttpServletRequest request) {
        //공고 가져오기
        User sessionUser = (User) session.getAttribute("sessionUser");
        List<Resume> resumeList = mainService.findByUserIdResume(sessionUser.getId());
        request.setAttribute("resumeList", resumeList);
        Integer resumeChoice = (Integer) session.getAttribute("resumeChoice");
        if (resumeChoice != null) {
            List<Post> postList = mainService.matchingPost(resumeChoice);
            request.setAttribute("postList", postList);
        }

        return "person/matching";
    }

    @PostMapping("/person/match")
    public String matchingResume(int resumeChoice) {
        session.setAttribute("resumeChoice", resumeChoice);
        return "redirect:/person/matching";
    }
}



