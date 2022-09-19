package com.rachein.mmzf.exception;

import com.rachein.mmzf.result.CodeMsg;
import lombok.Getter;

/**
 * @author 计算机系 ITAEM 吴远健
 * @date 2022/2/27 20:36
 */
@Getter
public class GlobalException extends RuntimeException{
    private CodeMsg codeMsg;

    public GlobalException(CodeMsg codeMsg) {
        super(codeMsg.toString());
        this.codeMsg = codeMsg;
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }

    
}
