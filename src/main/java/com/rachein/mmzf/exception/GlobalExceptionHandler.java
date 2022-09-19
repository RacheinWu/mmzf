package com.rachein.mmzf.exception;



import com.rachein.mmzf.result.CodeMsg;
import com.rachein.mmzf.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.BindException;
import java.util.List;

/**
 * @author 计算机系 ITAEM 吴远健
 * @date 2022/2/27 20:35
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        log.error(e.toString());
        if (e instanceof GlobalException) {
            return Result.error(((GlobalException) e).getCodeMsg());
        } else if (e instanceof BindException) {
            org.springframework.validation.BindException ex = (org.springframework.validation.BindException)e;
            List<ObjectError> errors = ex.getAllErrors();//绑定错误返回很多错误，是一个错误列表，只需要第一个错误
            ObjectError error = errors.get(0);
            String msg = error.getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));//给状态码填充参数
        }
        else {
            return Result.error(CodeMsg.ERROR_SERVER);
        }


    }

}
