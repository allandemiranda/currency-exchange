package app.bank.dummy.advices;

import java.util.Map;
import lombok.NonNull;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.error.ErrorAttributeOptions.Include;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

@Component
public class CustomErrorAttributesHandler extends DefaultErrorAttributes {

  public Map<String, Object> extractErrorAttributes(final @NonNull WebRequest webRequest, final @NonNull HttpStatus httpStatus) {
    final ErrorAttributeOptions options = ErrorAttributeOptions.of(Include.PATH, Include.MESSAGE, Include.ERROR, Include.STATUS);
    final Map<String, Object> errorAttributes = getErrorAttributes(webRequest, options);
    errorAttributes.put("error", httpStatus.getReasonPhrase());
    errorAttributes.put("status", httpStatus.value());
    return errorAttributes;
  }
}
