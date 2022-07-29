package mx.edu.utez.pret.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import mx.edu.utez.pret.config.Response;
import mx.edu.utez.pret.pojo.ErrorPojo;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @Autowired
    private Response response;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, org.springframework.http.HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(response.buildStandardResponse("Datos invalidos", mapErrorFields(ex.getBindingResult().getFieldErrors()), "Favor de verificar los datos"), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(response.buildStandardResponse("Error", 5000, "Favor de verificar los parámetros de envío."), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, org.springframework.http.HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(response.buildStandardResponse("Error en el envío", 2005, "Favor de verificar los datos."), HttpStatus.BAD_REQUEST);
    }
    
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, org.springframework.http.HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(response.buildStandardResponse("No encontrado", 2003, "El recurso o petición solicitado no se encontró."), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolationException(HttpServletRequest req, DataIntegrityViolationException e) {
        return new ResponseEntity<>(response.buildStandardResponse("Datos duplicados", 2005, "Favor de verificar los datos."), HttpStatus.BAD_REQUEST);
    }

    /**
     * Get all error files without repeat.
     * 
     * @param List<FieldError> fieldErrors.
     * @return List with all errors without repeat.
     */
    public List<ErrorPojo> mapErrorFields (List<FieldError> fieldErrors) {
        // Element in dictionary
        ErrorPojo answ;

        // Response
        List<ErrorPojo> errorList = new ArrayList<>();
        
        // Dictionary
        // TreeMap keep the elements sorted
        Map<Integer, ErrorPojo> errorDictionary = new TreeMap<>();

        for (FieldError fe : fieldErrors) {
            answ = null;
            Integer key = null;
            String code = fe.getCode();

            // Build a new element to dictionary
            if (code != null) {
                switch (code) {
                    // 2001 - Required data
                    case "NotNull":
                    case "NotBlank":
                    key = 2001;
                        answ = new ErrorPojo(key, "Datos requeridos", "Uno o más campos requeridos se enviaron vacíos.");
                        break;
                    
                    // 2002 - Characters errors
                    case "DescriptionFormat":
                    case "MatriculaFormat":
                    case "ParagraphFormat":
                    case "NameFormat":
                    case "JobTitleFormat":
                        key = 2002;
                        answ = new ErrorPojo(key, "Error de caracteres", "Algunos campos contienen caracteres no válidos.");
                        break;

                    // 2004 - Invalid format
                    case "Size":
                    case "DateTimeFormat":
                    case "EmailFormat":
                    case "PhoneFormat":
                    case "Min":
                    case "Max":
                        key = 2004;
                        answ = new ErrorPojo(key, "Formato inválido", "Uno o más campos contiene un formato incorrecto.");
                        break;

                    default:
                }

                // Check if type error exist in dictionary and the answer is not null
                if (!errorDictionary.containsKey(key))
                    errorDictionary.put(key, answ);
            }
        }

        // Dictionary to a List
        errorDictionary.forEach((k, v) -> errorList.add(v));

        return errorList;
    }
}
