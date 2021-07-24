package com.esmc.mcnp.core.constant;

import com.baomidou.mybatisplus.core.toolkit.Constants;

public interface CommomConstant extends Constants {
    String FILE_PATH = System.getProperty("user.dir");

    char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
}
