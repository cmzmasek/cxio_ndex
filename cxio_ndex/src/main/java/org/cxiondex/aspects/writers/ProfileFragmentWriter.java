package org.cxiondex.aspects.writers;

import java.io.IOException;

import org.cxio.aspects.writers.AbstractFragmentWriter;
import org.cxio.util.JsonWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxiondex.aspects.datamodels.ProfileElement;

public class ProfileFragmentWriter extends AbstractFragmentWriter {

    public static ProfileFragmentWriter createInstance() {
        return new ProfileFragmentWriter();
    }

    private ProfileFragmentWriter() {
    }

    @Override
    public void writeElement(final AspectElement element, final JsonWriter w) throws IOException {
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
