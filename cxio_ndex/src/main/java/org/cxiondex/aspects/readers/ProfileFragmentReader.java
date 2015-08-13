package org.cxiondex.aspects.readers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cxio.aspects.readers.ParserUtils;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;
import org.cxiondex.aspects.datamodels.ProfileElement;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ProfileFragmentReader implements AspectFragmentReader {
    private final ObjectMapper _m;
    private String             _time_stamp;

    public static ProfileFragmentReader createInstance() {
        return new ProfileFragmentReader();
    }

    private ProfileFragmentReader() {
        _m = new ObjectMapper();
    }

    @Override
    public String getAspectName() {
        return ProfileElement.NAME;
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
                    final String name = ParserUtils.getTextValueRequired(o, ProfileElement.PROFILE_NAME);
                    final String desc = ParserUtils.getTextValueRequired(o, ProfileElement.PROFILE_DESC);
                    aspects.add(new ProfileElement(name, desc));
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
