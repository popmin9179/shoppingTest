package com.shoppingTest.controller;

import com.shoppingTest.domain.CodeGroup;
import com.shoppingTest.service.CodeGroupService;
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
@RequestMapping("/codegroup")
@PreAuthorize("hasRole('ADMIN')")
public class CodeGroupController {

    private final CodeGroupService service;

    @GetMapping("/register")
    public void registerForm(Model model) throws Exception{
        CodeGroup codeGroup = new CodeGroup();

        model.addAttribute(codeGroup);
    }

    @PostMapping("/register")
    public String register(@Validated CodeGroup codeGroup, BindingResult result, RedirectAttributes rttr) throws Exception{


        if(result.hasErrors()){
            return "codegroup/register";
        }
        service.register(codeGroup);

        rttr.addFlashAttribute("msg","SUCCESS");
        return "redirect:/codegroup/list";
    }

    @GetMapping("/list")
    public void list(Model model) throws Exception{
        model.addAttribute("list", service.list());
    }

    @GetMapping("/read")
    public void read(String groupCode, Model model) throws Exception{
        model.addAttribute(service.read(groupCode));
    }

    @PostMapping("/remove")
    public String remove(String groupCode, RedirectAttributes rttr) throws Exception{
        service.remove(groupCode);

        rttr.addFlashAttribute("msg", "SUCCESS");

        return "redirect:/codegroup/list";
    }

    @GetMapping("/modify")
    public void modifyForm(String groupCode, Model model) throws Exception{
        model.addAttribute(service.read(groupCode));
    }

    @PostMapping("/modify")
    public String modify(CodeGroup codeGroup, BindingResult result, RedirectAttributes rttr) throws Exception{
        if(result.hasErrors()){
            return "codegroup/modify";
        }
        service.modify(codeGroup);
        rttr.addFlashAttribute("msg", "SUCCESS");

        return "redirect:/codegroup/list";
    }

}
