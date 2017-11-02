# Auto Slides project #
Automated generation of presentations based on topics.

## Installation ##
A working Maven installation is needed.

1. Clone the repository.
2. Copy dependencies using the pom file. (`mvn dependency:copy-dependencies package`)
3. Use the generated .jar to run the program. (`java –jar Auto_Slides-final.jar`)

## Read from File ##
1. Use files as described in "ReadFromFileExample.txt".
2. Add the text file's path to the end of the command line prompt. (e.g. `java –jar Auto_Slides-final.jar C:\Directory\data.txt`)

## Presentation Sources ##
* DBPedia (wiki.dbpedia.org)
* SimplePedia (simple.wikipedia.org)
* WikiPedia (en.wikipedia.org)
* WikiMedia (commons.wikimedia.org)
* Flickr (flickr.com)
* OpenStreetMap (openstreetmap.org)

## Original contributors ##
This project has been manually migrated to a new Git repository at Github for several reasons. All authors approved to migrate all files in this repository to Github as of a new repository. In order to pay proper attribution, we list all original authors here.

* Lucas Lange
* Christian Staudte
* Johannes Bräuer
* Andrea Niebuhr
* Francesco Mandry
* Han Tek Foo
* Kilian Schwabe