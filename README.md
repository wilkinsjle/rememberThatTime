# RememberThatTime


Such interactions can be happening in different contexts and involve an unbounded number of types of events and actions, each with their own set of special parameters. 

In doing so, we have designed a computational architecture, and developed a framwork based on that, to fristly convert such raw logs to an standardized notation (AIL), find interesting spans of events inside them, and convert those interesting event sequences to stories using an NLG (Natural Language Generation) engine. 

This project contains the code for this framework in Java and should contain sample log files. In the main package of "rememberthattime", you will find two main Java classes of "TellTheStory" and "TellTheFunParts"; The former creates full stories from an interaction, and the latter tries to generate interesting stories based on extraordinary spans of events.

Original author: Morteza Behrooz (morteza@ucsc.com)

## The Architecture:
Code for this is under package: `edu.ucsc.soe.ccs.rememberthattime`

#### Part 1 - AIL Generation

#### Part 2 - Deviation Finder

#### Part 3 - NLG Output

## Run:

Two classes with main methods:
`TellTheFunParts` and `TellTheStory`. 

