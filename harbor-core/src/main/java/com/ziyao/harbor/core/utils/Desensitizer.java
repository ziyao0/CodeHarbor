package com.ziyao.harbor.core.utils;

/**
 * 脱敏器
 *
 * @author ziyao zhang
 * @since 2024/3/22
 */
public abstract class Desensitizer {


    /**
     * 脱敏
     *
     * @param str  字符串
     * @param type 脱敏类型;可以脱敏：用户id、中文名、身份证号、座机号、手机号、地址、电子邮件、密码
     * @return 脱敏之后的字符串
     * @author dazer and neusoft and qiaomu
     * @since 5.6.2
     */
    public static String desensitize(CharSequence str, Type type) {
        if (Strings.isEmpty(str)) {
            return Strings.EMPTY;
        }
        String newStr = String.valueOf(str);
        switch (type) {
            case USER_ID:
                newStr = String.valueOf(userId());
                break;
            case CHINESE_NAME:
                newStr = chineseName(String.valueOf(str));
                break;
            case ID_CARD:
                newStr = idCardNum(String.valueOf(str), 1, 2);
                break;
            case FIXED_PHONE:
                newStr = fixedPhone(String.valueOf(str));
                break;
            case MOBILE_PHONE:
                newStr = mobilePhone(String.valueOf(str));
                break;
            case ADDRESS:
                newStr = address(String.valueOf(str), 8);
                break;
            case EMAIL:
                newStr = email(String.valueOf(str));
                break;
            case PASSWORD:
                newStr = password(String.valueOf(str));
                break;
            case CAR_LICENSE:
                newStr = carLicense(String.valueOf(str));
                break;
            case BANK_CARD:
                newStr = bankCard(String.valueOf(str));
                break;
            default:
        }
        return newStr;
    }

    /**
     * 【用户id】不对外提供userId
     *
     * @return 脱敏后的主键
     */
    public static Long userId() {
        return 0L;
    }

    /**
     * 【中文姓名】只显示第一个汉字，其他隐藏为2个星号，比如：李**
     *
     * @param fullName 姓名
     * @return 脱敏后的姓名
     */
    public static String chineseName(String fullName) {
        if (Strings.isEmpty(fullName) || fullName.length() < 2) {
            return fullName;
        }
        if (fullName.length() == 2)
            return Strings.hide(fullName, 1, fullName.length());
        else
            return Strings.hide(fullName, 1, fullName.length() - 1);
    }

    /**
     * 【身份证号】前1位 和后2位
     *
     * @param idCardNum 身份证
     * @param front     保留：前面的front位数；从1开始
     * @param end       保留：后面的end位数；从1开始
     * @return 脱敏后的身份证
     */
    public static String idCardNum(String idCardNum, int front, int end) {
        //身份证不能为空
        if (Strings.isEmpty(idCardNum)) {
            return Strings.EMPTY;
        }
        //需要截取的长度不能大于身份证号长度
        if ((front + end) > idCardNum.length()) {
            return Strings.EMPTY;
        }
        //需要截取的不能小于0
        if (front < 0 || end < 0) {
            return Strings.EMPTY;
        }
        return Strings.hide(idCardNum, front, idCardNum.length() - end);
    }

    /**
     * 【固定电话 前四位，后两位
     *
     * @param num 固定电话
     * @return 脱敏后的固定电话；
     */
    public static String fixedPhone(String num) {
        if (Strings.isEmpty(num)) {
            return Strings.EMPTY;
        }
        return Strings.hide(num, 4, num.length() - 2);
    }

    /**
     * 【手机号码】前三位，后4位，其他隐藏，比如135****2210
     *
     * @param num 移动电话；
     * @return 脱敏后的移动电话；
     */
    public static String mobilePhone(String num) {
        if (Strings.isEmpty(num)) {
            return Strings.EMPTY;
        }
        return Strings.hide(num, 3, num.length() - 4);
    }

    /**
     * 【地址】只显示到地区，不显示详细地址，比如：北京市海淀区****
     *
     * @param address       家庭住址
     * @param sensitiveSize 敏感信息长度
     * @return 脱敏后的家庭地址
     */
    public static String address(String address, int sensitiveSize) {
        if (Strings.isEmpty(address)) {
            return Strings.EMPTY;
        }
        int length = address.length();
        return Strings.hide(address, length - sensitiveSize, length);
    }

    /**
     * 【电子邮箱】邮箱前缀仅显示第一个字母，前缀其他隐藏，用星号代替，@及后面的地址显示，比如：d**@126.com
     *
     * @param email 邮箱
     * @return 脱敏后的邮箱
     */
    public static String email(String email) {
        if (Strings.isEmpty(email)) {
            return Strings.EMPTY;
        }
        int index = Strings.indexOf(email, '@');
        if (index <= 1) {
            return email;
        }
        return Strings.hide(email, 1, index);
    }

    /**
     * 【密码】密码的全部字符都用*代替，比如：******
     *
     * @param password 密码
     * @return 脱敏后的密码
     */
    public static String password(String password) {
        if (Strings.isEmpty(password)) {
            return Strings.EMPTY;
        }
        return Strings.repeat('*', password.length());
    }

    /**
     * 【中国车牌】车牌中间用*代替
     * eg1：null       -》 ""
     * eg1：""         -》 ""
     * eg3：苏D40000   -》 苏D4***0
     * eg4：陕A12345D  -》 陕A1****D
     * eg5：京A123     -》 京A123     如果是错误的车牌，不处理
     *
     * @param carLicense 完整的车牌号
     * @return 脱敏后的车牌
     */
    public static String carLicense(String carLicense) {
        if (Strings.isEmpty(carLicense)) {
            return Strings.EMPTY;
        }
        // 普通车牌
        if (carLicense.length() == 7) {
            carLicense = Strings.hide(carLicense, 3, 6);
        } else if (carLicense.length() == 8) {
            // 新能源车牌
            carLicense = Strings.hide(carLicense, 3, 7);
        }
        return carLicense;
    }

    /**
     * 银行卡号脱敏
     * eg: 1101 **** **** **** 3256
     *
     * @param bankCardNo 银行卡号
     * @return 脱敏之后的银行卡号
     * @since 5.6.3
     */
    public static String bankCard(String bankCardNo) {
        if (Strings.isEmpty(bankCardNo)) {
            return bankCardNo;
        }
        bankCardNo = Strings.trim(bankCardNo);
        if (bankCardNo.length() < 9) {
            return bankCardNo;
        }

        final int length = bankCardNo.length();
        final int midLength = length - 8;
        final StringBuilder buf = new StringBuilder();

        buf.append(bankCardNo, 0, 4);
        for (int i = 0; i < midLength; ++i) {
            if (i % 4 == 0) {
                buf.append(Chars.SPACE);
            }
            buf.append('*');
        }
        buf.append(Chars.SPACE).append(bankCardNo, length - 4, length);
        return buf.toString();
    }

    public enum Type {
        //用户id
        USER_ID,
        //中文名
        CHINESE_NAME,
        //身份证号
        ID_CARD,
        //座机号
        FIXED_PHONE,
        //手机号
        MOBILE_PHONE,
        //地址
        ADDRESS,
        //电子邮件
        EMAIL,
        //密码
        PASSWORD,
        //中国大陆车牌，包含普通车辆、新能源车辆
        CAR_LICENSE,
        //银行卡
        BANK_CARD
    }

    private Desensitizer() {
    }
}
