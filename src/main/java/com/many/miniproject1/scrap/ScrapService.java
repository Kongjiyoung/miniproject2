package com.many.miniproject1.scrap;


import com.many.miniproject1._core.errors.exception.Exception401;
import com.many.miniproject1._core.errors.exception.Exception404;
import com.many.miniproject1.apply.Apply;
import com.many.miniproject1.apply.ApplyJPARepository;
import com.many.miniproject1.apply.ApplyRequest;
import com.many.miniproject1.apply.ApplyResponse;
import com.many.miniproject1.offer.Offer;
import com.many.miniproject1.offer.OfferJPARepository;
import com.many.miniproject1.offer.OfferRequest;
import com.many.miniproject1.offer.OfferResponse;
import com.many.miniproject1.post.Post;
import com.many.miniproject1.post.PostJPARepository;
import com.many.miniproject1.resume.Resume;
import com.many.miniproject1.resume.ResumeJPARepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ScrapService {
    private final ScrapJPARepository scrapJPARepository;
    private final ApplyJPARepository applyJPARepository;
    private final ResumeJPARepository resumeJPARepository;
    private final PostJPARepository postJPARepository;
    private final OfferJPARepository offerJPARepository;


    @Transactional
    public void deleteScrapPost(Integer id) {
        Scrap scrap = scrapJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("스크랩한 공고를 찾을 수 없습니다"));
        scrapJPARepository.deleteById(id);

    }

    public void deleteScrap(Integer id) {
        scrapJPARepository.deleteById(id);
    }

    public ScrapResponse.ScrapResumeDetailDTO getResumeDetail(Integer scrapId) {
        Scrap scrap = scrapJPARepository.findByResumeIdAndSkillAndUser(scrapId);
        return new ScrapResponse.ScrapResumeDetailDTO(scrap);
    }

    public ScrapResponse.ScrapPostDetailDTO scrapPostDetail(Integer scrapId) {
        Scrap scrap = scrapJPARepository.findByScrapIdJoinPost(scrapId);
        return new ScrapResponse.ScrapPostDetailDTO(scrap);
    }

    public Scrap findById(int id) {
        Scrap scrap = scrapJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("이력서를 찾을 수 없습니다"));
        return scrap;
    }

    public List<ScrapResponse.ScrapPostListDTO> personScrapList(Integer userId) {
        List<Scrap> scrapList = scrapJPARepository.findByCompanyIdJoinSkills(userId);
        return scrapList.stream().map(scrap -> new ScrapResponse.ScrapPostListDTO(scrap)).toList();
    }

    public List<ScrapResponse.ScrapResumeListDTO> companyScrapList(Integer userId) {
        List<Scrap> scrapList = scrapJPARepository.findByUserIdJoinSkillAndResume(userId);
        return scrapList.stream().map(scrap -> new ScrapResponse.ScrapResumeListDTO(scrap)).toList();
    }

    @Transactional
    public OfferResponse.ChoiceDTO sendPostToResume(Integer scrapId, Integer postChoice) {
        Scrap scrap = scrapJPARepository.findById(scrapId).orElseThrow();
        Resume resume = resumeJPARepository.findById(scrap.getResume().getId())
                .orElseThrow(() -> new Exception404("이력서를 찾을 수 없습니다."));
        Post post = postJPARepository.findById(postChoice)
                .orElseThrow(() -> new Exception401("존재하지 않는 공고입니다!" + postChoice));
        OfferRequest.ScrapOfferDTO scrapOfferDTO = new OfferRequest.ScrapOfferDTO(resume, post);
        Offer offer = offerJPARepository.save(scrapOfferDTO.toEntity());

        return new OfferResponse.ChoiceDTO(offer);
    }

    @Transactional
    public ApplyResponse.ChoiceDTO sendResumeToPost(Integer scrapId, Integer resumeChoice) {
        Resume resume = resumeJPARepository.findById(resumeChoice)
                .orElseThrow(() -> new Exception404("이력서를 찾을 수 없습니다."));
        Scrap scrap = scrapJPARepository.findById(scrapId)
                .orElseThrow(()->new Exception401("존재하지 않는 공고입니다."));
        Post post = postJPARepository.findById(scrap.getPost().getId())
                .orElseThrow(() -> new Exception401("존재하지 않는 공고입니다!" + resumeChoice));
        ApplyRequest.SaveDTO scrapApplyDTO = new ApplyRequest.SaveDTO(resume,post);
        Apply apply = applyJPARepository.save(scrapApplyDTO.toEntity());

        return new ApplyResponse.ChoiceDTO(apply);
    }

    public List<Post> companyPostList(int id) {
        return postJPARepository.findByPostId(id);
    }
    public List<Resume> personResumeList(int id) {
        return resumeJPARepository.findByResumeId(id);
    }


}
