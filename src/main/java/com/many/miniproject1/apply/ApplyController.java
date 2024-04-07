package com.many.miniproject1.apply;



import com.many.miniproject1.post.PostJPARepository;
import com.many.miniproject1.resume.ResumeJPARepository;
import com.many.miniproject1.skill.SkillRepository;
import com.many.miniproject1.resume.Resume;
import com.many.miniproject1.resume.ResumeRequest;
import com.many.miniproject1.resume.ResumeResponse;
import com.many.miniproject1.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ApplyController {
    private final HttpSession session;

    private final SkillRepository skillRepository;
    private final PostJPARepository postJPARepository;
    private final ResumeJPARepository resumeJPARepository;
    private final ApplyService applyService;

    //기업에서 받은 이력서 관리

    @GetMapping("/company/resumes")
    public String companyResume(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        List<ApplyResponse.AppliedResumeSkillDTO> appliedResumeSkillDTOList = applyService.getAppliedResumeSkillDTOs(sessionUser.getId());
        request.setAttribute("applyList", appliedResumeSkillDTOList);
        request.setAttribute("company", sessionUser);
        return "company/applied-resumes";
    }

    @GetMapping("/company/resumes/{id}")
    public String companyResumeDetail(@PathVariable int id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        ApplyResponse.AppliedResumeSkillDetailDTO appliedResumeDetail = applyService.getAppliedResume(id);
        applyService.companyResumeDetail(id);
        request.setAttribute("apply",appliedResumeDetail);

        return "company/applied-resume-detail";
    }

    @PostMapping("/company/resumes/{id}/is-pass")
    public String companyPass(@PathVariable Integer id,ApplyRequest.UpdateIsPassDTO reqDTO) {

//        비행기 버튼 누르고 나서 어디로 가야하는지 잘 모르겠어서 현재 페이지로 남겨놓음(번복 가능)
        // 목적: 합격/불합격 버튼을 누르면 받은 지원받은 이력서 디테일 페이지에 합격/불합격 상태가 뜨고
        // 지원자의 지원 현황에도 합격 불합격이 뜸
        Apply apply = applyService.getApplyById(id);
        ApplyRequest.UpdateIsPassDTO updateIsPassDTO = applyService.isPassResume(id, reqDTO);
        apply.updateIsPass(reqDTO);

        return "redirect:/company/resumes/" + id;
    }

    // 개인이 지원한 이력서 목록 YSH
    @GetMapping("/person/applies")
    public String personApply(HttpServletRequest request) {
        User sessionUser=(User) session.getAttribute("sessionUser");
        List<ApplyResponse.ApplyPostSkillDTO> applyPostSkillDTOList = applyService.getApplyPostSkillDTOs(sessionUser.getId());
        request.setAttribute("applyList", applyPostSkillDTOList);

        return "person/applies";
    }

    @GetMapping("/person/applies/{id}") // 내가 지원한 공고 디테일
    public String personApply(@PathVariable int id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        ApplyResponse.ApplyPostSkillDetailDTO applyPostDetail = applyService.getPostDetail(id);
        request.setAttribute("apply", applyPostDetail);
        // 스킬 리스트 만들어서 돌리기
        return "person/post-apply-detail"; // 이것과 포스트 디테일과의 차이: 취소하기 버튼이 있냐, 스크랩 버튼이 있냐 차이
    }

    @PostMapping("/person/applies/{id}/delete")
    public String appliedDelete(@PathVariable int id, HttpServletRequest request) {
        applyService.deleteApply(id);
        request.setAttribute("apply", id);

        return "redirect:/person/applies";
    }
}
