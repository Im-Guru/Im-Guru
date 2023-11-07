package kr.co.imguru.global.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseFormat<T> {

    private Optional<T> data;

    private String message;

    private HttpStatus statusCode;

    // Success - If the ResponseStatus is declared + return Only Message
    public static <T> ResponseFormat<T> success(ResponseStatus responseStatus) {
        return ResponseFormat.<T>builder()
                .data(Optional.empty())
                .message(responseStatus.getMessage())
                .statusCode(responseStatus.getStatusCode())
                .build();
    }


    // Success - If the ResponseStatus is declared + return Message And Data
    public static <T> ResponseFormat<T> successWithData(ResponseStatus responseStatus,
                                                        T data) {
        return ResponseFormat.<T>builder()
                .data(Optional.ofNullable(data))
                .message(responseStatus.getMessage())
                .statusCode(responseStatus.getStatusCode())
                .build();
    }

}
