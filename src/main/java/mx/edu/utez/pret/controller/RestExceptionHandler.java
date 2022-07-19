package mx.edu.utez.pret.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import mx.edu.utez.pret.config.Response;

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

    /**
     * Get all error files without repeat.
     * 
     * @param List<FieldError> fieldErrors.
     * @return List with all errors without repeat.
     */
    public List<Map<String, Object>> mapErrorFields (List<FieldError> fieldErrors) {
        // Element in dictionary
        Map<String, Object> answ;

        // Response
        List<Map<String, Object>> errorList = new ArrayList<>();
        
        // Dictionary
        // TreeMap keep the elements sorted
        Map<Integer, Map<String, Object>> errorDictionary = new TreeMap<>();

        for (FieldError fe : fieldErrors) {
            answ = null;
            Integer key = null;
            String code = fe.getCode();

            // Build a new element to dictionary
            if (code != null)
                switch (code) {
                    // 2001 - Required data
                    case "NotNull":
                    case "NotBlank":
                    key = 2001;
                        answ = response.buildStandardResponse("Datos requeridos", key, "Uno o más campos requeridos se enviaron vacíos.");
                        break;
                    
                    // 2002 - Characters errors
                    case "DescriptionFormat":
                    case "MatriculaFormat":
                    case "ParagraphFormat":
                    case "NameFormat":
                        key = 2002;
                        answ = response.buildStandardResponse("Error de caracteres", key, "Algunos campos contienen caracteres no válidos.");
                        break;

                    // 2004 - Invalid format
                    case "Size":
                    case "DateTimeFormat":
                    case "EmailFormat":
                    case "PhoneNumberFormat":
                    case "Min":
                    case "Max":
                        key = 2004;
                        answ = response.buildStandardResponse("Formato inválido", key, "Uno o más campos contiene un formato incorrecto.");
                        break;

                    default:
                }

            // Check if type error exist in dictionary and the answer is not null
            if (!errorDictionary.containsKey(key) && answ != null)
                errorDictionary.put(key, answ);
        }

        // Dictionary to a List
        errorDictionary.forEach((k, v) -> errorList.add(v));

        return errorList;
    }

}
