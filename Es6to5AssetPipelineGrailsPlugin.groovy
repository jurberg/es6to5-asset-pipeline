import asset.pipeline.AssetHelper
import asset.pipeline.AssetPipelineConfigHolder
import asset.pipeline.DirectiveProcessor
import asset.pipeline.es6to5.Es6AssetFileResolver
import asset.pipeline.es6to5.Es6File

class Es6to5AssetPipelineGrailsPlugin {

    def version = "0.1"
    def grailsVersion = "2.3 > *"
    def title = "6to5 Asset-Pipeline Plugin"
    def author = "John Urberg"
    def authorEmail = "jurberg@gmail.com"
    def description = "Provides Ecmascript 6 support for the asset-pipeline static asset management plugin."
//    def documentation = "http://grails.org/plugin/es6to5-asset-pipeline"
    def license = "MIT"
//    def organization = [ name: "My Company", url: "http://www.my-company.com/" ]
//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]
//    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]
//    def scm = [ url: "http://svn.codehaus.org/grails-plugins/" ]

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before
    }

    def doWithSpring = {
        // TODO Implement runtime spring config (optional)
    }

    def doWithDynamicMethods = { ctx ->
        AssetHelper.assetSpecs << Es6File
        DirectiveProcessor.DIRECTIVES.put('from', "requireFileDirective")
        AssetPipelineConfigHolder.registerResolver(new Es6AssetFileResolver('es6', 'grails-app/assets/javascripts'))
    }

    def doWithApplicationContext = { ctx ->
        // TODO Implement post initialization spring config (optional)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    def onShutdown = { event ->
        // TODO Implement code that is executed when the application shuts down (optional)
    }
}
