package com.kpi.magazines.utils.velocity;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.StringWriter;

/**
 * Created by Konstantin Minkov on 31.07.2016.
 *
 * Class for working with Apache Velocity.
 */
public class VelocityService {

    private final static VelocityEngine velocity;

    static {
        velocity = new VelocityEngine();
        velocity.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocity.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocity.init();
    }

    /**
     * Renders a page with params.
     * @param templateFile - name of the .vm template page from resources folder.
     * @param context - params, that should be inserted in template page.
     * @return final page as String.
     */
    public static String render(String templateFile, VelocityContext context) {
        final Template template = velocity.getTemplate(templateFile);
        final StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer.toString();
    }
}
