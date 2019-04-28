there are 3 packages in the project.
package[mybatisMappers] contains an interface and a mapper xml file which define the sql operation.
Mind that the table name which you are about to insert in, it should be adjusted.

package [Spring] contains the spring context for  registering bean and a properties file about database connection.
mind that the the properties file should be override so as to connect your own database for test.

the structure of the tables required in the test is quite simple with only one column(text).
here the table I used called t1 and t2, you can see it in the mapper config file {first.xml}. 

in the package [test] , the class {creatingString} is trivial and it's for generating the content we insert.
the efficienciesComparison class is the most important to test for the efficiencies differences of two ways of batch operation.
more description can be seen in the class file.
