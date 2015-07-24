# RegexQuiz

I started this project to practice my Java skills, while I am studying for the OCP certification. Regex is one of the subjects on the exam, and to get well trained in Regex a lot of practice is important. So I decided do build an app that quizzes me on regex patterns, and to get some experience with other Java components on the way.

## Config files
The app reads config files from the ./config directory. (This is fixed now, planning to move this to a better location later).

An example of a config file is in this folder. The first line is not really important at this time (it should be there to verify and support multiple question types later on). The following lines are TEXT;REGEX lines. 

Only questions that require you to give the matched starting positions are supported at this time.

## Run the app
From the main directory:
`java -cp target/classes nl.rudidevries.regexquiz.RegexQuizApp`

# Todo

* Add a Match output question type
* Maven configuration
* Unit testing
* ...

## Disclaimer
I will probably never finish/expand this project. It was all good fun. Earned my OCP btw ;-)
