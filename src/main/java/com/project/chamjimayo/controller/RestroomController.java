package com.project.chamjimayo.controller;

import com.project.chamjimayo.controller.dto.BaseException;
import com.project.chamjimayo.controller.dto.EnrollRestroomRequest;
import com.project.chamjimayo.controller.dto.RestroomDetail;
import com.project.chamjimayo.controller.dto.RestroomNearByRequest;
import com.project.chamjimayo.controller.dto.RestroomNearByResponse;
import com.project.chamjimayo.service.RestroomService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/restroom")
public class RestroomController {

    private final RestroomService restroomService;

    @PostMapping("/import")
    public ResponseEntity<List<String>> importRestroom() throws BaseException {
        return ResponseEntity.ok(restroomService.importRestroom());
    }

    @PostMapping("/enroll")
    public ResponseEntity<String> enrollRestroom(
        @RequestBody EnrollRestroomRequest enrollRestroomRequest) throws BaseException {
        return ResponseEntity.ok(restroomService.enrollRestroom(enrollRestroomRequest));
    }

    @GetMapping("/nearby/{publicOrPaid}")
    public ResponseEntity<List<RestroomNearByResponse>> restroomNearBy(
        @PathVariable(value = "publicOrPaid", required = true) String publicOrPaid,
        @RequestParam(value = "distance", required = false) Double distance,
        @RequestParam String longitude, String latitude) throws BaseException {
        if (distance == null) {
            distance = 1000.0;
        }
        RestroomNearByRequest restroomNearByRequest = new RestroomNearByRequest(longitude,
            latitude, publicOrPaid, distance);
        return ResponseEntity.ok(restroomService.nearBy(restroomNearByRequest));
    }

    @GetMapping("detail")
    public ResponseEntity<RestroomDetail> restroomDetail(@RequestParam Long restroomId)
        throws BaseException {
        return ResponseEntity.ok(restroomService.restroomDetail(restroomId));
    }
}
