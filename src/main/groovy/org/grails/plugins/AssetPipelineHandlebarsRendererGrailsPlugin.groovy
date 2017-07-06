package org.grails.plugins

import asset.pipeline.AssetPipelineConfigHolder
import com.github.jknack.handlebars.io.TemplateLoader
import grails.plugins.Plugin
import groovy.util.logging.Slf4j

@Slf4j
class AssetPipelineHandlebarsRendererGrailsPlugin extends Plugin
{
    def grailsVersion   = "3.0.0 > *"
    def title           = "Grails asset pipeline handlebars"
    def author          = "Danny Casady"
    def authorEmail     = "dpcasady@gmail.com"
    def description     = "Grails asset pipeline handlebars templates renderer plugin"
    def documentation   = "https://github.com/dpcasady/asset-pipeline-handlebars-renderer"
    def scm             = [url: "https://github.com/dpcasady/asset-pipeline-handlebars-renderer"]
    def issueManagement = [system: "Github Issues", url: "https://github.com/dpcasady/asset-pipeline-handlebars-renderer"]

    def profiles  = ['web']
    def loadAfter = ['assetPipeline']

    Closure doWithSpring() {{->
        Map conf = AssetPipelineConfigHolder.config.handlebars
        String root = conf?.templateRoot ?: 'templates'
        TemplateLoader loader = new AssetPipelineTemplateLoader(root)

        handlebarsService(HandlebarsService) {
            templateLoader = loader
            grailsApplication = grailsApplication
        }
    }}

}
