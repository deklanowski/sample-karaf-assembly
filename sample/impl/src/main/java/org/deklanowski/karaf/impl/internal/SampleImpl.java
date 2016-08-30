package org.deklanowski.karaf.impl.internal;

import org.deklanowski.karaf.api.Sample;

/**
 * Hello world!
 *
 */
public class SampleImpl implements Sample
{
    public void sampleMethod(String s) {
        System.out.printf("Sample impl: "+s);
    }
}
