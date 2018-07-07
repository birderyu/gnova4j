package gnova.geometry.io;

import java.io.IOException;

/**
 * 几何对象输入输出异常
 */
public class GeometryIOException
        extends IOException {

    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5749683146083280476L;

    /**
     * 构造器
     */
    public GeometryIOException() {
        super();
    }

    /**
     * 构造器
     * @param message 异常信息
     */
	public GeometryIOException(String message) {
        super(message);
    }

    /**
     * 构造器
     * @param throwable 引起异常的原因
     */
    public GeometryIOException(Throwable throwable) {
        super(throwable);        
    }

    /**
     * 构造器
     * @param message 异常信息
     * @param throwable 引起异常的原因
     */
    public GeometryIOException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
