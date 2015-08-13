package org.cxiondex.aspects.writers;

import java.io.IOException;
import java.util.Map;

import org.cxio.aspects.writers.AbstractAspectFragmentWriter;
import org.cxio.util.JsonWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxiondex.aspects.datamodels.ContextElement;

public class ContextFragmentWriter extends AbstractAspectFragmentWriter {

    public static ContextFragmentWriter createInstance() {
        return new ContextFragmentWriter();
    }

    public static ContextFragmentWriter createInstance(final String time_stamp) {
        final ContextFragmentWriter w = new ContextFragmentWriter();
        w.setTimeStamp(time_stamp);
        return w;
    }

    private ContextFragmentWriter() {
    }

    @Override
    protected void writeElement(final AspectElement element, final JsonWriter w) throws IOException {
        final ContextElement e = (ContextElement) element;
        w.writeStartObject();
        for (final Map.Entry<String, String> entry : e.getContextKeyValues().entrySet()) {
            w.writeStringFieldIfNotEmpty(entry.getKey(), entry.getValue());
        }
        w.writeEndObject();

    }

    @Override
    public String getAspectName() {
        return ContextElement.NAME;
    }

}
