Clinical Data Extraction
========================
Software suite in JAVA to extract key information from medical discharge summaries

Dependencies
-------------
* BANNER.jar
* forms-1.3.0.jar
* jdom-2.0.5.jar
* lucene-core-4.10.3.jar
* opennlp-maxent-3.0.3.jar
* weka.jar
* commons-lang3-3.3.2.jar
* gtk-4.1.jar
* junit-4.11.jar
* lucene-snowball-3.0.2.jar
* opennlp-tools-1.5.3.jar
* config.xml
* guava-18.0.jar
* jwnl-1.3.3.jar
* lucene-suggest-4.10.3.jar
* opennlp-uima-1.5.3.jar
* dragontool.jar
* hamcrest-core-1.3.jar
* lucene-analyzers-common-4.10.3.jar
* mallet.jar

How to Run?
------------
1. System argument of Main should contain the path of config file. Sample config file you can find in resources
2. Simply run the Main class
3. It would output the data to the output file specified in the config

How to write Component?
------------------------
1. Component is basically anything which is doing the actual function. It can vary from tokenizer to de-identifier.
2. Use the framework to write the component
3. According to the component, simply implement the corresponding interface
4. If exception needs to be thrown, wrap your message in ComponentException