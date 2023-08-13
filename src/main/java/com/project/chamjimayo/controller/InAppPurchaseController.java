package com.project.chamjimayo.controller;

import com.project.chamjimayo.controller.dto.PointChangeDto;
import com.project.chamjimayo.controller.dto.response.ApiStandardResponse;
import com.project.chamjimayo.controller.dto.response.ErrorResponse;
import com.project.chamjimayo.controller.dto.request.GoogleInAppPurchaseRequest;
import com.project.chamjimayo.controller.dto.response.RefundResponse;
import com.project.chamjimayo.security.CustomUserDetails;
import com.project.chamjimayo.service.InAppPurchaseService;
import com.project.chamjimayo.service.RefundService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "in-app", description = "in-app 결제 관련 api")
@RequestMapping("/api/in-app")
public class InAppPurchaseController {
  private final InAppPurchaseService inAppPurchaseService;
  private final RefundService refundService;

  @Operation(summary = "구매 영수증 검증", description = "구매한 영수증 토큰으로 유효한지 검증")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "영수증 검증 성공"),
      @ApiResponse(responseCode = "400", description = "1. 결제 완료 상태가 아닙니다\t\n"
          + "2. 사용자가 존재하지 않습니다.\t\n"
          + "3. 구글 플레이스토어 api 관련 오류 메시지",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class),
              examples = @ExampleObject(value = "{ \"code\": \"24\", \"msg\": \"fail\","
                  + " \"data\": {\"status\": \" PURCHASE_VERIFICATION_EXCEPTION\", "
                  + "\"msg\":\"결제가 완료되지 않았습니다.\"} }")))
  })
  @Parameter(name = "Bearer-Token", description = "jwt token", schema = @Schema(type = "string"),
      in = ParameterIn.HEADER, example = "Bearer e1323423534", required = true)
  @PostMapping("/purchase/verify")
  public ResponseEntity<Object> purchaseVerify(
      @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails customUserDetails,
      @RequestBody GoogleInAppPurchaseRequest request) {
    PointChangeDto pointChangeDto = inAppPurchaseService.verifyPurchase(customUserDetails.getId(),
        request);
    return ResponseEntity.ok(ApiStandardResponse.success(pointChangeDto));
  }

  @Operation(summary = "포인트 충전 환불", description = "받은 환불 정보를 가지고 포인트 환불 처리")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "포인트 환불 성공"),
      @ApiResponse(responseCode = "400", description = "요청 변수 에러",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class),
              examples = @ExampleObject(value = "{ \"code\": \"26\", \"msg\": \"fail\","
                  + " \"data\": {\"status\": \" REFUND_EXCEPTION\", "
                  + "\"msg\":\"환불 실패.\"} }")))
  })
  @PostMapping("/refund")
  public ResponseEntity<ApiStandardResponse<List<RefundResponse>>> processRefund() {
    List<RefundResponse> result = refundService.processRefund();
    return ResponseEntity.ok(ApiStandardResponse.success(result));
  }
}
