package swlayer.project.demo.enggine.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHelper {
    public static <T> CommonResponse<T> successResponse(T data) {
        CommonResponse<T> response = new CommonResponse<>();
        response.setStatus(String.valueOf(HttpStatus.OK.value()));
        response.setMessage(HttpStatus.OK.name());
        response.setData(data);
        return response;
    }

    public static <T> ResponseEntity<CommonResponseErr<T>> errorResponse(String errors, int status, String message) {
        CommonResponseErr<T> response = new CommonResponseErr<>();
        response.setStatus(String.valueOf(status));
        response.setMessage(message);
        response.setError((T) errors);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
