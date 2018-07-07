package gnova.config.reader.xml;

import gnova.core.annotation.NotNull;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DomHelper {

    @NotNull
    public static Element readElement(@NotNull String xml)
            throws DocumentException {
        Document doc = DocumentHelper.parseText(xml);
        return doc.getRootElement();
    }

    public static Map<String, Object> subElements(@NotNull Element root) {
        Map<String, Object> cache = new HashMap<>();
        List<Element> elements = root.elements();
        for (Element element : elements) {
            Object o = cache.get(element.getName());
            if (o == null) {
                cache.put(element.getName(), element);
            } else {
                if (o instanceof Element) {
                    List<Element> list = new ArrayList<>();
                    list.add((Element) o);
                    list.add(element);
                    cache.put(element.getName(), list);
                } else {
                    List<Element> list = (List<Element>) o;
                    list.add(element);
                    cache.put(element.getName(), list);
                }
            }
        }
        return cache;
    }

}
