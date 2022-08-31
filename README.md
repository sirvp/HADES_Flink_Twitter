# Real-Time Hate Detection in Twitter Using Flink

A Flink application to read tweets in Real-time and detect instances of Hate Speech and Offensive Language in them.

Developed as part of final MSc Data Science Project in University of Glasgow

## Pre-requisites

Requires Flink, JDK-1.8, and Maven to run the application successfully.

The Classification Model requires pandas, scikit-learn, spacy and flask libraries.

## Usage

<a href = HADES_ClassificationModel.ipynb> HADES_ClassificationModel</a> file pre-trains the Hate Speech Detection Model with the data provided
in <a href = labeled_data.csv>labeled_data</a> file.

The Classification Model has both Logistic Regression and Support Vector Machine implementations.
This file also sets up a REST API Server using Flask to expose the model to the Flink program.


The repository can be downloaded and run in a Java IDE of your choice.  
The <a href = pom.xml>POM</a> file contains all the necessary dependencies for the project to run successfully. 

The Code can be built using 

```
mvn clean install
```

Main function to be run is written in the <a href = src/main/java/twitter_streaming/StreamingJob.java>StreamingJob</a> class.

#### Order of Execution
1. Run HADES_Classification Model ipynb - to start up the local server
2. Run Maven Clean Install code
3. Run StreamingJob class



## Credits
This code was developed with inspiration from various sources on the Internet. Major sources are credited below:

- <a href = https://github.com/haseeb1431/twitter-flink-project >haseeb1431/twitter-flink-project</a>  
- <a href = https://github.com/aedenj/flink-machine-learning-fish-market-example/tree/main/model>aedenj/flink-machine-learning-fish-market-example</a>  
- <a href = https://www.blog.duomly.com/python-api-tutorial/>Python API Tutorial</a>  
- <a href = https://techndeck.com/post-request-with-json-using-java-11-httpclient-api/>POST Request with JSON using Java 11 HttpClient API</a>  
- <a href = https://www.baeldung.com/apache-flink>Introduction to Apache Flink with Java</a>
