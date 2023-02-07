package com.shoppingTest.controller;

import com.shoppingTest.domain.Notice;
import com.shoppingTest.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
@RequestMapping("/notice")
public class NoticeController {
    private final NoticeService service;

    @GetMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public void registerForm(Model model) throws Exception{
        Notice notice = new Notice();

        model.addAttribute(notice);
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public String register(@Validated Notice notice, BindingResult result, RedirectAttributes rttr) throws Exception{
        if(result.hasErrors()){
            return "notice/register";
        }

        service.register(notice);

        rttr.addFlashAttribute("msg", "SUCCESS");

        return "redirect:/notice/list";
    }
    // 목록 화면
    @GetMapping("/list")
    public void list(Model model) throws Exception {
        model.addAttribute("list", service.list());
    }

    // 상세 화면
    @GetMapping("/read")
    public void read(Long noticeNo, Model model) throws Exception {
        model.addAttribute(service.read(noticeNo));
    }

    // 삭제 처리
    @PostMapping("/remove")
    @PreAuthorize("hasRole('ADMIN')")
    public String remove(Long noticeNo, RedirectAttributes rttr) throws Exception {
        service.remove(noticeNo);

        rttr.addFlashAttribute("msg", "SUCCESS");

        return "redirect:/notice/list";
    }
    // 수정 화면
    @GetMapping("/modify")
    @PreAuthorize("hasRole('ADMIN')")
    public void modifyForm(Long noticeNo, Model model) throws Exception {
        model.addAttribute(service.read(noticeNo));
    }

    // 수정 처리
    @PostMapping("/modify")
    @PreAuthorize("hasRole('ADMIN')")
    public String modify(@Validated Notice notice, BindingResult result, RedirectAttributes rttr) throws Exception {
        if (result.hasErrors()) {
            return "notice/modify";
        }

        service.modify(notice);

        rttr.addFlashAttribute("msg", "SUCCESS");

        return "redirect:/notice/list";
    }
}