package com.example.myapplication.repository

import android.util.Log
import android.widget.ImageView
import com.example.myapplication.R
import com.example.myapplication.databaseConnection.EmpDetailManipulate
import com.example.myapplication2.EmployeeEntity
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class EmployeeRepo(var empDetailManipulate: EmpDetailManipulate) {

    val employees = listOf(
        EmployeeEntity(1, "John Doe", "Address 1", "john@example.com", 1234567890, 5, 50000, "CRM", "Trainee", "EMP001", "01-01-1990", "Male"),
        EmployeeEntity(2, "Jane Smith", "Address 2", "jane@example.com", 9876543210, 3, 60000, "Jambav", "Employee", "EMP002", "15-03-1992", "Female"),
        EmployeeEntity(3, "Michael Johnson", "Address 3", "michael@example.com", 8765432109, 7, 70000, "Trainer Central", "Team Leader", "EMP003", "20-07-1988", "Male"),
        EmployeeEntity(4, "Emily Brown", "Address 4", "emily@example.com", 7890123456, 4, 80000, "Site 24*7", "Manager", "EMP004", "10-12-1995", "Female"),
        EmployeeEntity(5, "David Wilson", "Address 5", "david@example.com", 8901234567, 6, 90000, "People", "Admin", "EMP005", "05-05-1985", "Male"),
        EmployeeEntity(6, "Emma Davis", "Address 6", "emma@example.com", 9012345678, 2, 70000, "Creater App", "Super Admin", "EMP006", "30-09-1998", "Female"),
        EmployeeEntity(7, "James Anderson", "Address 7", "james@example.com", 2345678901, 5, 55000, "CRM", "Employee", "EMP007", "14-02-1991", "Male"),
        EmployeeEntity(8, "Olivia Martinez", "Address 8", "olivia@example.com", 3456789012, 3, 65000, "Jambav", "Trainee", "EMP008", "25-04-1993", "Female"),
        EmployeeEntity(9, "William Jackson", "Address 9", "william@example.com", 4567890123, 7, 75000, "Trainer Central", "Admin", "EMP009", "08-08-1987", "Male"),
        EmployeeEntity(10, "Sophia Taylor", "Address 10", "sophia@example.com", 5678901234, 4, 85000, "Site 24*7", "Super Admin", "EMP010", "20-11-1994", "Female"),
        EmployeeEntity(11, "Alexander White", "Address 11", "alex@example.com", 6789012345, 6, 95000, "People", "Trainee", "EMP011", "15-06-1986", "Male"),
        EmployeeEntity(12, "Isabella Harris", "Address 12", "isabella@example.com", 7890123456, 2, 80000, "Creater App", "Employee", "EMP012", "30-10-1997", "Female"),
        EmployeeEntity(13, "Daniel Thomas", "Address 13", "daniel@example.com", 8901234567, 5, 60000, "CRM", "Team Leader", "EMP013", "05-03-1990", "Male"),
        EmployeeEntity(14, "Mia Clark", "Address 14", "mia@example.com", 9012345678, 3, 70000, "Jambav", "Manager", "EMP014", "18-05-1992", "Female"),
        EmployeeEntity(15, "Liam Walker", "Address 15", "liam@example.com", 1234567890, 5, 50000, "Trainer Central", "Admin", "EMP015", "01-01-1990", "Male"),
        EmployeeEntity(16, "Sophia Green", "Address 16", "sophia@example.com", 9876543210, 3, 60000, "Site 24*7", "Super Admin", "EMP016", "15-03-1992", "Female"),
        EmployeeEntity(17, "Jacob Martinez", "Address 17", "jacob@example.com", 8765432109, 7, 70000, "People", "Trainee", "EMP017", "20-07-1988", "Male"),
        EmployeeEntity(18, "Emma Wilson", "Address 18", "emma@example.com", 7890123456, 4, 80000, "Creater App", "Employee", "EMP018", "10-12-1995", "Female"),
        EmployeeEntity(19, "Noah Davis", "Address 19", "noah@example.com", 8901234567, 6, 90000, "CRM", "Team Leader", "EMP019", "05-05-1985", "Male"),
        EmployeeEntity(20, "Olivia Harris", "Address 20", "olivia@example.com", 9012345678, 2, 70000, "Jambav", "Manager", "EMP020", "30-09-1998", "Female"),
        EmployeeEntity(21, "William Moore", "Address 21", "william@example.com", 2345678901, 5, 55000, "Trainer Central", "Admin", "EMP021", "14-02-1991", "Male"),
        EmployeeEntity(22, "Ava Taylor", "Address 22", "ava@example.com", 3456789012, 3, 65000, "Site 24*7", "Super Admin", "EMP022", "25-04-1993", "Female"),
        EmployeeEntity(23, "James Wilson", "Address 23", "james@example.com", 4567890123, 7, 75000, "People", "Trainee", "EMP023", "08-08-1987", "Male"),
        EmployeeEntity(24, "Charlotte Lee", "Address 24", "charlotte@example.com", 5678901234, 4, 85000, "Creater App", "Manager", "EMP024", "20-11-1994", "Female"),
        EmployeeEntity(25, "Logan Harris", "Address 25", "logan@example.com", 6789012345, 6, 95000, "CRM", "Employee", "EMP025", "15-06-1986", "Male"),
        EmployeeEntity(26, "Amelia Taylor", "Address 26", "amelia@example.com", 7890123456, 2, 80000, "Jambav", "Team Leader", "EMP026", "30-10-1997", "Female"),
        EmployeeEntity(27, "Benjamin Clark", "Address 27", "benjamin@example.com", 8901234567, 5, 60000, "Trainer Central", "Admin", "EMP027", "05-03-1990", "Male"),
        EmployeeEntity(28, "Mia Anderson", "Address 28", "mia@example.com", 9012345678, 3, 70000, "Site 24*7", "Super Admin", "EMP028", "18-05-1992", "Female"),
        EmployeeEntity(29, "Ethan Martin", "Address 29", "ethan@example.com", 1234567890, 5, 50000, "People", "Trainee", "EMP029", "01-01-1990", "Male"),
        EmployeeEntity(30, "Sophia Jackson", "Address 30", "sophia@example.com", 9876543210, 3, 60000, "Creater App", "Employee", "EMP030", "15-03-1992", "Female"),
        EmployeeEntity(31, "Jackson Brown", "Address 31", "jackson@example.com", 8765432109, 7, 70000, "CRM", "Team Leader", "EMP031", "20-07-1988", "Male"),
        EmployeeEntity(32, "Emily Wilson", "Address 32", "emily@example.com", 7890123456, 4, 80000, "Jambav", "Manager", "EMP032", "10-12-1995", "Female"),
        EmployeeEntity(33, "Michael Thomas", "Address 33", "michael@example.com", 8901234567, 6, 90000, "Trainer Central", "Admin", "EMP033", "05-05-1985", "Male"),
        EmployeeEntity(34, "Olivia Garcia", "Address 34", "olivia@example.com", 9012345678, 2, 70000, "Site 24*7", "Super Admin", "EMP034", "30-09-1998", "Female"),
        EmployeeEntity(35, "William Wilson", "Address 35", "william@example.com", 2345678901, 5, 55000, "People", "Trainee", "EMP035", "14-02-1991", "Male"),
        EmployeeEntity(36, "Emma Taylor", "Address 36", "emma@example.com", 3456789012, 3, 65000, "Creater App", "Employee", "EMP036", "25-04-1993", "Female"),
        EmployeeEntity(37, "James Moore", "Address 37", "james@example.com", 4567890123, 7, 75000, "CRM", "Team Leader", "EMP037", "08-08-1987", "Male"),
        EmployeeEntity(38, "Charlotte White", "Address 38", "charlotte@example.com", 5678901234, 4, 85000, "Jambav", "Manager", "EMP038", "20-11-1994", "Female"),
        EmployeeEntity(39, "Noah Harris", "Address 39", "noah@example.com", 6789012345, 6, 95000, "Trainer Central", "Admin", "EMP039", "15-06-1986", "Male"),
        EmployeeEntity(40, "Ava Clark", "Address 40", "ava@example.com", 7890123456, 2, 80000, "Site 24*7", "Super Admin", "EMP040", "30-10-1997", "Female"),
        EmployeeEntity(41, "William Davis", "Address 41", "william@example.com", 8901234567, 5, 60000, "People", "Trainee", "EMP041", "05-03-1990", "Male"),
        EmployeeEntity(42, "Sophia Hernandez", "Address 42", "sophia@example.com", 9012345678, 3, 70000, "Creater App", "Employee", "EMP042", "18-05-1992", "Female"),
        EmployeeEntity(43, "Michael Brown", "Address 43", "michael@example.com", 1234567890, 5, 50000, "CRM", "Team Leader", "EMP043", "01-01-1990", "Male"),
        EmployeeEntity(44, "Emma Garcia", "Address 44", "emma@example.com", 9876543210, 3, 60000, "Jambav", "Manager", "EMP044", "15-03-1992", "Female"),
        EmployeeEntity(45, "James Wilson", "Address 45", "james@example.com", 8765432109, 7, 70000, "Trainer Central", "Admin", "EMP045", "20-07-1988", "Male"),
        EmployeeEntity(46, "Olivia Johnson", "Address 46", "olivia@example.com", 7890123456, 4, 80000, "Site 24*7", "Super Admin", "EMP046", "10-12-1995", "Female"),
        EmployeeEntity(47, "William Smith", "Address 47", "william@example.com", 8901234567, 6, 90000, "People", "Trainee", "EMP047", "05-05-1985", "Male"),
        EmployeeEntity(48, "Sophia Martinez", "Address 48", "sophia@example.com", 9012345678, 2, 70000, "Creater App", "Employee", "EMP048", "30-09-1998", "Female"),
        EmployeeEntity(49, "Jackson Lee", "Address 49", "jackson@example.com", 2345678901, 5, 55000, "CRM", "Team Leader", "EMP049", "14-02-1991", "Male"),
        EmployeeEntity(50, "Emily Hernandez", "Address 50", "emily@example.com", 3456789012, 3, 65000, "Jambav", "Manager", "EMP050", "25-04-1993", "Female")
    )

//    val employees = listOf(
//        EmployeeEntity(1, "John Doe", "Address 1", "john@example.com", 1234567890, 5, 50000, "CRM", "Trainee", "EMP001", "01-01-1990", "Male"),
//        EmployeeEntity(2, "Jane Smith", "Address 2", "jane@example.com", 9876543210, 3, 60000, "Jambav", "Employee", "EMP002", "15-03-1992", "Female"),
//        EmployeeEntity(3, "Michael Johnson", "Address 3", "michael@example.com", 8765432109, 7, 70000, "Trainer Central", "Team Leader", "EMP003", "20-07-1988", "Male"),
//        EmployeeEntity(4, "Emily Brown", "Address 4", "emily@example.com", 7890123456, 4, 80000, "Site 24*7", "Manager", "EMP004", "10-12-1995", "Female"),
//        EmployeeEntity(5, "David Wilson", "Address 5", "david@example.com", 8901234567, 6, 90000, "People", "Admin", "EMP005", "05-05-1985", "Male"),
//        EmployeeEntity(6, "Emma Davis", "Address 6", "emma@example.com", 9012345678, 2, 70000, "Creater App", "Super Admin", "EMP006", "30-09-1998", "Female"),
//        EmployeeEntity(7, "James Anderson", "Address 7", "james@example.com", 2345678901, 5, 55000, "CRM", "Trainee", "EMP007", "14-02-1991", "Male"),
//        EmployeeEntity(8, "Olivia Martinez", "Address 8", "olivia@example.com", 3456789012, 3, 65000, "Jambav", "Employee", "EMP008", "25-04-1993", "Female"),
//        EmployeeEntity(9, "William Jackson", "Address 9", "william@example.com", 4567890123, 7, 75000, "Trainer Central", "Team Leader", "EMP009", "08-08-1987", "Male"),
//        EmployeeEntity(10, "Sophia Taylor", "Address 10", "sophia@example.com", 5678901234, 4, 85000, "Site 24*7", "Manager", "EMP010", "20-11-1994", "Female"),
//        EmployeeEntity(11, "Alexander White", "Address 11", "alex@example.com", 6789012345, 6, 95000, "People", "Admin", "EMP011", "15-06-1986", "Male"),
//        EmployeeEntity(12, "Isabella Harris", "Address 12", "isabella@example.com", 7890123456, 2, 80000, "Creater App", "Super Admin", "EMP012", "30-10-1997", "Female"),
//        EmployeeEntity(13, "Daniel Thomas", "Address 13", "daniel@example.com", 8901234567, 5, 60000, "CRM", "Trainee", "EMP013", "05-03-1990", "Male"),
//        EmployeeEntity(14, "Mia Clark", "Address 14", "mia@example.com", 9012345678, 3, 70000, "Jambav", "Employee", "EMP014", "18-05-1992", "Female"),
//        EmployeeEntity(15, "Liam Walker", "Address 15", "liam@example.com", 1234567890, 5, 50000, "Trainer Central", "Team Leader", "EMP015", "01-01-1990", "Male"),
//        EmployeeEntity(16, "Sophia Green", "Address 16", "sophia@example.com", 9876543210, 3, 60000, "Site 24*7", "Manager", "EMP016", "15-03-1992", "Female"),
//        EmployeeEntity(17, "Jacob Martinez", "Address 17", "jacob@example.com", 8765432109, 7, 70000, "People", "Admin", "EMP017", "20-07-1988", "Male"),
//        EmployeeEntity(18, "Emma Wilson", "Address 18", "emma@example.com", 7890123456, 4, 80000, "Creater App", "Super Admin", "EMP018", "10-12-1995", "Female"),
//        EmployeeEntity(19, "Noah Davis", "Address 19", "noah@example.com", 8901234567, 6, 90000, "CRM", "Trainee", "EMP019", "05-05-1985", "Male"),
//        EmployeeEntity(20, "Olivia Harris", "Address 20", "olivia@example.com", 9012345678, 2, 70000, "Jambav", "Employee", "EMP020", "30-09-1998", "Female"),
//        EmployeeEntity(21, "William Moore", "Address 21", "william@example.com", 2345678901, 5, 55000, "Trainer Central", "Team Leader", "EMP021", "14-02-1991", "Male"),
//        EmployeeEntity(22, "Ava Taylor", "Address 22", "ava@example.com", 3456789012, 3, 65000, "Site 24*7", "Manager", "EMP022", "25-04-1993", "Female"),
//        EmployeeEntity(23, "James Wilson", "Address 23", "james@example.com", 4567890123, 7, 75000, "People", "Admin", "EMP023", "08-08-1987", "Male"),
//        EmployeeEntity(24, "Charlotte Lee", "Address 24", "charlotte@example.com", 5678901234, 4, 85000, "Creater App", "Super Admin", "EMP024", "20-11-1994", "Female"),
//        EmployeeEntity(25, "Logan Harris", "Address 25", "logan@example.com", 6789012345, 6, 95000, "CRM", "Trainee", "EMP025", "15-06-1986", "Male"),
//        EmployeeEntity(26, "Amelia Taylor", "Address 26", "amelia@example.com", 7890123456, 2, 80000, "Jambav", "Employee", "EMP026", "30-10-1997", "Female"),
//        EmployeeEntity(27, "Benjamin Clark", "Address 27", "benjamin@example.com", 8901234567, 5, 60000, "Trainer Central", "Team Leader", "EMP027", "05-03-1990", "Male"),
//        EmployeeEntity(28, "Mia Anderson", "Address 28", "mia@example.com", 9012345678, 3, 70000, "Site 24*7", "Manager", "EMP028", "18-05-1992", "Female"),
//        EmployeeEntity(29, "Ethan Martin", "Address 29", "ethan@example.com", 1234567890, 5, 50000, "People", "Admin", "EMP029", "01-01-1990", "Male"),
//        EmployeeEntity(30, "Sophia Jackson", "Address 30", "sophia@example.com", 9876543210, 3, 60000, "Creater App", "Super Admin", "EMP030", "15-03-1992", "Female"),
//        EmployeeEntity(31, "Jackson Brown", "Address 31", "jackson@example.com", 8765432109, 7, 70000, "CRM", "Trainee", "EMP031", "20-07-1988", "Male"),
//        EmployeeEntity(32, "Emily Wilson", "Address 32", "emily@example.com", 7890123456, 4, 80000, "Jambav", "Employee", "EMP032", "10-12-1995", "Female"),
//        EmployeeEntity(33, "Michael Thomas", "Address 33", "michael@example.com", 8901234567, 6, 90000, "Trainer Central", "Team Leader", "EMP033", "05-05-1985", "Male"),
//        EmployeeEntity(34, "Olivia Garcia", "Address 34", "olivia@example.com", 9012345678, 2, 70000, "Site 24*7", "Manager", "EMP034", "30-09-1998", "Female"),
//        EmployeeEntity(35, "William Wilson", "Address 35", "william@example.com", 2345678901, 5, 55000, "People", "Admin", "EMP035", "14-02-1991", "Male"),
//        EmployeeEntity(36, "Emma Taylor", "Address 36", "emma@example.com", 3456789012, 3, 65000, "Creater App", "Super Admin", "EMP036", "25-04-1993", "Female"),
//        EmployeeEntity(37, "James Moore", "Address 37", "james@example.com", 4567890123, 7, 75000, "CRM", "Trainee", "EMP037", "08-08-1987", "Male"),
//        EmployeeEntity(38, "Charlotte White", "Address 38", "charlotte@example.com", 5678901234, 4, 85000, "Jambav", "Employee", "EMP038", "20-11-1994", "Female"),
//        EmployeeEntity(39, "Noah Harris", "Address 39", "noah@example.com", 6789012345, 6, 95000, "Trainer Central", "Team Leader", "EMP039", "15-06-1986", "Male"),
//        EmployeeEntity(40, "Ava Clark", "Address 40", "ava@example.com", 7890123456, 2, 80000, "Site 24*7", "Manager", "EMP040", "30-10-1997", "Female"),
//        EmployeeEntity(41, "William Davis", "Address 41", "william@example.com", 8901234567, 5, 60000, "People", "Admin", "EMP041", "05-03-1990", "Male"),
//        EmployeeEntity(42, "Sophia Hernandez", "Address 42", "sophia@example.com", 9012345678, 3, 70000, "Creater App", "Super Admin", "EMP042", "18-05-1992", "Female"),
//        EmployeeEntity(43, "Michael Brown", "Address 43", "michael@example.com", 1234567890, 5, 50000, "CRM", "Trainee", "EMP043", "01-01-1990", "Male"),
//        EmployeeEntity(44, "Emma Garcia", "Address 44", "emma@example.com", 9876543210, 3, 60000, "Jambav", "Employee", "EMP044", "15-03-1992", "Female"),
//        EmployeeEntity(45, "James Wilson", "Address 45", "james@example.com", 8765432109, 7, 70000, "Trainer Central", "Team Leader", "EMP045", "20-07-1988", "Male"),
//        EmployeeEntity(46, "Olivia Johnson", "Address 46", "olivia@example.com", 7890123456, 4, 80000, "Site 24*7", "Manager", "EMP046", "10-12-1995", "Female"),
//        EmployeeEntity(47, "William Smith", "Address 47", "william@example.com", 8901234567, 6, 90000, "People", "Admin", "EMP047", "05-05-1985", "Male"),
//        EmployeeEntity(48, "Sophia Martinez", "Address 48", "sophia@example.com", 9012345678, 2, 70000, "Creater App", "Super Admin", "EMP048", "30-09-1998", "Female"),
//        EmployeeEntity(49, "Jackson Lee", "Address 49", "jackson@example.com", 2345678901, 5, 55000, "CRM", "Trainee", "EMP049", "14-02-1991", "Male"),
//        EmployeeEntity(50, "Emily Hernandez", "Address 50", "emily@example.com", 3456789012, 3, 65000, "Jambav", "Employee", "EMP050", "25-04-1993", "Female")
//    )

//    val employeeList = listOf(
//        EmployeeEntity(
//            id = 1,
//            name = "John Smith",
//            address = "123 Main St",
//            mail_id = "john.smith@example.com",
//            mobile_no = 1234567890,
//            year_of_experience = 5,
//            salary = 60000,
//            department = "HR",
//            designation = "Manager",
//            employee_id = "EMP-001",
//            date_of_birth = "19-09-1989",
//            gender = "Male"
//        ),
//        EmployeeEntity(
//            id = 2,
//            name = "Alice Johnson",
//            address = "456 Elm St",
//            mail_id = "alice.johnson@example.com",
//            mobile_no = 9876543210,
//            year_of_experience = 7,
//            salary = 75000,
//            department = "IT",
//            designation = "Engineer",
//            employee_id = "EMP-002",
//            date_of_birth = "19-09-1989",
//            gender = "Female"
//        ),
//        EmployeeEntity(
//            id = 3,
//            name = "Michael Williams",
//            address = "789 Oak St",
//            mail_id = "michael.williams@example.com",
//            mobile_no = 5551234567,
//            year_of_experience = 3,
//            salary = 55000,
//            department = "Finance",
//            designation = "Analyst",
//            employee_id = "EMP-003",
//            date_of_birth = "19-09-1989",
//            gender = "Male"
//        ),
//        EmployeeEntity(
//            id = 4,
//            name = "Emily Brown",
//            address = "101 Pine St",
//            mail_id = "emily.brown@example.com",
//            mobile_no = 3339876543,
//            year_of_experience = 6,
//            salary = 68000,
//            department = "Sales",
//            designation = "Associate",
//            employee_id = "EMP-004",
//            date_of_birth = "19-09-1989",
//            gender = "Female"
//        ),
//        EmployeeEntity(
//            id = 5,
//            name = "Christopher Jones",
//            address = "321 Cedar St",
//            mail_id = "christopher.jones@example.com",
//            mobile_no = 2223334444,
//            year_of_experience = 8,
//            salary = 85000,
//            department = "Marketing",
//            designation = "Manager",
//            employee_id = "EMP-005",
//            date_of_birth = "19-09-1989",
//            gender = "Male"
//        ),
//        // Add more EmployeeEntity objects here with unique details
//        EmployeeEntity(
//            id = 6,
//            name = "Emma Davis",
//            address = "789 Maple St",
//            mail_id = "emma.davis@example.com",
//            mobile_no = 6667778888,
//            year_of_experience = 4,
//            salary = 62000,
//            department = "HR",
//            designation = "Trainee",
//            employee_id = "EMP-006",
//            date_of_birth = "19-09-1989",
//            gender = "Female"
//        ),
//        EmployeeEntity(
//            id = 7,
//            name = "Daniel Wilson",
//            address = "905 Oak St",
//            mail_id = "daniel.wilson@example.com",
//            mobile_no = 4445556666,
//            year_of_experience = 2,
//            salary = 52000,
//            department = "IT",
//            designation = "Engineer",
//            employee_id = "EMP-007",
//            date_of_birth = "19-09-1989",
//            gender = "Male"
//        ),
//        EmployeeEntity(
//            id = 8,
//            name = "Olivia Garcia",
//            address = "123 Pine St",
//            mail_id = "olivia.garcia@example.com",
//            mobile_no = 7778889999,
//            year_of_experience = 5,
//            salary = 70000,
//            department = "Finance",
//            designation = "Manager",
//            employee_id = "EMP-008",
//            date_of_birth = "19-09-1989",
//            gender = "Female"
//        ),
//        EmployeeEntity(
//            id = 9,
//            name = "David Martinez",
//            address = "456 Cedar St",
//            mail_id = "david.martinez@example.com",
//            mobile_no = 2221110000,
//            year_of_experience = 7,
//            salary = 80000,
//            department = "Sales",
//            designation = "Associate",
//            employee_id = "EMP-009",
//            date_of_birth = "19-09-1989",
//            gender = "Male"
//        ),
//        EmployeeEntity(
//            id = 10,
//            name = "Sophia Taylor",
//            address = "321 Elm St",
//            mail_id = "sophia.taylor@example.com",
//            mobile_no = 8889990000,
//            year_of_experience = 3,
//            salary = 59000,
//            department = "Marketing",
//            designation = "Analyst",
//            employee_id = "EMP-010",
//            date_of_birth = "19-09-1989",
//            gender = "Female"
//        ),
//        EmployeeEntity(
//            id = 11,
//            name = "James Rodriguez",
//            address = "101 Maple St",
//            mail_id = "james.rodriguez@example.com",
//            mobile_no = 7776665555,
//            year_of_experience = 6,
//            salary = 72000,
//            department = "HR",
//            designation = "Analyst",
//            employee_id = "EMP-011",
//            date_of_birth = "19-09-1989",
//            gender = "Male"
//        ),
//        EmployeeEntity(
//            id = 12,
//            name = "Amelia Hernandez",
//            address = "789 Oak St",
//            mail_id = "amelia.hernandez@example.com",
//            mobile_no = 6665554444,
//            year_of_experience = 2,
//            salary = 53000,
//            department = "IT",
//            designation = "Trainee",
//            employee_id = "EMP-012",
//            date_of_birth = "19-09-1989",
//            gender = "Female"
//        ),
//        EmployeeEntity(
//            id = 13,
//            name = "Logan Smith",
//            address = "456 Cedar St",
//            mail_id = "logan.smith@example.com",
//            mobile_no = 3334445555,
//            year_of_experience = 4,
//            salary = 64000,
//            department = "Finance",
//            designation = "Manager",
//            employee_id = "EMP-013",
//            date_of_birth = "19-09-1989",
//            gender = "Male"
//        ),
//        EmployeeEntity(
//            id = 14,
//            name = "Mia Johnson",
//            address = "321 Elm St",
//            mail_id = "mia.johnson@example.com",
//            mobile_no = 2223334444,
//            year_of_experience = 7,
//            salary = 78000,
//            department = "Sales",
//            designation = "Associate",
//            employee_id = "EMP-014",
//            date_of_birth = "19-09-1989",
//            gender = "Female"
//        ),
//        EmployeeEntity(
//            id = 15,
//            name = "Noah Williams",
//            address = "101 Maple St",
//            mail_id = "noah.williams@example.com",
//            mobile_no = 1112223333,
//            year_of_experience = 3,
//            salary = 57000,
//            department = "Marketing",
//            designation = "Analyst",
//            employee_id = "EMP-015",
//            date_of_birth = "19-09-1989",
//            gender = "Male"
//        ),
//        EmployeeEntity(
//            id = 16,
//            name = "Ava Brown",
//            address = "789 Oak St",
//            mail_id = "ava.brown@example.com",
//            mobile_no = 4445556666,
//            year_of_experience = 6,
//            salary = 71000,
//            department = "HR",
//            designation = "Analyst",
//            employee_id = "EMP-016",
//            date_of_birth = "19-09-1989",
//            gender = "Female"
//        ),
//        EmployeeEntity(
//            id = 17,
//            name = "Liam Jones",
//            address = "456 Cedar St",
//            mail_id = "liam.jones@example.com",
//            mobile_no = 7778889999,
//            year_of_experience = 2,
//            salary = 54000,
//            department = "IT",
//            designation = "Trainee",
//            employee_id = "EMP-017",
//            date_of_birth = "19-09-1989",
//            gender = "Male"
//        ),
//        EmployeeEntity(
//            id = 18,
//            name = "Emma Garcia",
//            address = "321 Elm St",
//            mail_id = "emma.garcia@example.com",
//            mobile_no = 8889990000,
//            year_of_experience = 5,
//            salary = 72000,
//            department = "Finance",
//            designation = "Manager",
//            employee_id = "EMP-018",
//            date_of_birth = "19-09-1989",
//            gender = "Female"
//        ),
//        EmployeeEntity(
//            id = 19,
//            name = "William Martinez",
//            address = "101 Maple St",
//            mail_id = "william.martinez@example.com",
//            mobile_no = 1112223333,
//            year_of_experience = 7,
//            salary = 83000,
//            department = "Sales",
//            designation = "Associate",
//            employee_id = "EMP-019",
//            date_of_birth = "19-09-1989",
//            gender = "Male"
//        ),
//        EmployeeEntity(
//            id = 20,
//            name = "Sophia Taylor",
//            address = "789 Oak St",
//            mail_id = "sophia.taylor@example.com",
//            mobile_no = 4445556666,
//            year_of_experience = 3,
//            salary = 60000,
//            department = "Marketing",
//            designation = "Analyst",
//            employee_id = "EMP-020",
//            date_of_birth = "19-09-1989",
//            gender = "Female"
//        ),
//        EmployeeEntity(
//            id = 21,
//            name = "Oliver Smith",
//            address = "456 Cedar St",
//            mail_id = "oliver.smith@example.com",
//            mobile_no = 7778889999,
//            year_of_experience = 6,
//            salary = 74000,
//            department = "HR",
//            designation = "Analyst",
//            employee_id = "EMP-021",
//            date_of_birth = "19-09-1989",
//            gender = "Male"
//        ),
//        EmployeeEntity(
//            id = 22,
//            name = "Isabella Johnson",
//            address = "321 Elm St",
//            mail_id = "isabella.johnson@example.com",
//            mobile_no = 8889990000,
//            year_of_experience = 2,
//            salary = 55000,
//            department = "IT",
//            designation = "Trainee",
//            employee_id = "EMP-022",
//            date_of_birth = "19-09-1989",
//            gender = "Female"
//        ),
//        EmployeeEntity(
//            id = 23,
//            name = "Liam Williams",
//            address = "101 Maple St",
//            mail_id = "liam.williams@example.com",
//            mobile_no = 1112223333,
//            year_of_experience = 5,
//            salary = 71000,
//            department = "Finance",
//            designation = "Manager",
//            employee_id = "EMP-023",
//            date_of_birth = "19-09-1989",
//            gender = "Male"
//        ),
//        EmployeeEntity(
//            id = 24,
//            name = "Ava Brown",
//            address = "789 Oak St",
//            mail_id = "ava.brown@example.com",
//            mobile_no = 4445556666,
//            year_of_experience = 7,
//            salary = 82000,
//            department = "Sales",
//            designation = "Associate",
//            employee_id = "EMP-024",
//            date_of_birth = "19-09-1989",
//            gender = "Female"
//        ),
//        EmployeeEntity(
//            id = 25,
//            name = "Noah Jones",
//            address = "456 Cedar St",
//            mail_id = "noah.jones@example.com",
//            mobile_no = 7778889999,
//            year_of_experience = 3,
//            salary = 61000,
//            department = "Marketing",
//            designation = "Analyst",
//            employee_id = "EMP-025",
//            date_of_birth = "19-09-1989",
//            gender = "Male"
//        ),
//        EmployeeEntity(
//            id = 26,
//            name = "Emma Garcia",
//            address = "321 Elm St",
//            mail_id = "emma.garcia@example.com",
//            mobile_no = 8889990000,
//            year_of_experience = 6,
//            salary = 75000,
//            department = "HR",
//            designation = "Manager",
//            employee_id = "EMP-026",
//            date_of_birth = "19-09-1989",
//            gender = "Female"
//        ),
//        EmployeeEntity(
//            id = 27,
//            name = "William Martinez",
//            address = "101 Maple St",
//            mail_id = "william.martinez@example.com",
//            mobile_no = 1112223333,
//            year_of_experience = 2,
//            salary = 56000,
//            department = "IT",
//            designation = "Trainee",
//            employee_id = "EMP-027",
//            date_of_birth = "19-09-1989",
//            gender = "Male"
//        ),
//        EmployeeEntity(
//            id = 28,
//            name = "Sophia Taylor",
//            address = "789 Oak St",
//            mail_id = "sophia.taylor@example.com",
//            mobile_no = 4445556666,
//            year_of_experience = 5,
//            salary = 73000,
//            department = "Finance",
//            designation = "Manager",
//            employee_id = "EMP-028",
//            date_of_birth = "19-09-1989",
//            gender = "Female"
//        ),
//        EmployeeEntity(
//            id = 29,
//            name = "Oliver Smith",
//            address = "456 Cedar St",
//            mail_id = "oliver.smith@example.com",
//            mobile_no = 7778889999,
//            year_of_experience = 7,
//            salary = 84000,
//            department = "Sales",
//            designation = "Associate",
//            employee_id = "EMP-029",
//            date_of_birth = "19-09-1989",
//            gender = "Male"
//        ),
//        EmployeeEntity(
//            id = 30,
//            name = "Isabella Johnson",
//            address = "321 Elm St",
//            mail_id = "isabella.johnson@example.com",
//            mobile_no = 8889990000,
//            year_of_experience = 3,
//            salary = 62000,
//            department = "Marketing",
//            designation = "Analyst",
//            employee_id = "EMP-030",
//            date_of_birth = "19-09-1989",
//            gender = "Female"
//        ),
//        EmployeeEntity(
//            id = 31,
//            name = "Liam Williams",
//            address = "101 Maple St",
//            mail_id = "liam.williams@example.com",
//            mobile_no = 1112223333,
//            year_of_experience = 6,
//            salary = 76000,
//            department = "HR",
//            designation = "Analyst",
//            employee_id = "EMP-031",
//            date_of_birth = "19-09-1989",
//            gender = "Male"
//        ),
//        EmployeeEntity(
//            id = 32,
//            name = "Ava Brown",
//            address = "789 Oak St",
//            mail_id = "ava.brown@example.com",
//            mobile_no = 4445556666,
//            year_of_experience = 2,
//            salary = 57000,
//            department = "IT",
//            designation = "Trainee",
//            employee_id = "EMP-032",
//            date_of_birth = "19-09-1989",
//            gender = "Female"
//        ),
//        EmployeeEntity(
//            id = 33,
//            name = "Noah Jones",
//            address = "456 Cedar St",
//            mail_id = "noah.jones@example.com",
//            mobile_no = 7778889999,
//            year_of_experience = 5,
//            salary = 74000,
//            department = "Finance",
//            designation = "Manager",
//            employee_id = "EMP-033",
//            date_of_birth = "19-09-1989",
//            gender = "Male"
//        ),
//        EmployeeEntity(
//            id = 34,
//            name = "Emma Garcia",
//            address = "321 Elm St",
//            mail_id = "emma.garcia@example.com",
//            mobile_no = 8889990000,
//            year_of_experience = 7,
//            salary = 85000,
//            department = "Sales",
//            designation = "Associate",
//            employee_id = "EMP-034",
//            date_of_birth = "19-09-1989",
//            gender = "Female"
//        ),
//        EmployeeEntity(
//            id = 35,
//            name = "William Martinez",
//            address = "101 Maple St",
//            mail_id = "william.martinez@example.com",
//            mobile_no = 1112223333,
//            year_of_experience = 3,
//            salary = 63000,
//            department = "Marketing",
//            designation = "Analyst",
//            employee_id = "EMP-035",
//            date_of_birth = "19-09-1989",
//            gender = "Male"
//        ),
//        EmployeeEntity(
//            id = 36,
//            name = "Sophia Taylor",
//            address = "789 Oak St",
//            mail_id = "sophia.taylor@example.com",
//            mobile_no = 4445556666,
//            year_of_experience = 6,
//            salary = 77000,
//            department = "HR",
//            designation = "Manager",
//            employee_id = "EMP-036",
//            date_of_birth = "19-09-1989",
//            gender = "Female"
//        ),
//        EmployeeEntity(
//            id = 37,
//            name = "Oliver Smith",
//            address = "456 Cedar St",
//            mail_id = "oliver.smith@example.com",
//            mobile_no = 7778889999,
//            year_of_experience = 2,
//            salary = 58000,
//            department = "IT",
//            designation = "Trainee",
//            employee_id = "EMP-037",
//            date_of_birth = "19-09-1989",
//            gender = "Male"
//        ),
//        EmployeeEntity(
//            id = 38,
//            name = "Isabella Johnson",
//            address = "321 Elm St",
//            mail_id = "isabella.johnson@example.com",
//            mobile_no = 8889990000,
//            year_of_experience = 5,
//            salary = 75000,
//            department = "Finance",
//            designation = "Manager",
//            employee_id = "EMP-038",
//            date_of_birth = "19-09-1989",
//            gender = "Female"
//        ),
//        EmployeeEntity(
//            id = 39,
//            name = "Liam Williams",
//            address = "101 Maple St",
//            mail_id = "liam.williams@example.com",
//            mobile_no = 1112223333,
//            year_of_experience = 7,
//            salary = 86000,
//            department = "Sales",
//            designation = "Associate",
//            employee_id = "EMP-039",
//            date_of_birth = "19-09-1989",
//            gender = "Male"
//        ),
//        EmployeeEntity(
//            id = 40,
//            name = "Ava Brown",
//            address = "789 Oak St",
//            mail_id = "ava.brown@example.com",
//            mobile_no = 4445556666,
//            year_of_experience = 3,
//            salary = 64000,
//            department = "Marketing",
//            designation = "Analyst",
//            employee_id = "EMP-040",
//            date_of_birth = "19-09-1989",
//            gender = "Female"
//        ),
//        EmployeeEntity(
//            id = 41,
//            name = "Noah Jones",
//            address = "456 Cedar St",
//            mail_id = "noah.jones@example.com",
//            mobile_no = 7778889999,
//            year_of_experience = 6,
//            salary = 78000,
//            department = "HR",
//            designation = "Analyst",
//            employee_id = "EMP-041",
//            date_of_birth = "19-09-1989",
//            gender = "Male"
//        ),
//        EmployeeEntity(
//            id = 42,
//            name = "Emma Garcia",
//            address = "321 Elm St",
//            mail_id = "emma.garcia@example.com",
//            mobile_no = 8889990000,
//            year_of_experience = 2,
//            salary = 59000,
//            department = "IT",
//            designation = "Trainee",
//            employee_id = "EMP-042",
//            date_of_birth = "19-09-1989",
//            gender = "Female"
//        ),
//        EmployeeEntity(
//            id = 43,
//            name = "William Martinez",
//            address = "101 Maple St",
//            mail_id = "william.martinez@example.com",
//            mobile_no = 1112223333,
//            year_of_experience = 5,
//            salary = 76000,
//            department = "Finance",
//            designation = "Manager",
//            employee_id = "EMP-043",
//            date_of_birth = "19-09-1989",
//            gender = "Male"
//        ),
//        EmployeeEntity(
//            id = 44,
//            name = "Sophia Taylor",
//            address = "789 Oak St",
//            mail_id = "sophia.taylor@example.com",
//            mobile_no = 4445556666,
//            year_of_experience = 7,
//            salary = 87000,
//            department = "Sales",
//            designation = "Associate",
//            employee_id = "EMP-044",
//            date_of_birth = "19-09-1989",
//            gender = "Female"
//        ),
//        EmployeeEntity(
//            id = 45,
//            name = "Oliver Smith",
//            address = "456 Cedar St",
//            mail_id = "oliver.smith@example.com",
//            mobile_no = 7778889999,
//            year_of_experience = 3,
//            salary = 65000,
//            department = "Marketing",
//            designation = "Analyst",
//            employee_id = "EMP-045",
//            date_of_birth = "19-09-1989",
//            gender = "Male"
//        ),
//        EmployeeEntity(
//            id = 46,
//            name = "Isabella Johnson",
//            address = "321 Elm St",
//            mail_id = "isabella.johnson@example.com",
//            mobile_no = 8889990000,
//            year_of_experience = 6,
//            salary = 78000,
//            department = "HR",
//            designation = "Manager",
//            employee_id = "EMP-046",
//            date_of_birth = "19-09-1989",
//            gender = "Female"
//        ),
//        EmployeeEntity(
//            id = 47,
//            name = "Liam Williams",
//            address = "101 Maple St",
//            mail_id = "liam.williams@example.com",
//            mobile_no = 1112223333,
//            year_of_experience = 2,
//            salary = 60000,
//            department = "IT",
//            designation = "Trainee",
//            employee_id = "EMP-047",
//            date_of_birth = "19-09-1989",
//            gender = "Male"
//        ),
//        EmployeeEntity(
//            id = 48,
//            name = "Ava Brown",
//            address = "789 Oak St",
//            mail_id = "ava.brown@example.com",
//            mobile_no = 4445556666,
//            year_of_experience = 5,
//            salary = 79000,
//            department = "Finance",
//            designation = "Manager",
//            employee_id = "EMP-048",
//            date_of_birth = "19-09-1989",
//            gender = "Female"
//        ),
//        EmployeeEntity(
//            id = 49,
//            name = "Noah Jones",
//            address = "456 Cedar St",
//            mail_id = "noah.jones@example.com",
//            mobile_no = 7778889999,
//            year_of_experience = 7,
//            salary = 88000,
//            department = "Sales",
//            designation = "Associate",
//            employee_id = "EMP-049",
//            date_of_birth = "19-09-1989",
//            gender = "Male"
//        ),
//        EmployeeEntity(
//            id = 50,
//            name = "Emma Garcia",
//            address = "321 Elm St",
//            mail_id = "emma.garcia@example.com",
//            mobile_no = 8889990000,
//            year_of_experience = 3,
//            salary = 66000,
//            department = "Marketing",
//            designation = "Analyst",
//            employee_id = "EMP-050",
//            date_of_birth = "19-09-1989",
//            gender = "Female"
//        )
//    )

    suspend fun addEmployeeDetails(employee: EmployeeEntity) {

//        for(i in 0 until 50){
//
//            empDetailManipulate.addEmployeeDetail(employees[i])
//        }
        empDetailManipulate.addEmployeeDetail(employee)
    }


    suspend fun getAllEmployeeDetails() {}

    suspend fun getEmployeeFromId(id: Long) {}

    suspend fun updateEmployeeDetails(employee: EmployeeEntity) {

        empDetailManipulate.updateEmployeeDetail(employee)
    }

    suspend fun deleteEmployeeDetailById(id: Long) {

        empDetailManipulate.deleteEmployeeDetailById(id)
    }

    fun setProfileImage(profileImage: ImageView, id: Long) {
        profileImage.apply {

            when (id % 5) {
                1L -> {
                    setImageResource(R.drawable.people2)
                }

                2L -> {
                    setImageResource(R.drawable.people7)
                }

                3L -> {
                    setImageResource(R.drawable.people4)
                }

                4L -> {
                    setImageResource(R.drawable.people8)
                }

                else -> {
                    this.setImageResource(R.drawable.people6)
                }
            }
        }
    }
}