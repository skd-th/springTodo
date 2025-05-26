package com.example.springtodo.controller;

import com.example.springtodo.dto.TodoDTO;
import com.example.springtodo.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/todo")
@Log4j2
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

//    @RequestMapping("/list")
//    public void list() {
//        log.info("todo list........");
//    }
//
////    @RequestMapping(value = "/register", method = RequestMethod.GET)
////    public void register() {
////        log.info("todo register.......");
////    }
//
//    @GetMapping("/register")
//    public void registerGet() {
//        log.info("GET todo register.......");
//    }
//
    @RequestMapping("/list")
    public void list(Model model) {
        log.info("todo list....................................");

        model.addAttribute("dtoList", todoService.getAll());
    }

    @GetMapping("/register")
    public void registerGet() {
        log.info("GET todo register.............");
    }

    @PostMapping("/register")
    public String registerPost(@Valid TodoDTO todoDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        log.info("POST todo register.......");

        if(bindingResult.hasErrors()) {
            log.info("has errors........................");
//            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
//
//            return "redirect:/todo/register";

            return "/todo/register";
        }
        log.info(todoDTO);

        todoService.register(todoDTO);      // p.325
        return "redirect:/todo/list";
    }                      // p.314

//    @GetMapping("/read")
//    public void read(@RequestParam("tno") Long tno, Model model) {
//
//        TodoDTO todoDTO = todoService.getOne(tno);
//        log.info(todoDTO);
//
//        model.addAttribute("dto", todoDTO);           이걸 p.336에서 밑처럼 바꾸래
//    }

    @GetMapping({"/read", "/modify"})
    public void read(@RequestParam("tno") Long tno, Model model) {

        TodoDTO todoDTO = todoService.getOne(tno);
        log.info(todoDTO);

        model.addAttribute("dto", todoDTO);
    }

    @PostMapping("/modify")
    public String modify(@Valid TodoDTO todoDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()) {
            log.info("has errors........................");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            redirectAttributes.addAttribute("tno", todoDTO.getTno());
            return "redirect:/todo/modify";
        }

        log.info(todoDTO);

        todoService.modify(todoDTO);

        return "redirect:/todo/list";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam("tno") Long tno, RedirectAttributes redirectAttributes) {

        log.info("-------------------remove-----------------------------");
        log.info("tno: " + tno);

        todoService.remove(tno);

        return "redirect:/todo/list";
    }

}
