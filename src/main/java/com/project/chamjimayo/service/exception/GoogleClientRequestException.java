package com.project.chamjimayo.service.exception;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GoogleClientRequestException extends RuntimeException {

  public GoogleClientRequestException(Throwable cause) {
    super(extractMessageFromException(cause), cause);
  }

  public ErrorStatus toErrorCode() {
    return ErrorStatus.GOOGLE_CLIENT_REQUEST_EXCEPTION;
  }

  private static String extractMessageFromException(Throwable e) {
    String exceptionMessage = e.getLocalizedMessage();
    Pattern pattern = Pattern.compile("\\{(.*?)\\}$", Pattern.DOTALL);
    Matcher matcher = pattern.matcher(exceptionMessage);

    if (matcher.find()) {
      JsonObject jsonObject = JsonParser.parseString("{" + matcher.group(1) + "}")
          .getAsJsonObject();
      return jsonObject.get("code").getAsString() + " " + jsonObject.get("message").getAsString();
    }
    return "구글 api 요청에 실패했습니다.";
  }
}
