package com.shoppingTest.controller;

import com.shoppingTest.common.security.domain.CustomUser;
import com.shoppingTest.domain.Item;
import com.shoppingTest.domain.Member;
import com.shoppingTest.prop.ShopProperties;
import com.shoppingTest.service.ItemService;
import com.shoppingTest.service.MemberService;
import com.shoppingTest.service.UserItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping("/item")
public class ItemController {
    private final ItemService itemService;

    private final ShopProperties shopProperties;

    // 업무로직을 처리할 서비스 객체를 필드로 선언한다.
    private final MemberService memberService;

    private final UserItemService userItemService;

    // 메시지를 처리할 MessageSource를 필드로 선언한다.
    private final MessageSource messageSource;

    // 등록 화면
    @GetMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public String registerForm(Model model) {
        model.addAttribute(new Item());

        return "item/register";
    }
    // 등록 처리
    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public String register(@Validated Item item, BindingResult result, RedirectAttributes rttr) throws Exception {
        if(result.hasErrors()) {
            return "item/register";
        }

        MultipartFile pictureFile = item.getPicture();
        MultipartFile previewFile = item.getPreview();

        String createdPictureFilename = uploadFile(pictureFile.getOriginalFilename(), pictureFile.getBytes());
        String createdPreviewFilename = uploadFile(previewFile.getOriginalFilename(), previewFile.getBytes());

        item.setPictureUrl(createdPictureFilename);
        item.setPreviewUrl(createdPreviewFilename);

        itemService.register(item);

        rttr.addFlashAttribute("msg", "SUCCESS");
        return "redirect:/item/list";
    }
    // 목록 화면
    @GetMapping("/list")
    public void list(Model model) throws Exception {
        List<Item> itemList = itemService.list();

        model.addAttribute("itemList", itemList);
    }

    // 상세 화면
    @GetMapping("/read")
    public String read(Long itemId, Model model) throws Exception {
        Item item = itemService.read(itemId);

        model.addAttribute(item);

        return "item/read";
    }
    // 수정 화면
    @GetMapping("/modify")
    @PreAuthorize("hasRole('ADMIN')")
    public String modifyForm(Long itemId, Model model) throws Exception {
        Item item = itemService.read(itemId);

        model.addAttribute(item);

        return "item/modify";
    }
    // 수정 처리
    @PostMapping("/modify")
    @PreAuthorize("hasRole('ADMIN')")
    public String modify(@Validated Item item, BindingResult result, RedirectAttributes rttr) throws Exception {
        if(result.hasErrors()) {
            return "item/modify";
        }

        MultipartFile pictureFile = item.getPicture();

        if (pictureFile != null && pictureFile.getSize() > 0) {
            String createdFilename = uploadFile(pictureFile.getOriginalFilename(), pictureFile.getBytes());

            item.setPictureUrl(createdFilename);
        }

        MultipartFile previewFile = item.getPreview();

        if (previewFile != null && previewFile.getSize() > 0) {
            String createdFilename = uploadFile(previewFile.getOriginalFilename(), previewFile.getBytes());

            item.setPreviewUrl(createdFilename);
        }

        itemService.modify(item);

        rttr.addFlashAttribute("msg", "SUCCESS");

        return "redirect:/item/list";
    }
    // 삭제 화면
    @GetMapping("/remove")
    @PreAuthorize("hasRole('ADMIN')")
    public String removeForm(Long itemId, Model model) throws Exception {
        Item item = itemService.read(itemId);

        model.addAttribute(item);

        return "item/remove";
    }

    // 삭제 처리
    @PostMapping("/remove")
    @PreAuthorize("hasRole('ADMIN')")
    public String remove(Item item, RedirectAttributes rttr) throws Exception {
        itemService.remove(item.getItemId());
        rttr.addFlashAttribute("msg", "SUCCESS");

        return "redirect:/item/list";
    }

    private String uploadFile(String originalName, byte[] fileData) throws Exception {
        UUID uid = UUID.randomUUID();

        String createdFileName = uid.toString() + "_" + originalName;

        String uploadPath = shopProperties.getUploadPath();
        File target = new File(uploadPath, createdFileName);

        FileCopyUtils.copy(fileData, target);

        return createdFileName;
    }
    // 미리보기 이미지 표시
    @ResponseBody
    @GetMapping("/display")
    public ResponseEntity<byte[]> displayFile(Long itemId) throws Exception {
        ResponseEntity<byte[]> entity = null;

        String fileName = itemService.getPreview(itemId);

        try {
            String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);

            MediaType mType = getMediaType(formatName);

            HttpHeaders headers = new HttpHeaders();

            String uploadPath = shopProperties.getUploadPath();
            File file = new File(uploadPath + File.separator + fileName);

            if (mType != null) {
                headers.setContentType(mType);
            }

            entity = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }
    // 이미지 형식 확인
    private MediaType getMediaType(String formatName){
        if(formatName != null) {
            if(formatName.equals("JPG")) {
                return MediaType.IMAGE_JPEG;
            }

        }
        if(formatName.equals("GIF")) {
            return MediaType.IMAGE_GIF;
        }

        if(formatName.equals("PNG")) {
            return MediaType.IMAGE_PNG;
        }
        return null;
    }

    // 상품 구매 요청을 처리한다.
    @PostMapping("/buy")
    public String buy(Long itemId, RedirectAttributes rttr, Authentication authentication) throws Exception {
        CustomUser customUser = (CustomUser)authentication.getPrincipal();
        Member member = customUser.getMember();

        Long userNo = member.getUserNo();

        member.setCoin(memberService.getCoin(userNo));

        Item item = itemService.read(itemId);

        userItemService.register(member, item);

        String message = messageSource.getMessage("item.purchaseComplete", null, Locale.KOREAN);
        rttr.addFlashAttribute("msg", message);

        return "redirect:/item/success";
    }

    // 상품 구매 성공 화면을 표시한다.
    @GetMapping("/success")
    public String success() throws Exception {
        return "item/success";
    }
}