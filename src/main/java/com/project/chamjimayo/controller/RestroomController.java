package com.project.chamjimayo.controller;

import com.project.chamjimayo.controller.dto.ApiStandardResponse;
import com.project.chamjimayo.controller.dto.BaseException;
import com.project.chamjimayo.controller.dto.EnrollRestroomRequest;
import com.project.chamjimayo.controller.dto.ErrorResponse;
import com.project.chamjimayo.controller.dto.RestroomDetail;
import com.project.chamjimayo.controller.dto.RestroomNearByRequest;
import com.project.chamjimayo.controller.dto.RestroomResponse;
import com.project.chamjimayo.service.RestroomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;
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
@Tag(name = "restroom", description = "화장실관련 API")
@RestController
@RequestMapping("/api/restroom")
public class RestroomController {

	private final RestroomService restroomService;

	@Operation(summary = "공공화장실 데이터 입력", description = "공공화장실 데이터를 가져와 DB에 저장")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "공공화장실 데이터 입력")
	})
	@PostMapping("/import")
	public ResponseEntity<ApiStandardResponse<List<RestroomResponse>>> importRestroom() {
		return ResponseEntity.ok(ApiStandardResponse.success(restroomService.importRestroom()));
	}

	@Operation(summary = "유료화장실 등록", description = "받은 유료화장실 정보로 화장실 객체 생성 후 DB에 저장")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "유료화장실 등록 성공"),
		@ApiResponse(responseCode = "400", description = "요청 변수 에러",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class),
				examples = @ExampleObject(value = "{ \"code\": \"20\", \"msg\": \"fail\","
					+ " \"data\": {\"status\": \" RESTROOM_NAME_DUPLICATE_EXCEPTION\", "
					+ "\"msg\":\"화장실명이 이미 존재합니다.\"} }")))
	})
	@PostMapping("/enroll")
	public ResponseEntity<ApiStandardResponse<RestroomResponse>> enrollRestroom(
		@RequestBody EnrollRestroomRequest enrollRestroomRequest) throws BaseException {
		return ResponseEntity.ok(
			ApiStandardResponse.success(restroomService.enrollRestroom(enrollRestroomRequest)));
	}

	@Operation(summary = "주변 유/무료 화장실리스트",
		description = "받은 좌표값으로부터 설정한 거리 내부에 있는 화장실 리스트를 반환, 거리를 설정하지 않으면 default로 1KM")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "주변 화장실 리스트 검색 성공"),
		@ApiResponse(responseCode = "400", description = "요청 변수 에러",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class),
				examples = @ExampleObject(value = "{ \"code\": \"17\", \"msg\": \"fail\","
					+ " \"data\": {\"status\": \" RESTROOM_NOT_FOUND\", "
					+ "\"msg\":\"주변에 화장실이 존재하지 않습니다.\"} }")))
	})
	@GetMapping("/nearby/{publicOrPaidOrEntire}")
	public ResponseEntity<ApiStandardResponse<List<RestroomDetail>>> restroomNearBy(
		@PathVariable(value = "publicOrPaidOrEntire") String publicOrPaidOrEntire,
		@RequestParam(value = "distance", required = false) Optional<Double> distance,
		@RequestParam double longitude, double latitude) {
		distance = Optional.of(distance.orElse(1000D));
		RestroomNearByRequest restroomNearByRequest = new RestroomNearByRequest(longitude,
			latitude, publicOrPaidOrEntire, distance.get());
		return ResponseEntity.ok(
			ApiStandardResponse.success(restroomService.nearBy(restroomNearByRequest)));
	}

	@Operation(summary = "화장실 세부 정보", description = "받은 화장실Id로 화장실 세부 정보를 검색 및 반환")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "주변 화장실 세부 정보 조회 성공"),
		@ApiResponse(responseCode = "400", description = "요청 변수 에러",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class),
				examples = @ExampleObject(value = "{ \"code\": \"17\", \"msg\": \"fail\","
					+ " \"data\": {\"status\": \" RESTROOM_NOT_FOUND\", "
					+ "\"msg\":\"화장실을 찾을 수 없습니다.\"} }")))
	})
	@GetMapping("/detail")
	public ResponseEntity<ApiStandardResponse<RestroomDetail>> restroomDetail(
		@RequestParam Long restroomId)
		throws BaseException {
		return ResponseEntity.ok(
			ApiStandardResponse.success(restroomService.restroomDetail(restroomId)));
	}
}
