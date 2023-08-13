package com.project.chamjimayo.controller;

import com.project.chamjimayo.controller.dto.PageDto;
import com.project.chamjimayo.controller.dto.response.ApiStandardResponse;
import com.project.chamjimayo.controller.dto.request.EnrollRestroomRequest;
import com.project.chamjimayo.controller.dto.response.ErrorResponse;
import com.project.chamjimayo.controller.dto.response.NearByResponse;
import com.project.chamjimayo.controller.dto.response.RestroomDetailResponse;
import com.project.chamjimayo.controller.dto.request.RestroomNearByRequest;
import com.project.chamjimayo.controller.dto.response.RestroomResponse;
import com.project.chamjimayo.controller.dto.request.UsingRestroomRequest;
import com.project.chamjimayo.controller.dto.response.UsingRestroomResponse;
import com.project.chamjimayo.security.CustomUserDetails;
import com.project.chamjimayo.service.RestroomService;
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
      @RequestBody EnrollRestroomRequest enrollRestroomRequest) {
    return ResponseEntity.ok(
        ApiStandardResponse.success(restroomService.enrollRestroom(enrollRestroomRequest)));
  }

  @Operation(summary = "주변 유/무료 화장실리스트",
      description = "받은 좌표값으로부터 설정한 거리 내부에 있는 화장실 리스트를 반환, 거리 순으로 화장실 정렬, 거리를 설정하지 않으면 default로 1KM")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "주변 화장실 리스트 검색 성공"),
      @ApiResponse(responseCode = "400", description = "요청 변수 에러",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class),
              examples = @ExampleObject(value = "{ \"code\": \"17\", \"msg\": \"fail\","
                  + " \"data\": {\"status\": \" RESTROOM_NOT_FOUND\", "
                  + "\"msg\":\"주변에 화장실이 존재하지 않습니다.\"} }")))
  })
  @Parameter(name = "sortBy", schema = @Schema(type = "string"),
      in = ParameterIn.QUERY, example = "distance / rating", description = "default = distance")
  @Parameter(name = "page", schema = @Schema(type = "int"),
      in = ParameterIn.QUERY, example = "1", description = "받고 싶은 페이지 (입력하지 않으면 페이징 하지 않은 전체 데이터 반환)")
  @Parameter(name = "size", schema = @Schema(type = "int"),
      in = ParameterIn.QUERY, example = "10", description = "한 페이지에 담고싶은 데이터 개수")
  @Parameter(name = "publicOrPaidOrEntire", schema = @Schema(type = "string"),
      in = ParameterIn.PATH, example = "public/paid/entire")
  @GetMapping("/nearby/{publicOrPaidOrEntire}")
  public ResponseEntity<ApiStandardResponse<List<NearByResponse>>> restroomNearBy(
      @PathVariable(value = "publicOrPaidOrEntire") String publicOrPaidOrEntire,
      @RequestParam(defaultValue = "1000") Double distance,
      @RequestParam double longitude,
      @RequestParam double latitude,
      @RequestParam(defaultValue = "distance") String sortBy,
      @RequestParam(defaultValue = "-1") int page,
      @RequestParam(defaultValue = "10") int size) {
    RestroomNearByRequest restroomNearByRequest = new RestroomNearByRequest(longitude,
        latitude, publicOrPaidOrEntire, distance, sortBy);
    PageDto pageDto = new PageDto(page,size);
    return ResponseEntity.ok(
        ApiStandardResponse.success(restroomService.nearBy(restroomNearByRequest,pageDto)));
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
  public ResponseEntity<ApiStandardResponse<RestroomDetailResponse>> restroomDetail(
      @RequestParam Long restroomId) {
    return ResponseEntity.ok(
        ApiStandardResponse.success(restroomService.restroomDetail(restroomId)));
  }

  @Operation(summary = "화장실 사용", description = "받은 화장실 Id로 화장실 사용 로직 수행")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "화장실 사용 로직 성공"),
      @ApiResponse(responseCode = "400", description = "요청 변수 에러",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class),
              examples = @ExampleObject(value = "{ \"code\": \"17\", \"msg\": \"fail\","
                  + " \"data\": {\"status\": \" RESTROOM_NOT_FOUND\", "
                  + "\"msg\":\"화장실을 찾을 수 없습니다.\"} }")))
  })
  @Parameter(name = "Bearer-Token", description = "jwt token", schema = @Schema(type = "string"),
      in = ParameterIn.HEADER, example = "Bearer e1323423534")
  @PostMapping("/use")
  public ResponseEntity<ApiStandardResponse<UsingRestroomResponse>> usingRestroom(
      @RequestBody UsingRestroomRequest usingRestroomRequest,
      @Parameter(hidden = true) @AuthenticationPrincipal
      CustomUserDetails userDetails) {
    long userId = userDetails.getId();
    return ResponseEntity.ok(
        ApiStandardResponse.success(
            restroomService.usingRestroom(userId, usingRestroomRequest.getRestroomId())));
  }

  @Operation(summary = "화장실 사용종료", description = "받은 화장실 Id로 화장실 사용종료 로직 수행")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "화장실 사용종료 로직 성공"),
      @ApiResponse(responseCode = "400", description = "요청 변수 에러",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class),
              examples = @ExampleObject(value = "{ \"code\": \"17\", \"msg\": \"fail\","
                  + " \"data\": {\"status\": \" RESTROOM_NOT_FOUND\", "
                  + "\"msg\":\"화장실을 찾을 수 없습니다.\"} }")))
  })
  @Parameter(name = "Bearer-Token", description = "jwt token", schema = @Schema(type = "string"),
      in = ParameterIn.HEADER, example = "Bearer e1323423534")
  @PostMapping("/endofuse")
  public ResponseEntity<ApiStandardResponse<UsingRestroomResponse>> endOfUsingRestroom(
      @Parameter(hidden = true) @AuthenticationPrincipal
      CustomUserDetails userDetails) {
    long userId = userDetails.getId();
    return ResponseEntity.ok(ApiStandardResponse.success(
        restroomService.endOfUsingRestroom(userId)));
  }
}
