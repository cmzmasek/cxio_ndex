package org.cxiondex.aspects.writers;

import java.io.IOException;

import org.cxio.aspects.writers.AbstractAspectFragmentWriter;
import org.cxio.core.JsonWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxiondex.aspects.datamodels.ProfileElement;

public class ProfileFragmentWriter extends AbstractAspectFragmentWriter {

    public static ProfileFragmentWriter createInstance() {
        return new ProfileFragmentWriter();
    }

    public static ProfileFragmentWriter createInstance(final String time_stamp) {
        final ProfileFragmentWriter w = new ProfileFragmentWriter();
        w.setTimeStamp(time_stamp);
        return w;
    }

    private ProfileFragmentWriter() {
    }

    @Override
    protected void writeElement(final AspectElement element, final JsonWriter w) throws IOException {
        final ProfileElement e = (ProfileElement) element;
        w.writeStartObject();
        w.writeStringFieldIfNotEmpty(ProfileElement.PROFILE_NAME, e.getName());
        w.writeStringFieldIfNotEmpty(ProfileElement.PROFILE_DESC, e.getDescription());
        w.writeEndObject();
    }

    @Override
    public String getAspectName() {
        return ProfileElement.NAME;
    }

}
