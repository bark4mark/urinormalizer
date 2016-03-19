# A utility class to normalize a URI

The following problems are corrected
* Remove trailing slash
* Add HTTP if missing
* Replace spaces
* Encode special characters
* Remove default filename
* Remove ./ and ../
* Remove default port
* Make scheme and host lower case
* Preserve the case of the path
* (Optional) remove fragment
* Deal with relative scheme
