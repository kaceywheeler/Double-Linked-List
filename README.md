****************************
* Double-Linked List Project 
* CS 221
* 05/09/21
* Kacey Wheeler
****************************

OVERVIEW:
  
  Manages data through a doubly linked list data structure where the list is made up
  Nodes with both a previous and next reference. The list can be manipulated to do things 
  such as remove elements, add elements, or set an element to a different element using this 
  type of list. 

INCLUDED FILES:

  * IUDoubleLinkedList.java -  source file
  * IndexedUnsortedList.java - source file 
  * Node.java - source file 
  * ListTester.java - tester file 
  * README - this file 
  
COMPILING AND RUNNING: 
  
  Ensure the the correct list is being tested by checking that doubleLinkedList
  is the ListToUse in line 23 of ListTester.java 
  
  From the directory containing all source files, compile the driver class 
  (and all dependencies) with the command:
  $javac ListTester.java
  
  Run the compiled class file with the command:
  $java ListTester
  
  Console output will give the results after the program finishes 
  
PROGRAM DESIGN AND IMPORTANT CONCEPTS:

  The main class of this program is the IUDoubleLinkedList.java which implements
  IndexedUnsortedList.java and also uses the Node class to maintain references 
  to the next Node in the list as well as the previous Node. 
 
  The doubly linked list implementation allows for elements to be added to the end of the 
  list more efficiently than even the singly list provides. It also is much easier to add and 
  remove for many different circumstances than the array based implementation provides. 
  Both the singly linked list and doubly linked list elements can be added and removed without
  having to worry about expanding the capacity of the array or shifting elements although they 
  do require more overhead. It also does take longer to access elements in the middle of the list
  than the array based implementation in which elements are indexed, allowing for quick and 
  easy access. Therefore, depending on what needs to be done with the data in the list, 
  the doubly linked list implementation would work well. 

  IUDoubleLinkedList.java has methods to remove the first element from the list, add after a 
  desired element, add after a certain index, remove an element at a specific index, and more. 
  It also includes the functionality of a listIterator which allows for elements to be stepped over
  in both directions through the next and previous method. It also contains functionality for 
  removing the last accesssed element, adding after the last accessed element, and setting 
  the last accessed element to a new element. 

  ListTester.java tests the methods in IUDoubleLinkedList.java. 

TESTING: 

  Extensive testing was carried out to ensure proper functionality of the doubly linked list. 
  The ListTester.java class was created to test many different test cases to account for many 
  different situations which could occur. This tester file tests different situations such as when 
  the list is empty, when it has a single element, when the second element is removed and then
  and then set is called, etc. Testing for listIterator functionality added to the double linked list 
  was also added to the tester file to test the listIterator methods. While a great majority of the 
  tests were passing, a couple were still failing. Of 1305 tests run, 1297 were passing and 8
  were failing. The tests which were failing were relating to the previous method of the listIterator,
  so this method is in need of further debugging. 

DISCUSSION:

  Problems which were run into during program development involved NullPointerExceptions which
  arose from not adequetly accounting for all the different corner cases in each method. 
  There were different circumstances in which getNext and getPrev were being called on null, 
  throwing this exception. Therefore, different cases were handled to account for these issues. 
  For example, There was a situation where nextNode.getNext() was being called even when 
  the nextNode was null so an if statement was added to ensure this code was only ran when 
  nextNode did not equal null. 

  Understanding the abstract concept of a doubly linked list was also incredibly challenging. 
  It became incredibly confusing to keep track of what each Node was pointing to and to ensure
  they were all pointing to the right previous Node and next Node, even after doing complex 
  operations such as adding and removing. I began drawing out the different Nodes and sketching 
  out exactly what was happening during these complex operations to the different pointers of the 
  Node which helped considerably. I was having a really difficult time until I was able to think about 
  the concept of a doubly linked list in a more concrete manner. 

  Overall, being able to see how the array based implementation and the linked list implementations 
  of the same methods functioned differently from eachother was extremely helpful. It was
  interesting to see how the different implementations had very different strong-suits and
  weaknesses, and I can see how understanding these differences would be particulary valuable
  in the future. 
