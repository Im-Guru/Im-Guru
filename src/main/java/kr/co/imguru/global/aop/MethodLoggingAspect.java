package kr.co.imguru.global.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Component
public class MethodLoggingAspect {

    @Pointcut("execution(* kr.co.imguru.domain.member.service.MemberService.*(..))")
    private void memberService() {

    }

    @Pointcut("execution(* kr.co.imguru.domain.post.service.PostService.*(..))")
    private void postService() {

    }

    @Pointcut("execution(* kr.co.imguru.domain.guru.service.GuruInfoService.*(..))")
    private void guruService() {

    }

    @Pointcut("execution(* kr.co.imguru.domain.message.service.MessageService.*(..))")
    private void messageService() {

    }

    @Pointcut("execution(* kr.co.imguru.domain.reply.service.ReplyService.*(..))")
    private void replyService() {

    }

    @Pointcut("execution(* kr.co.imguru.domain.report.service.ReportPostService.*(..))")
    private void reportPostService() {

    }

    @Pointcut("execution(* kr.co.imguru.domain.report.service.ReportReplyService.*(..))")
    private void reportReplyService() {

    }

    @Pointcut("execution(* kr.co.imguru.domain.review.service.ReviewService.*(..))")
    private void reviewService() {

    }

    @Pointcut("execution(* kr.co.imguru.domain.skill.service.SkillService.*(..))")
    private void skillService() {

    }

    @Around("memberService() || postService() || replyService() || guruService() || skillService() || reportPostService() || reportReplyService() || messageService() || reviewService()")
    public Object logServiceTime(ProceedingJoinPoint joinPoint) throws Throwable {

        String className = joinPoint.getTarget().getClass().getSimpleName();

        String methodName = joinPoint.getSignature().getName();

        StopWatch stopWatch = new StopWatch();

        stopWatch.start();

        Object result = null;

        try {
            result = joinPoint.proceed();
            stopWatch.stop();
            log.info("[serviceTime] ClassName:{} - MethodName:{} : {}ms", className, methodName, stopWatch.getTotalTimeMillis());
        } catch (Throwable throwable) {
            // 예외 정보 로깅
            log.error("[EXCEPTION] {}-{} : {}", className, methodName, throwable.getMessage(), throwable);
            throw throwable;
        }
        return result;
    }

}
