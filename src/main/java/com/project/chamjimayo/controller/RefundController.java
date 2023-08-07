package com.project.chamjimayo.controller;

import com.project.chamjimayo.controller.dto.ApiStandardResponse;
import com.project.chamjimayo.controller.dto.ErrorResponse;
import com.project.chamjimayo.controller.dto.RefundResult;
import com.project.chamjimayo.service.RefundService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Tag(name = "refund", description = "환불 관련 API")
@RestController
@RequestMapping("/api/refund")
public class RefundController {

	private final RefundService refundService;

	@Operation(summary = "포인트 충전 환불", description = "받은 환불 정보를 가지고 포인트 환불 처리")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "포인트 환불 성공"),
			@ApiResponse(responseCode = "400", description = "요청 변수 에러",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class),
							examples = @ExampleObject(value = "{ \"code\": \"26\", \"msg\": \"fail\","
									+ " \"data\": {\"status\": \" REFUND_EXCEPTION\", "
									+ "\"msg\":\"환불 실패.\"} }")))
	})
	@GetMapping("/process")
	public ResponseEntity<ApiStandardResponse<List<RefundResult>>> processRefund() {
		List<RefundResult> result = refundService.processRefund();
		return ResponseEntity.ok(ApiStandardResponse.success(result));
	}
}
