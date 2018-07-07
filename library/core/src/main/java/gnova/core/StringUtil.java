package gnova.core;

import gnova.core.annotation.NotNull;

/**
 * 字符串的帮助类
 *
 * @author birderyu
 * @version 1.0.0
 */
public class StringUtil {

    /**
     * 判断一个字符串是否为null或为空
     *
     * @param s 字符串，可以为null
     * @return 若为null或为空，则返回true，否则返回false
     */
    public static boolean isNullOrEmpty(String s) {
        return s == null || isBlank(s);
    }

    /**
     * 判断一个字符串是否都为空格
     *
     * @param str 字符串，不允许为null
     * @return 若都为空格，则返回true，否则返回false
     */
    public static boolean isBlank(@NotNull String str) {
        int length;
        if (str != null && (length = str.length()) != 0) {
            for(int i = 0; i < length; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }

}
