package gnova.drawing.d2;

/**
 * 复合轮廓
 */
public interface MultiOutline extends Iterable<Outline> {

    Outline getOutlineAt(int n);

    float getOffsetAt(int n);

}
