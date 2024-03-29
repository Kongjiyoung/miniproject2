package com.many.miniproject1.scrap;


import com.many.miniproject1._core.errors.exception.Exception401;
import com.many.miniproject1._core.errors.exception.Exception404;
import com.many.miniproject1.apply.Apply;
import com.many.miniproject1.apply.ApplyJPARepository;
import com.many.miniproject1.apply.ApplyRequest;
import com.many.miniproject1.resume.Resume;
import com.many.miniproject1.resume.ResumeJPARepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ScrapService {
    private final ScrapJPARepository scrapJPARepository;
    private final ScrapQueryRepository scrapQueryRepository;
    private final ApplyJPARepository applyJPARepository;
    private final ResumeJPARepository resumeJPARepository;

    public List<Scrap> personScrapForm (Integer userId){
        return scrapJPARepository.findByPostIdJoinskills(userId);
    }

    @Transactional
    public Apply saveApply(int id, int  resumeId){
       Scrap scrap =scrapJPARepository.findById(id).orElseThrow(() -> new Exception401(""));
       Resume resume = resumeJPARepository.findById(resumeId).orElseThrow(()-> new Exception401(""));
       ApplyRequest.SaveDTO saveApply=new ApplyRequest.SaveDTO(resume, scrap.getPost());
       Apply apply=applyJPARepository.save(saveApply.toEntity());
       return apply;
    }

    @Transactional
    public void deleteScrapPost(int id){
        scrapJPARepository.deleteById(id);

    }
    public void deleteScrap(Integer id) {
        scrapJPARepository.deleteById(id);
    }

    public Scrap getResumeDetail(Integer userId, Integer resumeId){
        return scrapJPARepository.findByResumeIdAndSkillAndUser(userId, resumeId)
                .orElseThrow(()-> new Exception404("이력서를 찾을 수 없습니다."));
    }

    public Scrap findById(int id) {
        Scrap scrap = scrapJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("이력서를 찾을 수 없습니다"));
        return scrap;
    }
    public List<Scrap> companyScrapList(Integer userId){
        return scrapJPARepository.findByUserIdJoinSkillAndResume(userId);
    }

//public Scrap
}
