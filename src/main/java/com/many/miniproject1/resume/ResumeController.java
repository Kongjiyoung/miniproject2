package com.many.miniproject1.resume;

import com.many.miniproject1._core.errors.exception.Exception404;

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
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;
    private final HttpSession session;

    // 목록보기
    @GetMapping("/person/resume")
    public String personResumeForm(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        List<ResumeResponse.ResumeListDTO> respDTO = resumeService.findResumeList(sessionUser.getId());
        request.setAttribute("resumeList",respDTO);
        return "person/resumes";
    }
    // 상세보기
    @GetMapping("/person/resume/{id}/detail")
    public String personResumeDetailForm(@PathVariable int id, HttpServletRequest request) {
        ResumeResponse.ResumeDetailDTO respDTO = resumeService.getResumeDetail(id);
        request.setAttribute("resume", respDTO);
        return "person/resume-detail";
    }
    // 작성
    @PostMapping("/person/resume/save")
    public String personSaveResume(ResumeRequest.SaveDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        ResumeResponse.ResumeSaveDTO respDTO = resumeService.save(reqDTO, sessionUser);
        return "redirect:/person/resume";
    }
    // 작성 폼
    @GetMapping("/person/resume/save-form")
    public String personSaveResumeForm() {
        return "person/resume-save-form";
    }

    // 수정
    @PostMapping("/person/resume/{id}/detail/update")
    public String personUpdateResume(@PathVariable int id, ResumeRequest.UpdateDTO reqDTO) {
//        System.out.println("requestDTO = " + reqDTO);
//        User sessionUser = (User) session.getAttribute("sessionUser");
        resumeService.update(id, reqDTO);
        return "redirect:/person/resume/" + id + "/detail";
    }
    // 수정 폼
    @GetMapping("/person/resume/detail/{id}/update-form")
    public String personUpdateResumeForm(@PathVariable int id, HttpServletRequest request) {
        ResumeResponse.UpdateFormDTO respDTO = resumeService.findByResume(id);
        request.setAttribute("resume", respDTO);
        return "person/resume-update-form";
    }

    // 삭제
    @PostMapping("/person/resume/{id}/delete")
    public String personDeleteResume(@PathVariable Integer id) {
        resumeService.deleteResume(id);
        return "redirect:/person/resume";
    }
}