# Asset Pipeline Handlebars Renderer Grails Plugin

This plugin provides for server side execution of [Handlebars.js](http://handlebarsjs.com/) templates. It is
intended to compliment the [handlebars-asset-pipeline](https://github.com/bertramdev/asset-pipeline) plugin
which compiles Handlebars templates into JavaScript for client side usage. The templates are compiled into Java
code using [handlebars-java](https://github.com/jknack/handlebars.java).

## Installation

Add to build.gradle

```
dependencies {
     compile "org.grails.plugins:asset-pipeline-handlebars-renderer:0.1"
}
```

## Configuration

This plugin uses the same configuration as the [handlebars-asset-pipeline](https://github.com/bertramdev/asset-pipeline) plugin. Templates can be created in the standard assets/javascripts folder with an extension of .handlebars or .hbs. By default the `templateRoot` for your templates is specified as `templates`. This means that any handlebars file within  assets/javascripts/templates/ will utilize its file name (without the extension) as its template name. So a template that lives at `assets/javascripts/templates/my_template.hbs` can be rendered as `<handlebars:render template="my_template"/>`.

## Usage

A `handlebars:render` tag is provided to render handlebars templates similarly to how regular gsp templates are applied in views. Templates can be rendered inline:

```html
<handlebars:render model="[name: 'bob']">
    <p>Hello {{name}}</p>
</handlebars:render>
```

Or they can be stored in a separate file and referenced by name:

```html
<handlebars:render template="home/hello" model="[name: 'bob']"/>
```

For the above example, (assuming the default `templateRoot`) the template would be located at `grails-app/assets/javascripts/templates/home/hello.hbs`.

If no model is supplied then the default page bindings (page scope variables) are used:

```html
<handlebars:render>
    Hello {{name}} from the controller
</handlebars:render>
```

Behind the scenes, the taglib uses the `handlebarsService` to render templates. You can do the same if, for example, you want to render the template directly from a controller:

```groovy
def handlebarsService
…
def list() {
    render handlebarsService.apply("home/accounts", [accounts: Account.list()])
}
```
