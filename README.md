# Environment Requirements
1. JDK 1.8+
2. Gradle
3. IDE (optional with Intellij)


# How to Run
1. Clone the project into local
git clone https://AllenBo1189@bitbucket.org/AllenBo1189/codechallenge.git 
2. Import the project into IDE
3. Build and Test
~~~
gradle clean build test
~~~

4. Run with file as input
~~~
gradle clean build test runWithExec 
~~~

5. Run with console as input
~~~
Please run the file ApplicationMain
~~~

# Design and Test
## Interface oriented designs
The program is interface-oriented designed. For example, Employ entity is interface-oriented designed, the specific objects are wired from the main application.

## Logs
Used log annotation to add logs throughout the program.

## TDD methodology
The development followed TDD methodology. 

## Decoupling
The design followed the rule "depends on the interface, rather than the specific class". The classes are well decoupled.

## Functional Programming
The program used functional programming. 

## Easy Maintain
The design used annotations, try-catch system to make the code clean, focus on business, and easy to maintain.

# Sample Outputs 
## With Correct Input
~~~
07:46:03.891 [main] INFO com.codechallenge.calculate.CalculatorImpl - Start to calculate total tax for gross income 80000
07:46:03.891 [main] INFO com.codechallenge.calculate.CalculatorImpl - Segment the income into different tax ranges for gross income 80000
07:46:03.891 [main] INFO com.codechallenge.calculate.CalculatorImpl - Get the monthly income for gross income 80000
07:46:03.891 [main] INFO com.codechallenge.calculate.CalculatorImpl - Net income is 70000.0
Monthly Payslip for: "Mary Song"
Gross Monthly Income: $6666.67
Monthly Income Tax: $833.33
Net Monthly Income: $5833.33
~~~

## With Incorrect Input
~~~
07:57:54.759 [main] ERROR com.codechallenge.input.AnalyzeInputImpl - Input GenerateMonthlyPayslip "Mary Song"  -10 is wrongly formatted
07:57:54.759 [main] ERROR com.codechallenge.ApplicationMain - Invalid input
~~~

