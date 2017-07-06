package org.grails.plugins

import com.github.jknack.handlebars.io.StringTemplateSource
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification
/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class HandlebarsServiceSpec extends Specification {

    HandlebarsService service
    AssetPipelineTemplateLoader templateLoader = Mock()

    def setup() {
        service = new HandlebarsService(
            grailsApplication: grailsApplication,
            assetPipelineTemplateLoader: templateLoader)
        service.init()
    }

    void "inline templates are applied"() {
        given:
        String template = "{{name}}"

        when:
        String result = service.applyInline(template, [name: "John"])

        then:
        result == "John"
    }

    void "external templates are applied"() {
        given:
        String templateName = "my_template"
        String template = "{{name}}"
        templateLoader.sourceAt(templateName) >> new StringTemplateSource(templateName, template)

        when:
        String result = service.apply(templateName, [name: "John"])

        then:
        result == "John"
    }

    void "nested templates are applied"() {
        given:
        String childTemplateName = "child_template"
        String childTemplate = "{{name}}"
        templateLoader.sourceAt(childTemplateName) >> new StringTemplateSource(childTemplateName, childTemplate)

        and:
        String mainTemplateName = "my_template"
        String mainTemplate = "Hello {{>$childTemplateName}}"
        templateLoader.sourceAt(mainTemplateName) >> new StringTemplateSource(mainTemplateName, mainTemplate)

        when:
        String result = service.apply(mainTemplateName, [name: "John"])

        then:
        result == "Hello John"
    }
}