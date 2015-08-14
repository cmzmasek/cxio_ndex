package org.cxiondex.examples;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cxio.aspects.datamodels.CartesianLayoutElement;
import org.cxio.aspects.datamodels.EdgesElement;
import org.cxio.aspects.datamodels.NodesElement;
import org.cxio.aspects.readers.CartesianLayoutFragmentReader;
import org.cxio.aspects.readers.EdgesFragmentReader;
import org.cxio.aspects.readers.NodesFragmentReader;
import org.cxio.core.CxReader;
import org.cxio.core.CxWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;
import org.cxio.util.Util;
import org.cxiondex.aspects.datamodels.ContextElement;
import org.cxiondex.aspects.datamodels.ProfileElement;
import org.cxiondex.aspects.readers.ContextFragmentReader;
import org.cxiondex.aspects.readers.ProfileFragmentReader;
import org.cxiondex.aspects.writers.ContextFragmentWriter;
import org.cxiondex.aspects.writers.ProfileFragmentWriter;

public class Example1 {

    public static void main(final String[] args) throws IOException {

        // Creating same AspectElements and adding them to Lists (representing
        // AspectFragments)
        // --------------------------------------------------------------------

        final List<AspectElement> profile_elements = new ArrayList<AspectElement>();
        profile_elements.add(new ProfileElement("mouse net", "network of mouse interactions"));

        final List<AspectElement> context_elements = new ArrayList<AspectElement>();
        final ContextElement context = new ContextElement();
        context.put("key 1", "value 1");
        context.put("key 2", "value 2");
        context.put("key 3", "value 3");
        context.put("key 4", "value 4");
        context_elements.add(context);

        final List<AspectElement> edges_elements = new ArrayList<AspectElement>();
        edges_elements.add(new EdgesElement("edge0", "node0", "node1"));
        edges_elements.add(new EdgesElement("edge1", "node0", "node2"));

        final List<AspectElement> nodes_elements = new ArrayList<AspectElement>();
        nodes_elements.add(new NodesElement("node0"));
        nodes_elements.add(new NodesElement("node1"));
        nodes_elements.add(new NodesElement("node2"));

        final List<AspectElement> cartesian_elements = new ArrayList<AspectElement>();
        cartesian_elements.add(new CartesianLayoutElement("node0", 12, 21, 1));
        cartesian_elements.add(new CartesianLayoutElement("node1", 42, 23, 2));
        cartesian_elements.add(new CartesianLayoutElement("node2", 34, 23, 3));

        // Writing to CX
        // -------------
        final OutputStream out = new ByteArrayOutputStream();

        final String time_stamp = Util.getCurrentDate();

        final CxWriter w = CxWriter.createInstanceWithAllAvailableWriters(out, true, time_stamp);

        w.addAspectFragmentWriter(ProfileFragmentWriter.createInstance(time_stamp));
        w.addAspectFragmentWriter(ContextFragmentWriter.createInstance(time_stamp));

        w.start();
        w.writeAspectElements(profile_elements);
        w.writeAspectElements(context_elements);
        w.writeAspectElements(nodes_elements);
        w.writeAspectElements(edges_elements);
        w.writeAspectElements(cartesian_elements);
        w.end();

        final String cx_json_str = out.toString();
        System.out.println(cx_json_str);

        // Reading from CX
        // ---------------
        final Set<AspectFragmentReader> readers = new HashSet<>();

        final EdgesFragmentReader er = EdgesFragmentReader.createInstance();

        readers.add(er);
        readers.add(ProfileFragmentReader.createInstance());
        readers.add(ContextFragmentReader.createInstance());
        readers.add(NodesFragmentReader.createInstance());
        readers.add(CartesianLayoutFragmentReader.createInstance());

        final CxReader p = CxReader.createInstance(cx_json_str, readers);

        while (p.hasNext()) {
            final List<AspectElement> elements = p.getNext();
            if (!elements.isEmpty()) {
                final String aspect_name = elements.get(0).getAspectName();
                System.out.println();
                System.out.println(aspect_name + ": ");
                for (final AspectElement element : elements) {
                    System.out.println(element.toString());
                }
            }
        }
        System.out.println();
        System.out.println("edges time stamp: " + er.getTimeStamp());
    }

}
