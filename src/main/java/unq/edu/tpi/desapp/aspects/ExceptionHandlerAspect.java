package unq.edu.tpi.desapp.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import unq.edu.tpi.desapp.exceptions.BadRequestException;
import unq.edu.tpi.desapp.exceptions.FailedEmailException;
import unq.edu.tpi.desapp.model.exceptions.*;

import javax.mail.MessagingException;
import java.io.IOException;

@Aspect
@Component
public class ExceptionHandlerAspect {

    @Around("@annotation(unq.edu.tpi.desapp.aspects.ExceptionAspect)")
    private Object aroundHandlerError(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (NullPointerException ex) {
            throw BadRequestException.createWith("JSON bad request or missing field.");

        } catch (EndDateMustBeAfterStartDate |
                InvalidFactor |
                InvalidMinClosePercentage |
                IntegerMustBePositive |
                BadEmailAddressException ex) {
            throw BadRequestException.createWith(ex.getMessage());

        } catch (IndexOutOfBoundsException |
                IllegalArgumentException ex){
            throw BadRequestException.createWith("Incorrect params given.");

        } catch (MessagingException |
                IOException e) {
            throw new FailedEmailException("Failed sending email.");
        }
    }
}
