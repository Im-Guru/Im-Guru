package kr.co.imguru.global.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class ResponseErrorFormat {

    private final String message;

//    private final String statusName;

    private final HttpStatus statusCode;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<ValidationException> validationExceptions;

    @Getter
    @Builder
    @RequiredArgsConstructor
    public static class ValidationException {
        private final String message;

        private final String field;

        public static ValidationException of(final FieldError fieldError) {

            return ValidationException.builder()
                    .message(fieldError.getDefaultMessage())
                    .field(fieldError.getField())
                    .build();

        }
    }
}
