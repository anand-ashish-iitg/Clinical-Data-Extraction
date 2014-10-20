Clinical Data Extraction
========================
Software suite in JAVA to extract key information from medical discharge summaries

Dependencies
-------------
* guava-18.0
* jdom-2.0.5
* junit-4.10

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