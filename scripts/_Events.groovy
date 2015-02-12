import asset.pipeline.AssetHelper
import asset.pipeline.AssetPipelineConfigHolder
import asset.pipeline.DirectiveProcessor

eventAssetPrecompileStart = { assetConfig ->
    AssetHelper.assetSpecs << Class.forName("asset.pipeline.es6to5.Es6File")
    DirectiveProcessor.DIRECTIVES.put('from', "requireFileDirective")
    AssetPipelineConfigHolder.registerResolver(
            Class.forName("asset.pipeline.es6to5.Es6AssetFileResolver")
                    .getConstructor([String.class, String.class] as Class[])
                    .newInstance('es6', 'grails-app/assets/javascripts'))
}