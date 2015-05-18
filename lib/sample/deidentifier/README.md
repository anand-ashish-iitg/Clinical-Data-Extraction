DE-IDENTIFIER
=============
The implementation of de-identifier is inspired from ......

Currently the system supports for de-identification for a list of words only. The corpus needs to be tokenized either by using a tokenizer implemented in the suite or a newly developed one. The tokenizer ouput inside the system looks like <block>TOKEN</block>. The block tags are added automatically by the suite and developer doesn't need to add it manually. To provide direct support for large corpus one must complete the implemenation of the interface in *DeIdentifier.java* in a similar fashion to how it has been done for List of Strings.

```java
@Override
public List<String> DeIdentify(String data) throws ComponentException
{
    throw new ComponentException("Not Implemented");
}
```


Training/Testing
----------------

Training of data has been kept as side process since it is a time taking process. To train the system on a new corupus one must uncomment the following line and execute the main function in the *DeIdentifier.java*
```java
//        deIdentifier.writeClassifier("de-id-j48.bin", "train.xml");
``` 
Here, *de-id-j48.bin* is the name of file where model will be written, and the *train.xml* is the file corrosponding to training medical corpus.

**NOTE** : The training/testing file must have annotation in the exact format as it has been provided in the i2b2 challenge, 2006. Any additional tags can lead to execution errors. However, if one needs to work with a different file format, a parser for the same must be written in order to convert the file into ARFF format. Current implementation of parser is done in *ArffBuilder.java*

__Parameters__

Most of the paramters the can be modified are in *Constants.java*. The list of headers, trigger words, trigger bigrams can be changed as per users wish. The windows size is another tunable parameter specified in the constructor of the DeIdentifier. Currently, a windows size of 3  has been choosen.
```java
arffbuilder = new ArffBuilder(3, triggerWords, bigrams, headers);
```
The list of trigger words/bigrams are currenly choosen by analyzing the frequency of words/bigrams. To aid this analysis **TriggerGramStats.java**,**TriggerWordStats.java** provide implementation to count frequency at both instance and token level. Please take a note of the following code segement in case you need to adjust the window size when performing the frequency anaylsis.
```java
public static void main(String[] args) throws Exception {
		TriggerGramStats stats = new TriggerGramStats(TRAINING_FILE, WINDOW_SIZE);
		stats.generateStats();
		stats.cleanTokenStats();
		stats.printTokenStats();
}
```
```java
for (int j = 0; j < backwords.size() - THE_WINDOW_SIZE; j++) {
		backWord = "";
		for (int k = 0; k < THE_WINDOW_SIZE; k++) {
			backWord = backWord + " "
					+ backwords.get(j + k).getWord();
		}
		backWord = backWord.trim();
		....
}
```
These implementations can be run by executing the main function implemented in each of these files.


**NOTE** : Any change in these parameters involve re-training the model as the sytem will not work with the old model.
