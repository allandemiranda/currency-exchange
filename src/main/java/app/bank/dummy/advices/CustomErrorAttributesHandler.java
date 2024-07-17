package app.bank.dummy.advices;

import java.util.Map;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.error.ErrorAttributeOptions.Include;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

@Component
public class CustomErrorAttributesHandler extends DefaultErrorAttributes {

  public Map<String, Object> extractErrorAttributes(final WebRequest webRequest) {
    final ErrorAttributeOptions options = ErrorAttributeOptions.of(Include.PATH, Include.MESSAGE);
    return getErrorAttributes(webRequest, options);
  }
}
