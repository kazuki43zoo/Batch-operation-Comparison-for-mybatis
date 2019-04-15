there are 3 packages in the project.

package [Spring] contains the spring context for  registering bean and a properties file about database connection.
mind that the the properties file should be override so as to connect your database for test.

the structure of the tables required in the test is quite simple with only one column(text).

in the test package, the class {creatingString} is trivial and it's for generating the content we insert.
the efficienciesComparison class is the most important to test for the efficiencies differences of two ways of batch operation.
more description can be seen in the class file.
