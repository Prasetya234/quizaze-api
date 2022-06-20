package swlayer.project.demo.enggine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import swlayer.project.demo.enggine.response.ResponseHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notFoundAdviceController(NotFoundException notFoundException) {
        return ResponseHelper.errorResponse(notFoundException.getMessage(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name());
    }
    @ExceptionHandler(BussinesException.class)
    public ResponseEntity<?> bussinesExceprionAdviceController(BussinesException BussinesException) {
        return ResponseHelper.errorResponse(BussinesException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name());
    }
}
