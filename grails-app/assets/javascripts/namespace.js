(function (global) {

    global.to5Plugin = global.to5Plugin || {};

    global.to5Plugin.createNamespace = function(namespaceString) {
        var parts = namespaceString.split('.'),
            parent = global,
            currentPart = '';

        for(var i = 0, length = parts.length; i < length; i++) {
            currentPart = parts[i];
            parent[currentPart] = parent[currentPart] || {};
            parent = parent[currentPart];
        }

        parent.__module__ = {exports: {}};

        return parent.__module__;
    };

})(typeof global === "undefined" ? self : global);