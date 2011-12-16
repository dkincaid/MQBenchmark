This program was a quick app written to benchmark Amazon SQS against RabbitMQ. You will need to go into MQBenchmarkRunner
 and enter your queuename, RabbitMQ uri and Amazon SQS uri into the constants at the top of the class. Also go into
 SQSMessageConsumer and SQSMessagePublisher and put your Amazon access and secret key into the constants at the top of
 the class.

You can use Maven to package it up into a single jar using `mvn assembly:single`. Then run it from the command line using
`time java -jar MQBenchmark-1.0-SNAPSHOT-jar-with-dependencies.jar sqs 10000` (or change sqs to rabbitmq).

