package com.many.miniproject1.offer;


import com.many.miniproject1._core.errors.exception.Exception404;
import com.many.miniproject1.apply.Apply;
import com.many.miniproject1.apply.ApplyJPARepository;
import com.many.miniproject1.resume.Resume;
import com.many.miniproject1.skill.SkillJPARepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OfferService {
    private final OfferJPARepository offerJPARepository;

    // 개인 제안 목록
    public List<OfferResponse.personOffersDTO> personOffers(Integer postId) {
        List<Offer> offerList = offerJPARepository.findByPostIdJoinPost(postId);
        return offerList.stream().map(offer -> new OfferResponse.personOffersDTO(offer)).toList();
    }
    // 개인 제안 상세 (post)
    public OfferResponse.personOfferDetailDTO personOfferDetail(Integer id) {
        Offer offer = offerJPARepository.findByPostId(id);
        return new OfferResponse.personOfferDetailDTO(offer);
    }
    // 기업 제안 목록
    public List<OfferResponse.companyOffersDTO> companyOffers(Integer userId) {
        List<Offer> offerList = offerJPARepository.findByUserId(userId);
        return offerList.stream().map(offer -> new OfferResponse.companyOffersDTO(offer)).toList();
    }
    // 기업 제안 상세 (resume)
    public OfferResponse.companyOfferDetailDTO companyOfferDetail(int id) {
        Offer offer = offerJPARepository.findByIdJoinResumeAndSkillAndUser(id);
        return new OfferResponse.companyOfferDetailDTO(offer);
    }
    // 기업 제안 취소
    @Transactional
    public void deleteOffer(int id) {
        offerJPARepository.deleteById(id);
    }
}
