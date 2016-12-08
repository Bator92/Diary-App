package andras.bator.diaryapp.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;

/**
 * Created by abator on 12/7/2016.
 */
public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {
    public LocalDateTime unmarshal(String v) throws Exception {
        return LocalDateTime.parse(v);
    }

    public String marshal(LocalDateTime v) throws Exception {
        return v.toString();
    }
}
