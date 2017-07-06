package org.grails.plugins

import asset.pipeline.AssetFile
import asset.pipeline.AssetHelper
import com.github.jknack.handlebars.io.AbstractTemplateLoader
import com.github.jknack.handlebars.io.StringTemplateSource
import com.github.jknack.handlebars.io.TemplateSource

class AssetPipelineTemplateLoader extends AbstractTemplateLoader {

    AssetPipelineTemplateLoader(final String prefix) {
        setPrefix(prefix)
        setSuffix(null)
    }

    @Override
    TemplateSource sourceAt(final String uri) throws IOException {
        String location = resolve(normalize(uri))
        AssetFile file = AssetHelper.fileForUri(location, "application/javascript")
        if (file == null) {
            throw new FileNotFoundException(location)
        }
        return new StringTemplateSource(location, file.inputStream.getText("UTF-8"))
    }
}
