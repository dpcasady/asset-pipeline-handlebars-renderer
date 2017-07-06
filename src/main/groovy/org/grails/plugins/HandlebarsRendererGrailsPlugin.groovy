package org.grails.plugins

import grails.plugins.Plugin
import groovy.util.logging.Slf4j

@Slf4j
class HandlebarsRendererGrailsPlugin extends Plugin
{
    def grailsVersion   = "3.0.0 > *"
    def title           = "Grails handlebars"
    def author          = "Alexander Shamshurin"
    def authorEmail     = "shamshurin.alexander@gmail.com"
    def description     = "Grails handlebars templates renderer plugin"
    def documentation   = "https://github.com/salex772/handlebars-renderer"
    def scm             = [url: "https://github.com/salex772/handlebars-renderer"]
    def issueManagement = [system: "Github Issues", url: "https://github.com/salex772/handlebars-renderer"]

    def profiles = ['web']

}
