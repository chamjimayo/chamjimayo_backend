package com.project.chamjimayo.controller;

import com.project.chamjimayo.service.RestroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/restroom")
public class RestroomController {

    RestroomService restroomService;

    @Autowired
    public RestroomController(RestroomService restroomService) {
        this.restroomService = restroomService;
    }

    @PostMapping("/import")
    public void importRestroom() throws Exception {
        restroomService.importRestroom();
    }
}
