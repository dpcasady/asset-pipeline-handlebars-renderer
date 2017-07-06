package org.grails.plugins

import com.github.jknack.handlebars.Context
import com.github.jknack.handlebars.Handlebars
import com.github.jknack.handlebars.Template
import com.github.jknack.handlebars.cache.HighConcurrencyTemplateCache
import com.github.jknack.handlebars.context.FieldValueResolver
import com.github.jknack.handlebars.context.JavaBeanValueResolver
import com.github.jknack.handlebars.context.MapValueResolver
import com.github.jknack.handlebars.io.TemplateLoader
import grails.core.GrailsApplication
import grails.util.GrailsUtil

import javax.annotation.PostConstruct

/**
 * Compile Handlebars templates resolved by a given TemplateLoader.
 * Compiled templates are cached.
 */
class HandlebarsService {

    GrailsApplication grailsApplication

    Handlebars handlebars
    TemplateLoader templateLoader

    @PostConstruct
    private void init() {
        Map<String, Object> cfg = grailsApplication.config.grails.assets.handlebars
        String classHelperSource = cfg?.classHelperSource
        String uriHelperSource = cfg?.uriHelperSource
        String fileHelperSource = cfg?.fileHelperSource

        handlebars = new Handlebars(templateLoader)

        def cacheTemplates = grailsApplication.config.handlebars.cache.templates
        if (!(cacheTemplates instanceof Boolean)) {
            cacheTemplates = !GrailsUtil.developmentEnv
        }
        if (cacheTemplates) {
            handlebars.with(new HighConcurrencyTemplateCache())
        }

        if (classHelperSource) {
            registerHelpers(this.class.classLoader.loadClass(classHelperSource))
        }
        if (uriHelperSource) {
            registerHelpers(URI.create(uriHelperSource))
        }
        if (fileHelperSource) {
            registerHelpers(new File(fileHelperSource))
        }
    }

    /**
     * Apply a template provided as a resource to a model (Map, Java bean etc.) and return the generated String.
     */
    String apply(String templateName, Object model) {
        Template template = handlebars.compile(templateName)
        return template.apply(createContext(model))
    }

    /**
     * Apply a template provided as a String to a model (Map, Java bean etc.) and return the generated String.
     */
    String applyInline(String inlineTemplate, Object model) {
        Template template = handlebars.compileInline(inlineTemplate)
        return template.apply(createContext(model))
    }

    /** Create a context from a Map or Java bean etc. */
    Context createContext(Object model) {
        return Context.newBuilder(model)
                .resolver(MapValueResolver.INSTANCE, JavaBeanValueResolver.INSTANCE, FieldValueResolver.INSTANCE)
                .build()
    }

    void registerHelpers(final URI location) throws Exception {
        handlebars.registerHelpers(location)
    }

    void registerHelpers(final Object helperSource) {
        handlebars.registerHelper(helperSource)
    }

    void registerHelpers(final Class<?> helperSource) {
        handlebars.registerHelper(helperSource)
    }

    void registerHelpers(final File input) throws Exception {
        handlebars.registerHelpers(input)
    }

    void registerHelpers(final String filename, final Reader source) throws Exception {
        handlebars.registerHelpers(filename, source)
    }

    void registerHelpers(final String filename, final InputStream source) throws Exception {
        handlebars.registerHelpers(filename, source)
    }

    void registerHelpers(final String filename, final String source) throws Exception {
        handlebars.registerHelpers(filename, source)
    }
}
