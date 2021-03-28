package Client.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@Aspect
public class LoggableAspect {
    private static final Logger LOGGER = Logger.getLogger(LoggableAspect.class.getName());

    @Pointcut("@annotation(Loggable)")
    public void apDefinition() { }

    @Before("apDefinition()")
    public void before(JoinPoint joinPoint) {
        LOGGER.info(
                String.format(
                    "[Loggable] Entering Method {%s} of {%s}",
                    joinPoint.getSignature().toString(),
                    joinPoint.getSignature().getDeclaringType()
                )
        );
    }

    @After("apDefinition()")
    public void after(JoinPoint joinPoint) {
        LOGGER.info(
                String.format("[Loggable] Exiting Method {%s} of {%s}",
                    joinPoint.getSignature().getName(),
                    joinPoint.getSignature().getDeclaringType()
                )
        );
    }

    @AfterThrowing(value = "apDefinition()", throwing = "ex")
    public void throwing(RuntimeException ex) {
        LOGGER.log(Level.SEVERE, "[Loggable] " + ex.getMessage());
    }

    @AfterReturning(value = "apDefinition()", returning = "returnObject")
    public void returning(Object returnObject) {
        LOGGER.info("[Loggable] Return value {" + returnObject + "}");
    }

}