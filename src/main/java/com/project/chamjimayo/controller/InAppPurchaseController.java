package com.project.chamjimayo.controller;

import com.project.chamjimayo.controller.dto.GoogleInAppPurchaseRequest;
import com.project.chamjimayo.security.CustomUserDetails;
import com.project.chamjimayo.service.InAppPurchaseService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/in-app")
public class InAppPurchaseController {

  private final InAppPurchaseService inAppPurchaseService;

  @Parameter(name = "Bearer-Token", description = "jwt token", schema = @Schema(type = "string"),
      in = ParameterIn.HEADER, example = "Bearer e1323423534", required = true)
  @PostMapping("/purchase")
  public ResponseEntity<Object> purchaseProcess(
      @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails customUserDetails,
      @RequestBody GoogleInAppPurchaseRequest request) {
    inAppPurchaseService.processPurchase(customUserDetails.getId(), request);
    return ResponseEntity.noContent().build();
  }
}
