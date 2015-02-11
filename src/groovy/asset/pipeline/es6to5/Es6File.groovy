package asset.pipeline.es6to5

import asset.pipeline.AbstractAssetFile

import java.util.regex.Pattern

class Es6File extends AbstractAssetFile {
    static final String contentType = 'application/javascript'
    static extensions = ['es6']
    static final String compiledExtension = 'js'
    static processors = [Es6to5Processor]
    Pattern directivePattern = ~/import.*(from.*'.*').*/
}