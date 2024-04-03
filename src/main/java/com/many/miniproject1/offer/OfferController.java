package com.many.miniproject1.offer;

import com.many.miniproject1.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class OfferController {
    private final HttpSession session;
    private final OfferService offerService;

    // 개인 제안 목록
    @GetMapping("/person/offers")
    public String personOffers(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        List<OfferResponse.personOffersDTO> respDTO = offerService.personOffers(sessionUser.getId());
        request.setAttribute("offerList", respDTO);
        return "person/offers";
    }
    // 개인 제안 상세보기
    @GetMapping("/person/offer/post/detail/{id}")
    public String personOfferDetail(HttpServletRequest request, @PathVariable int id) {
        OfferResponse.personOfferDetailDTO respDTO = offerService.personOfferDetail(id);
        request.setAttribute("offer", respDTO);
        return "person/offer-post-detail";
    }

    // 기업 제안 목록
    @GetMapping("/company/offers")
    public String personPost(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        List<OfferResponse.companyOffersDTO> respDTO = offerService.companyOffers(sessionUser.getId());
        request.setAttribute("offerList", respDTO);
        return "company/offers";
    }

    @GetMapping("/company/offer/{id}/detail")
    public String companyOfferDetail(HttpServletRequest request, @PathVariable int id) {
        OfferResponse.companyOfferDetailDTO respDTO = offerService.companyOfferDetail(id);
        request.setAttribute("offer", respDTO);
        return "company/mypage-resume-detail";
    }

    // 제안한 이력서 DELETE (취소)
    @PostMapping("/company/offer/{id}/detail/delete")
    public String companyOfferDetailDelete(@PathVariable int id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        offerService.deleteOffer(id);
        return "redirect:/company/offers";
    }


    @GetMapping("/company/update-info-form")
    public String noFinded() {
        return "company/update-info-form";
    }

    @GetMapping("/company/offer-form/")
    public String noFinded2() {
        return "company/offer-form";
    }


}