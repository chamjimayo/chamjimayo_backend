package com.project.chamjimayo.controller;

import com.project.chamjimayo.controller.dto.request.ReviewRequest;
import com.project.chamjimayo.controller.dto.request.ReviewUpdateRequest;
import com.project.chamjimayo.controller.dto.response.ApiStandardResponse;
import com.project.chamjimayo.controller.dto.response.ErrorResponse;
import com.project.chamjimayo.controller.dto.response.ReviewResponse;
import com.project.chamjimayo.security.CustomUserDetails;
import com.project.chamjimayo.service.ReviewService;
import com.project.chamjimayo.service.dto.ReviewDto;
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
import javax.validation.Valid;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "review", description = "리뷰 API")
@RequiredArgsConstructor
@RequestMapping("/api/review")
@RestController
@Validated
public class ReviewController {

  private final ReviewService reviewService;

  @Operation(summary = "리뷰 조회", description = "특정 리뷰를 조회합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "리뷰 조회 성공"),
      @ApiResponse(responseCode = "400",
          description = "1. 파라미터가 부족합니다. \t\n"
              + "2. 리뷰 ID는 1 이상의 정수입니다. \t\n"
              + "3. 올바르지 않은 파라미터 값입니다.",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class),
              examples = @ExampleObject(value = "{ \"code\": \"02\", \"msg\": \"fail\","
                  + " \"data\": {\"status\": \"NEED_MORE_PARAMETER\", "
                  + "\"msg\":\"파라미터가 부족합니다.\"} }"))),
      @ApiResponse(responseCode = "404",
          description = "1. 리뷰를 찾을 수 없습니다. \t\n",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class),
              examples = @ExampleObject(value = "{ \"code\": \"16\", \"msg\": \"fail\","
                  + " \"data\": {\"status\": \"REVIEW_NOT_FOUND\", "
                  + "\"msg\":\"리뷰를 찾을 수 없습니다.\"} }")))})
  @GetMapping("/get/{reviewId}")
  public ResponseEntity<ApiStandardResponse<ReviewResponse>> getReview(
      @Parameter(description = "리뷰 ID", required = true, example = "1 (Long)")
      @PathVariable @Min(value = 1, message = "리뷰 ID는 1 이상의 정수입니다.") Long reviewId) {
    ReviewResponse reviewDto = reviewService.getReview(reviewId);
    ApiStandardResponse<ReviewResponse> apiStandardResponse = ApiStandardResponse.success(
        reviewDto);
    return ResponseEntity.ok(apiStandardResponse);
  }

  @Operation(summary = "리뷰 작성", description = "새로운 리뷰를 작성합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "리뷰 작성 성공."),
      @ApiResponse(responseCode = "400",
          description = "1. 화장실 ID를 입력해주세요. \t\n"
              + "2. 리뷰 내용을 입력해주세요. \t\n"
              + "3. 평점을 입력해주세요. \t\n"
              + "4. 평점은 0 ~ 5점으로 입력해주세요. \t\n"
              + "5. 올바르지 않은 JSON 형식입니다. \t\n"
              + "6. 유효한 토큰이 아닙니다. \t\n"
              + "7. 이미 리뷰가 작성되었습니다. 리뷰 수정을 이용해주세요.",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class),
              examples = @ExampleObject(value = "{ \"code\": \"23\", \"msg\": \"fail\","
                  + " \"data\": {\"status\": \"VALIDATION_EXCEPTION\", "
                  + "\"msg\":\"화장실 ID를 입력해주세요.\"} }"))),
      @ApiResponse(responseCode = "404",
          description = "1. 해당 화장실을 찾을 수 없습니다. \t\n"
              + "2. 사용된 화장실을 찾을 수 없습니다. \t\n"
              + "3. 유저를 찾지 못했습니다.",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class),
              examples = @ExampleObject(value = "{ \"code\": \"17\", \"msg\": \"fail\","
                  + " \"data\": {\"status\": \"RESTROOM_NOT_FOUND\", "
                  + "\"msg\":\"해당 화장실을 찾을 수 없습니다.\"} }")))})
  @Parameter(name = "Bearer-Token", description = "jwt token", schema = @Schema(type = "string"),
      in = ParameterIn.HEADER, example = "Bearer e1323423534")
  @PostMapping()
  public ResponseEntity<ApiStandardResponse<ReviewResponse>> createReview(
      @Valid @RequestBody ReviewRequest reviewRequest,
      @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails customUserDetails) {
    Long userId = customUserDetails.getId();
    Long usedRestroomId = reviewRequest.getUsedRestroomId();
    Long restroomId = reviewService.findRestroomIdByUsedRestroomId(usedRestroomId);
    ReviewDto reviewDto = ReviewDto.create(reviewRequest, restroomId);
    ReviewResponse createdReview = reviewService.createReview(userId, reviewDto);
    ApiStandardResponse<ReviewResponse> apiStandardResponse = ApiStandardResponse.success(
        createdReview);
    return ResponseEntity.ok(apiStandardResponse);
  }

  @Operation(summary = "리뷰 수정", description = "기존 리뷰를 수정합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "리뷰 수정 성공"),
      @ApiResponse(responseCode = "400",
          description = "1. 파라미터가 부족합니다. \t\n"
              + "2. 리뷰 ID는 1 이상의 정수입니다. \t\n"
              + "3. 올바르지 않은 파라미터 값입니다. \t\n"
              + "4. 리뷰 내용을 입력해주세요. \t\n"
              + "5. 평점을 입력해주세요. \t\n"
              + "6. 평점은 0 ~ 5점으로 입력해주세요. \t\n"
              + "7. 올바르지 않은 JSON 형식입니다. \t\n"
              + "8. 유효한 토큰이 아닙니다.",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class),
              examples = @ExampleObject(value = "{ \"code\": \"02\", \"msg\": \"fail\","
                  + " \"data\": {\"status\": \"NEED_MORE_PARAMETER\", "
                  + "\"msg\":\"파라미터가 부족합니다.\"} }"))),
      @ApiResponse(responseCode = "404",
          description = "1. 리뷰를 찾을 수 없습니다.",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class),
              examples = @ExampleObject(value = "{ \"code\": \"16\", \"msg\": \"fail\","
                  + " \"data\": {\"status\": \"REVIEW_NOT_FOUND\", "
                  + "\"msg\":\"리뷰를 찾을 수 없습니다.\"} }")))})
  @PatchMapping("/{reviewId}")
  public ResponseEntity<ApiStandardResponse<ReviewResponse>> updateReview(
      @Parameter(description = "리뷰 ID", required = true, example = "1 (Long)")
      @PathVariable @Min(value = 1, message = "리뷰 ID는 1 이상의 정수입니다.") Long reviewId,
      @Valid @RequestBody ReviewUpdateRequest reviewUpdateRequest) {
    ReviewDto reviewDto = ReviewDto.create(reviewUpdateRequest);
    ReviewResponse updatedReview = reviewService.updateReview(reviewId, reviewDto);
    ApiStandardResponse<ReviewResponse> apiStandardResponse = ApiStandardResponse.success(
        updatedReview);
    return ResponseEntity.ok(apiStandardResponse);
  }

  @Operation(summary = "리뷰 삭제", description = "특정 리뷰를 삭제합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "리뷰 삭제 성공"),
      @ApiResponse(responseCode = "400",
          description = "1. 파라미터가 부족합니다. \t\n"
              + "2. 리뷰 ID는 1 이상의 정수입니다. \t\n"
              + "3. 올바르지 않은 파라미터 값입니다. \t\n"
              + "4. 유효한 토큰이 아닙니다.",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class),
              examples = @ExampleObject(value = "{ \"code\": \"02\", \"msg\": \"fail\","
                  + " \"data\": {\"status\": \"NEED_MORE_PARAMETER\", "
                  + "\"msg\":\"파라미터가 부족합니다.\"} }"))),
      @ApiResponse(responseCode = "404",
          description = "1. 리뷰를 찾을 수 없습니다."
              + "2. 사용된 화장실을 찾을 수 없습니다. \t\n",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class),
              examples = @ExampleObject(value = "{ \"code\": \"16\", \"msg\": \"fail\","
                  + " \"data\": {\"status\": \"REVIEW_NOT_FOUND\", "
                  + "\"msg\":\"리뷰를 찾을 수 없습니다.\"} }")))})
  @DeleteMapping("/{reviewId}")
  public ResponseEntity<ApiStandardResponse<String>> deleteReview(
      @Parameter(description = "리뷰 ID", required = true, example = "1 (Long)")
      @PathVariable @Min(value = 1, message = "리뷰 ID는 1 이상의 정수입니다.") Long reviewId) {
    reviewService.deleteReview(reviewId);
    ApiStandardResponse<String> apiStandardResponse = ApiStandardResponse.success("리뷰 삭제 성공");
    return ResponseEntity.ok(apiStandardResponse);
  }

  @Operation(summary = "해당 유저의 모든 리뷰 조회 (최신순)", description = "특정 유저에 해당하는 모든 리뷰를 조회합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "리뷰 조회 성공"),
      @ApiResponse(responseCode = "400", description = "1. 유효한 토큰이 아닙니다.",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class),
              examples = @ExampleObject(value = "{ \"code\": \"06\", \"msg\": \"fail\","
                  + " \"data\": {\"status\": \"INVALID_TOKEN_EXCEPTION\", "
                  + "\"msg\":\"유효한 토큰이 아닙니다.\"} }"))),
      @ApiResponse(responseCode = "404",
          description = "1. 유저를 찾을 수 없습니다. \t\n",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class),
              examples = @ExampleObject(value = "{ \"code\": \"08\", \"msg\": \"fail\","
                  + " \"data\": {\"status\": \"USER_NOT_FOUND_EXCEPTION\", "
                  + "\"msg\":\"유저를 찾을 수 없습니다.\"} }")))})
  @Parameter(name = "Bearer-Token", description = "jwt token", schema = @Schema(type = "string"),
      in = ParameterIn.HEADER, example = "Bearer e1323423534")
  @GetMapping("/list")
  public ResponseEntity<ApiStandardResponse<List<ReviewResponse>>> getReviewsByUserId(
      @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails customUserDetails) {
    Long userId = customUserDetails.getId();
    List<ReviewResponse> reviewDtoList = reviewService.getReviewByUserID(userId);
    ApiStandardResponse<List<ReviewResponse>> apiStandardResponse = ApiStandardResponse.success(
        reviewDtoList);
    return ResponseEntity.ok(apiStandardResponse);
  }

  @Operation(summary = "해당 화장실의 모든 리뷰 조회 (최신순)", description = "특정 화장실에 해당하는 모든 리뷰를 조회합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "리뷰 조회 성공"),
      @ApiResponse(responseCode = "400",
          description = "1. 파라미터가 부족합니다. \t\n"
              + "2. 화장실 ID는 1 이상의 정수입니다. \t\n"
              + "3. 올바르지 않은 파라미터 값입니다.",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class),
              examples = @ExampleObject(value = "{ \"code\": \"02\", \"msg\": \"fail\","
                  + " \"data\": {\"status\": \"NEED_MORE_PARAMETER\", "
                  + "\"msg\":\"파라미터가 부족합니다.\"} }"))),
      @ApiResponse(responseCode = "404",
          description = "1. 화장실을 찾을 수 없습니다. \t\n",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class),
              examples = @ExampleObject(value = "{ \"code\": \"17\", \"msg\": \"fail\","
                  + " \"data\": {\"status\": \"RESTROOM_NOT_FOUND\", "
                  + "\"msg\":\"화장실을 찾을 수 없습니다.\"} }")))})
  @GetMapping("/list/latest/{restroomId}")
  public ResponseEntity<ApiStandardResponse<List<ReviewResponse>>> getReviewsByRestroomId(
      @Parameter(description = "화장실 ID", required = true, example = "1 (Long)")
      @PathVariable @Min(value = 1, message = "화장실 ID는 1 이상의 정수입니다.") Long restroomId) {
    List<ReviewResponse> revieweDtoList = reviewService.getReviewsByRestroomId(restroomId);
    ApiStandardResponse<List<ReviewResponse>> apiStandardResponse = ApiStandardResponse.success(
        revieweDtoList);
    return ResponseEntity.ok(apiStandardResponse);
  }

  @Operation(summary = "해당 화장실의 모든 리뷰 조회 (별점 높은 순)", description = "특정 화장실에 해당하는 모든 리뷰를 별점이 높은 순으로 조회합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "리뷰 조회 성공"),
      @ApiResponse(responseCode = "400",
          description = "1. 파라미터가 부족합니다. \t\n"
              + "2. 화장실 ID는 1 이상의 정수입니다. \t\n"
              + "3. 올바르지 않은 파라미터 값입니다.",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class),
              examples = @ExampleObject(value = "{ \"code\": \"02\", \"msg\": \"fail\","
                  + " \"data\": {\"status\": \"NEED_MORE_PARAMETER\", "
                  + "\"msg\":\"파라미터가 부족합니다.\"} }"))),
      @ApiResponse(responseCode = "404",
          description = "1. 화장실을 찾을 수 없습니다. \t\n",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class),
              examples = @ExampleObject(value = "{ \"code\": \"17\", \"msg\": \"fail\","
                  + " \"data\": {\"status\": \"RESTROOM_NOT_FOUND\", "
                  + "\"msg\":\"화장실을 찾을 수 없습니다.\"} }")))})
  @GetMapping("/list/high-rating/{restroomId}")
  public ResponseEntity<ApiStandardResponse<List<ReviewResponse>>> getReviewsByRestroomIdOrderByHighRating(
      @Parameter(description = "화장실 ID", required = true, example = "1 (Long)")
      @PathVariable @Min(value = 1, message = "화장실 ID는 1 이상의 정수입니다.") Long restroomId) {
    List<ReviewResponse> reviewDtoList = reviewService.getReviewsByRestroomIdOrderByHighRating(
        restroomId);
    ApiStandardResponse<List<ReviewResponse>> apiStandardResponse = ApiStandardResponse.success(
        reviewDtoList);
    return ResponseEntity.ok(apiStandardResponse);
  }

  @Operation(summary = "해당 화장실의 모든 리뷰 조회 (별점 낮은 순)", description = "특정 화장실에 해당하는 모든 리뷰를 별점이 낮은 순으로 조회합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "리뷰 조회 성공"),
      @ApiResponse(responseCode = "400",
          description = "1. 파라미터가 부족합니다. \t\n"
              + "2. 화장실 ID는 1 이상의 정수입니다. \t\n"
              + "3. 올바르지 않은 파라미터 값입니다.",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class),
              examples = @ExampleObject(value = "{ \"code\": \"02\", \"msg\": \"fail\","
                  + " \"data\": {\"status\": \"NEED_MORE_PARAMETER\", "
                  + "\"msg\":\"파라미터가 부족합니다.\"} }"))),
      @ApiResponse(responseCode = "404",
          description = "1. 화장실을 찾을 수 없습니다. \t\n",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class),
              examples = @ExampleObject(value = "{ \"code\": \"17\", \"msg\": \"fail\","
                  + " \"data\": {\"status\": \"RESTROOM_NOT_FOUND\", "
                  + "\"msg\":\"화장실을 찾을 수 없습니다.\"} }")))})
  @GetMapping("/list/low-rating/{restroomId}")
  public ResponseEntity<ApiStandardResponse<List<ReviewResponse>>> getReviewsByRestroomIdOrderByLowRating(
      @Parameter(description = "화장실 ID", required = true, example = "1 (Long)")
      @PathVariable @Min(value = 1, message = "화장실 ID는 1 이상의 정수입니다.") Long restroomId) {
    List<ReviewResponse> reviewDtoList = reviewService.getReviewsByRestroomIdOrderByLowRating(
        restroomId);
    ApiStandardResponse<List<ReviewResponse>> apiStandardResponse = ApiStandardResponse.success(
        reviewDtoList);
    return ResponseEntity.ok(apiStandardResponse);
  }
}
