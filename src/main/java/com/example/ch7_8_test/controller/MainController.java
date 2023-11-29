package com.example.ch7_8_test.controller;

import com.example.ch7_8_test.dto.ItemSearchDto;
import com.example.ch7_8_test.dto.MainItemDto;
import com.example.ch7_8_test.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ItemService itemService;

    @GetMapping(value = "/")
    public String main(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model){

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
        Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageable);

        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);

        return "main";
    }

    //rest test code
    // 전체는 , 컨트롤러 이지만, 이 함수만 레스트로 사용.
    @GetMapping(value = "/main2/{page}")
    @ResponseBody
    public Map<String,Object> main2(ItemSearchDto itemSearchDto, @PathVariable("page") Optional<Integer> page, Model model){
        Map<String,Object> map = new HashMap<String,Object>();

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
        Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageable);

        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);
        map.put("items",items);

//        return "main";
        return map;
    }


}