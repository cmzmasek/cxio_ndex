package org.cxiondex.aspects.readers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.cxio.aspects.readers.ParserUtils;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;
import org.cxiondex.aspects.datamodels.ContextElement;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ContextFragmentReader implements AspectFragmentReader {
    private final ObjectMapper _m;
    private String             _time_stamp;

    public static ContextFragmentReader createInstance() {
        return new ContextFragmentReader();
    }

    private ContextFragmentReader() {
        _m = new ObjectMapper();
    }

    @Override
    public String getAspectName() {
        return ContextElement.NAME;
    }

    @Override
    public List<AspectElement> readAspectFragment(final JsonParser jp) throws IOException {
        JsonToken t = jp.nextToken();
        if (t != JsonToken.START_ARRAY) {
            throw new IOException("malformed CX json in element " + getAspectName());
        }
        final List<AspectElement> aspects = new ArrayList<AspectElement>();
        _time_stamp = null;
        while (t != JsonToken.END_ARRAY) {
            if (t == JsonToken.START_OBJECT) {
                final ObjectNode o = _m.readTree(jp);
                if (o == null) {
                    throw new IOException("malformed CX json in element " + getAspectName());
                }
                if ((_time_stamp == null) && ParserUtils.isTimeStamp(o)) {
                    _time_stamp = ParserUtils.getTimeStampValue(o);
                }
                else {
                    final ContextElement e = new ContextElement();
                    final Iterator<Entry<String, JsonNode>> it = o.fields();
                    while (it.hasNext()) {
                        final Entry<String, JsonNode> i = it.next();
                        e.put(i.getKey(), i.getValue().asText());
                    }
                    aspects.add(e);
                }
            }
            t = jp.nextToken();
        }
        return aspects;
    }

    @Override
    public String getTimeStamp() {
        return _time_stamp;
    }
}
