# Fetch Tweets using Workaround API and Analyze for Sentiment
A project written , it bypass some limitations of Twitter Official API including allowing me to get over 2 million 
tweets in 3 days and analyzing all of them, thanks to about 64 cores.

## Details
Twitter Official API has the bother limitation of time constraints, you can't get older tweets than a week. Some tools
provide access to older tweets but in the most of them you have to spend some money before. I was searching other 
tools to do this job but I didn't find it, so I forked @Jefferson-Henrique's and added sentiment analysis. 

Basically when you enter on Twitter page a scroll loader starts, if you scroll down you start to get more and more 
tweets, all through calls to a JSON provider. After mimic we get the best advantage of Twitter Search on browsers, it 
can search the deepest oldest tweets.

This program then analyzes the tweets for sentiment by first running them through a sanitizer, then the Stanford 
CoreNLP parser (please make sure the English components are installed at 
`edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz`). Then SentiWordNet 3.0.0 is used to assign sentiment to all 
word in the tweet.
