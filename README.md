# es6to5-asset-pipeline
Grails asset pipeline that processes EcmaScript 6 file using Babel

# Features
- Uses [Babel](https://babeljs.io) to convert your EcmaScript 6 code to ES5
- Reads the import statements to determine what to require; no extra //= require is needed!
- Requires grails-asset-pipeline 2.1.1 and Grails 2.3.11

# Instructions
- Install babel and babel-runtime globally
```
npm install -g babel
```
- Grab any other transformers required for your code.  For example, if you wish to use generators, you'll need the
runtime.js file that contains the generator library code.  See the [babel docs](https://babeljs.io/docs/usage/transformers/)
for more details
- Require to5Plugin_namespace.js (included with the plugin) and the runtimes you generated in your app
```javascript
//application.js
//= require to5Plugin_namespace.js
//= require runtime.js
```
- Place your files in assets/javascripts with file extension of .es6
- Configure using settings if desired (add to grails.assets)

| Setting          | Description                                  | Default |
| -------          | -----------                                  | ------- |
| es6to5.command   | the full path to the 6to5 executable         | /usr/local/bin/babel |
| namespace.prefix | the prefix to add to your module's namespace | |

# Under the Covers
The asset pipeline looks for any files in the assets/javascripts folder with the extension of .es6 and processes them. 
It will use the import statement in the file to determine what files you depend upon, so you don't need to add
a separate asset-pipepline directive to include dependencies.  It will pass the contents of your file thru the
6to5 processor with the --runtime flag set.  It will then wrap your code in an IFEE and generate a global 'namespace' 
that your module will be attached to.  The namespace will be the directory structure of your file with '.\_\_module\_\_.exports' 
attached.  The babel compiler will replace any import statements with a 'require("<module>")' statement.  The 
plugin will replace that with the namespace for the module.
 
For example, take a file grails-app/assets/javascripts/app/controller/HomeCtrl.es6 with the following contents:
```javascript
import {app as App} from '/app/app';
 
const HOME_PATH = '/';
 
class HomeCtrl {
    constructor() {
        this.path = HOME_PATH;
    }
}
 
App.controller('HomeCtrl', HomeCtrl);
  
export default HomeCtrl;
```
The following module will be created on the global context (i.e. the window): app.controller.\_\_module\_\_.exports.  After 
processing, the following HomeCtrl.js file will be produced:
```javascript
(function(module, exports) {
"use strict";
 
var App = app.app.__module__.exports.app;
 
var HOME_PATH = "/";
 
var HomeCtrl = function HomeCtrl() {
    to5Runtime.classCallCheck(this, HomeCtrl);

    this.path = HOME_PATH;
};
 
App.controller("HomeCtrl", HomeCtrl);
  
module.exports = HomeCtrl;
 
}(module = to5Plugin.createNamespace("app.controllers.HomeCtrl"), module.exports));
```
