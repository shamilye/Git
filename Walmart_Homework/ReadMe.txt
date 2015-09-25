Homework assignment Global Products-Readme.txt

1. Automate an end-to-end user e-commerce transaction flow using any open source tool for www.walmart.com with an 
existing customer on Chrome or Safari browser.
(Please find the screen capture video of the execution of test automation)


How to run:

-Import the java project into IDE
-Add the selenium jars into build path of the project(these are included in the project).
-Also please make sure that chromedriver.exe is in the root of the project.
-Run TestingAutomation.java using any testing framework(JUnit).
-Chrome Browser pops up.
-Enter the username , password and search term from {tv, socks, dvd, toys, iPhone} in the IDE console

Execution:

-In the browser, the scenario in the problem statement is automated as follows:
 Login into the website->enter a search term in search textbox->Select an item from results->
 ->Add it to cart->View the cart->Close browser
-The Junit test is run.It fails if there is more than one item in the cart or if a different item is added to the cart.

Assumption:

-There are no items in the cart before running the test.I made this assumption as the test is needed to validate if there 
 is only one item in the cart after adding.

Technical choices:

-For "toys" search term, a different xPath is used to select the item from search results as the layout of items in the results
 page is different for this from those when {tv, socks, dvd,iPhone} are searched.
-Decision to get different elements(search box,select item link,add to cart button,view cart button,etc ) by different attributes 
 such as id,class,xPath is made based on uniqueness and also the performance.
-For comparing if the item selected is the item in the cart, rather than using the item Name, item Id is used considering it to be unique.
-TO make sure that there is only one item in the cart, the count distinct items and also the number of units of same item are validated.


Improvements:
-Explore ways to improve performance
-Reuse code to add more tests
-simplify the code

