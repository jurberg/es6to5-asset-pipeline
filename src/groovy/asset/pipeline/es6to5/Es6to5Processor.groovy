package asset.pipeline.es6to5

import asset.pipeline.AbstractProcessor
import asset.pipeline.AssetCompiler
import asset.pipeline.AssetFile
import asset.pipeline.AssetPipelineConfigHolder

/**
 * Use 6to5 to convert EcmaScript6 to ES5
 */
class Es6to5Processor extends AbstractProcessor {

    def es6to5command
    def modulePrefix

    Es6to5Processor(AssetCompiler precompiler) {
        super(precompiler)
        es6to5command = AssetPipelineConfigHolder.config?.es6to5?.command ?: '/usr/local/bin/6to5'
        modulePrefix = AssetPipelineConfigHolder.config?.es6to5?.module.prefix ?: ''
        if (modulePrefix) {
            modulePrefix += '.'
        }
    }

    /**
     * Pass the code thru the 6to5 command with runtime separated.  Capture the return
     * ES5 JavaScript and do the following:
     * <ul>
     *  <li>create a 'namespace' on the global object for the file and append '__module__.exports' to it.
     *   For example, "app/controllers/test-controller.es6" becomes app.controllers.testcontroller.__module__.exports</li>
     *  <li>find all 'require("{file}")' calls and replace with the correct namespace
     *   For example, 'require("../services/test-service.es6")' becomes app.services.testservice.__module__.exports</li>
     *  <li>wrap code in an IFEE</li>
     * </ul>
     * @param s
     * @param assetFile
     * @return es5 javascript
     */
    @Override
    String process(String s, AssetFile assetFile) {

        String fullPath = new File(assetFile.sourceResolver.baseDirectory, assetFile.path).getAbsolutePath()
        String module = modulePrefix + assetFile.path.replaceAll(/.(es6|js)/, '').replace('/', '.').replace('-', '')

        Process p = "${es6to5command} --runtime ${fullPath}".execute()

        Scanner err = new Scanner(p.err).useDelimiter("\\A")
        String errorMessage = (err.hasNext() ? err.next() : "")
        if (errorMessage) {
            throw new RuntimeException(errorMessage)
        }

        String code = p.text.replaceAll(/require\(\"(.*)(\.(es6|js))?\"\)/, { convertFileToNamespace(it[1], assetFile) })

        '(function(module, exports) {\n' + code + '}(module = to5Plugin.createNamespace("' + module + '"), module.exports));'
    }

    def convertFileToNamespace(file, assetFile) {
        String path = file
        if (path.contains('..')) {
            String baseDir = assetFile.sourceResolver.baseDirectory.toString()
            path = new File(new File(baseDir, assetFile.path).parentFile, file).getCanonicalPath()
            path = path.substring(path.indexOf(baseDir) + baseDir.length() + 1)
        } else {
            if (path.indexOf('/') == 0) {
                path = path.substring(1)
            }
        }
        modulePrefix + path.replaceAll(/.(es6|js)/, '').replace('/', '.').replace('-', '') + '.__module__.exports'
    }

}
