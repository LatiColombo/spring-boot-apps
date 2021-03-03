package ar.com.ada.second.tdvr.advice;

import ar.com.ada.second.tdvr.advice.validation.RestErrorsResponse;
import ar.com.ada.second.tdvr.exception.BusinessLogicException;
import ar.com.ada.second.tdvr.exception.EntityError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;

@RestControllerAdvice  // esta clase estará pendiente de las excepciones que detallemos acá (es como una alerta)
public class businessLogicExceptionHandler {

    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity handlerBusinessLogicException(BusinessLogicException e, NativeWebRequest request) {

        // Cada vez que ocurra alguna excepción de este tipo con este método voy a:
        // generar el status, generar la respuesta y enviar esa respuesta.
        HttpStatus httpStatus = e.getHttpStatus() !=null
                ? e.getHttpStatus()
                : HttpStatus.INTERNAL_SERVER_ERROR;
        //
        RestErrorsResponse restErrorsResponse = new RestErrorsResponse<EntityError>(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                e.getEntityErrors()
        );

        return ResponseEntity
                .status(httpStatus)
                .body(restErrorsResponse);
    }
}
