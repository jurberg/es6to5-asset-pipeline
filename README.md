# es6to5-asset-pipeline
Grails asset pipeline that processes EcmaScript 6 file using 6to5

# Instructions
- Place your files in assets/javascripts with file extension of .es6
- Install es6to5
- Require namespace.js & runtime.js
- Require es6to5runtime.js or generate your own
- Optionally set grails.assets.es6to5.command = 6to5 command if not /usr/local/bin/6to5
- Optionally set grails.assets.namespace.prefix
- Requires grails-asset-pipeline 2.1.0 and Grails 2.3+

# Features
- Uses [6to5](http://6to5.org) to convert your EcmaScript 6 code to ES5
- Reads the import statements to determine what to require; no extra //= require is needed
