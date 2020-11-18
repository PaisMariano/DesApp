package unq.edu.tpi.desapp.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Provides handling for exceptions throughout this service.
     *
     * @param ex The target exception
     * @param request The current request
     */
    @ExceptionHandler({
            LocationNotFoundException.class,
            ProjectNotFoundException.class,
            DonationNotFoundException.class,
            UserNotFoundException.class,
            ElementAlreadyExists.class,
            BadRequestException.class
    })
    @Nullable
    public final ResponseEntity<ApiError> handleException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();

        LOGGER.error("Handling " + ex.getClass().getSimpleName() + " due to " + ex.getMessage());

        if (ex instanceof UserNotFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            UserNotFoundException unfe = (UserNotFoundException) ex;

            return handleUserNotFoundException(unfe, headers, status, request);
        } else if (ex instanceof DonationNotFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            DonationNotFoundException dnfe = (DonationNotFoundException) ex;

            return handleDonationNotFoundException(dnfe, headers, status, request);
        } else if (ex instanceof ProjectNotFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            ProjectNotFoundException pnfe = (ProjectNotFoundException) ex;

            return handleProjectNotFoundException(pnfe, headers, status, request);
        } else if (ex instanceof LocationNotFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            LocationNotFoundException lnfe = (LocationNotFoundException) ex;

            return handleLocationNotFoundException(lnfe, headers, status, request);
        } else if (ex instanceof ElementAlreadyExists) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            ElementAlreadyExists eaee = (ElementAlreadyExists) ex;

            return handleElementAlreadyExists(eaee, headers, status, request);
        } else if (ex instanceof BadRequestException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            BadRequestException bre = (BadRequestException) ex;

            return handleBadRequestException(bre, headers, status, request);
        } else {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("Unknown exception type: " + ex.getClass().getName());
            }

            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleExceptionInternal(ex, null, headers, status, request);
        }
    }
    /**
     * Customize the response for UserNotFoundException.
     *
     * @param ex The exception
     * @param headers The headers to be written to the response
     * @param status The selected response status
     * @return a {@code ResponseEntity} instance
     */
    protected ResponseEntity<ApiError> handleUserNotFoundException(UserNotFoundException ex,
                                                                   HttpHeaders headers, HttpStatus status,
                                                                   WebRequest request) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return handleExceptionInternal(ex, new ApiError(errors.get(0)), headers, status, request);
    }

    protected ResponseEntity<ApiError> handleProjectNotFoundException(ProjectNotFoundException ex,
                                                                   HttpHeaders headers, HttpStatus status,
                                                                   WebRequest request) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return handleExceptionInternal(ex, new ApiError(errors.get(0)), headers, status, request);
    }

    protected ResponseEntity<ApiError> handleLocationNotFoundException(LocationNotFoundException ex,
                                                                   HttpHeaders headers, HttpStatus status,
                                                                   WebRequest request) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return handleExceptionInternal(ex, new ApiError(errors.get(0)), headers, status, request);
    }

    protected ResponseEntity<ApiError> handleDonationNotFoundException(DonationNotFoundException ex,
                                                                   HttpHeaders headers, HttpStatus status,
                                                                   WebRequest request) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return handleExceptionInternal(ex, new ApiError(errors.get(0)), headers, status, request);
    }

    protected ResponseEntity<ApiError> handleElementAlreadyExists(ElementAlreadyExists ex,
                                                                       HttpHeaders headers, HttpStatus status,
                                                                       WebRequest request) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return handleExceptionInternal(ex, new ApiError(errors.get(0)), headers, status, request);
    }

    protected ResponseEntity<ApiError> handleBadRequestException(BadRequestException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return handleExceptionInternal(ex, new ApiError(errors.get(0)), headers, status, request);
    }

    protected ResponseEntity<ApiError> handleExceptionInternal(Exception ex, @Nullable ApiError body,
                                                               HttpHeaders headers, HttpStatus status,
                                                               WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }

        return new ResponseEntity<>(body, headers, status);
    }
}
