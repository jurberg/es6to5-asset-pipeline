package asset.pipeline.es6to5

import asset.pipeline.AssetFile
import asset.pipeline.fs.FileSystemAssetResolver

class Es6AssetFileResolver extends FileSystemAssetResolver {

    Es6AssetFileResolver(String name, String basePath) {
        super(name, basePath, false)
    }

    AssetFile getAsset(String relativePath, String contentType = null, String extension = null, AssetFile baseFile = null) {
        relativePath = relativePath.replaceAll("'", '').replaceAll('"', '')
        super.getAsset(relativePath, contentType, extension , baseFile)
    }

}
