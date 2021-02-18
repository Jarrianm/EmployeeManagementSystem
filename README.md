# EmployeeManagementSystem:
This is a java project that connects to a MySQL structure to manage Companies, Departments and Employees.
Most of the functionality is driven by entering an id for the desired object, and running methods to update those table values.
An employee always has an address, department and company associated with it through id keys.
The company and department tables are joined through a joiner table to manage their relationship and data.
# Company functionality:
A company can be added to or deleted from the database, and all of them can be retrieved at once. A company can also display all of its departments and employees. 
In addition, a company can be updated in the following ways: 
1. Update its name
2. Transfer one of its departments to a new company
3. Remove a department from the company
4. Fire an employee
5. Move an employee to a new company
# Department functionality:
A department can be added to or deleted from the database, and all of them can be retrieved at once. A department can also display all of its employees. 
In addition, a department can be updated in the following ways:
1. Name
2. Phone
3. Change company
4. Trasnfer employee to a new department
5. Remove an employee from the department
# Employee functionality:
An employee can be added to or deleted from the database, and all of them can be retrieved at once.
In addition, an employee can be updated in the following ways:
1. First name
2. Last name
3. Gender
4. Date of birth
5. Salary
6. Address
7. Transfer department
8. Quit your company
9. Transfer to a new company
# Important information to run the project:
This project utilizes the .jar connection file to connect Java to MySQL. Make sure to add that file into your build-path to execute the code properly.
In addition, in the "config.properties" folder, make sure your server host, username and password are updated to reflect your local values. The ones listed in the project will not work for you as is.
Finally, make sure the MySQL server is on and running, and the ems-schema file is run and built first before executing any of the Java code.
