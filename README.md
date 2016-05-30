# Grails handlebars templates renderer plugin

This plugin is for Grails 3 and this is a fork of https://github.com/davidtinker/grails-handlebars


### Install

Add to build.gradle

    dependencies {
         compile "org.grails.plugins:handlebars-renderer:0.1.2"
    }

### Config

	grails.handlebars.templatesRoot = 'templates'
    grails.handlebars.templateExtension = '.hbs'
    grails.handlebars.templatesPathSeparator = '/'


### Usage

In controller:

	def data = [
    	name  : 'alex',
    ]
    
    //income.hbs must under path /src/main/webapps/templates/mailbox
    render handlebarsService.apply("mailbox/income", data)

    
In GSP template:    
    
    <handlebars:render template="mailbox/income" model="${data}"/>
    
or 
    
    <handlebars:render>
        Hello {{name}} from the controller
    </handlebars:render>
    
Please read complete documentation at https://github.com/davidtinker/grails-handlebars



 
 
