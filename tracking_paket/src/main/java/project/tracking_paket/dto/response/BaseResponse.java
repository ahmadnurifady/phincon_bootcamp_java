package project.tracking_paket.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {
    private HttpStatus codeHttp;
    private String message;
    private Object data;
}
