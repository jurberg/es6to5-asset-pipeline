import asset.pipeline.AssetHelper
import asset.pipeline.AssetPipelineConfigHolder
import asset.pipeline.DirectiveProcessor
import asset.pipeline.es6to5.Es6AssetFileResolver
import asset.pipeline.es6to5.Es6File

class Es6to5AssetPipelineGrailsPlugin {

    def version = "0.2"
    def grailsVersion = "2.3 > *"
    def title = "6to5 Asset-Pipeline Plugin"
    def author = "John Urberg"
    def authorEmail = "jurberg@gmail.com"
    def description = "Provides Ecmascript 6 support for the asset-pipeline static asset management plugin."
    def documentation = "https://github.com/jurberg/es6to5-asset-pipeline/blob/master/README.md"
    def license = "MIT"
    def issueManagement = [ system: "GITHUB", url: "https://github.com/jurberg/es6to5-asset-pipeline/issues" ]
    def scm = [ url: "https://github.com/jurberg/es6to5-asset-pipeline" ]

    def doWithDynamicMethods = { ctx ->
        AssetHelper.assetSpecs << Es6File
        DirectiveProcessor.DIRECTIVES.put('from', "requireFileDirective")
        AssetPipelineConfigHolder.registerResolver(new Es6AssetFileResolver('es6', 'grails-app/assets/javascripts'))
    }

}
