package org.cxiondex.aspects.writers;



import java.io.IOException;

import org.cxio.aspects.writers.AbstractFragmentWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.util.JsonWriter;
import org.cxiondex.aspects.datamodels.CitationElement;


public class CitationFragmentWriter extends AbstractFragmentWriter {

    public CitationFragmentWriter() {
        
    }

    @Override
    public void writeElement(AspectElement element, JsonWriter w)
            throws IOException {
        
        w.writeObject(element);
        
    }

    @Override
    public String getAspectName() {
        return CitationElement.NAME;
    }

}