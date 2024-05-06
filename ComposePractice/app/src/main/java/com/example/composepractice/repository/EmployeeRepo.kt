package com.example.composepractice.repository

import com.example.composepractice.EmployeeEntity
import com.example.composepractice.R
import com.example.composepractice.databaseConnection.EmpDetailManipulate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class EmployeeRepo @Inject constructor(private var empDetailManipulate: EmpDetailManipulate) {

//    var employees: List<EmployeeEntity>
//    init {
//
//        employees = listOf(
//            EmployeeEntity(
//                1,
//                "John Doe",
//                "Address 1",
//                "john@example.com",
//                1234567890,
//                5,
//                50000,
//                "CRM",
//                "Trainee",
//                "EMP001",
//                "01-01-1990",
//                "Male"
//            ),
//            EmployeeEntity(
//                2,
//                "Jane Smith",
//                "Address 2",
//                "jane@example.com",
//                9876543210,
//                3,
//                60000,
//                "Jambav",
//                "Employee",
//                "EMP002",
//                "15-03-1992",
//                "Female"
//            ),
//            EmployeeEntity(
//                3,
//                "Michael Johnson",
//                "Address 3",
//                "michael@example.com",
//                8765432109,
//                7,
//                70000,
//                "Trainer Central",
//                "Team Leader",
//                "EMP003",
//                "20-07-1988",
//                "Male"
//            ),
//            EmployeeEntity(
//                4,
//                "Emily Brown",
//                "Address 4",
//                "emily@example.com",
//                7890123456,
//                4,
//                80000,
//                "Site 24*7",
//                "Manager",
//                "EMP004",
//                "10-12-1995",
//                "Female"
//            ),
//            EmployeeEntity(
//                5,
//                "David Wilson",
//                "Address 5",
//                "david@example.com",
//                8901234567,
//                6,
//                90000,
//                "People",
//                "Admin",
//                "EMP005",
//                "05-05-1985",
//                "Male"
//            ),
//            EmployeeEntity(
//                6,
//                "Emma Davis",
//                "Address 6",
//                "emma@example.com",
//                9012345678,
//                2,
//                70000,
//                "Creater App",
//                "Super Admin",
//                "EMP006",
//                "30-09-1998",
//                "Female"
//            ),
//            EmployeeEntity(
//                7,
//                "James Anderson",
//                "Address 7",
//                "james@example.com",
//                2345678901,
//                5,
//                55000,
//                "CRM",
//                "Employee",
//                "EMP007",
//                "14-02-1991",
//                "Male"
//            ),
//            EmployeeEntity(
//                8,
//                "Olivia Martinez",
//                "Address 8",
//                "olivia@example.com",
//                3456789012,
//                3,
//                65000,
//                "Jambav",
//                "Trainee",
//                "EMP008",
//                "25-04-1993",
//                "Female"
//            ),
//            EmployeeEntity(
//                9,
//                "William Jackson",
//                "Address 9",
//                "william@example.com",
//                4567890123,
//                7,
//                75000,
//                "Trainer Central",
//                "Admin",
//                "EMP009",
//                "08-08-1987",
//                "Male"
//            ),
//            EmployeeEntity(
//                10,
//                "Sophia Taylor",
//                "Address 10",
//                "sophia@example.com",
//                5678901234,
//                4,
//                85000,
//                "Site 24*7",
//                "Super Admin",
//                "EMP010",
//                "20-11-1994",
//                "Female"
//            ),
//            EmployeeEntity(
//                11,
//                "Alexander White",
//                "Address 11",
//                "alex@example.com",
//                6789012345,
//                6,
//                95000,
//                "People",
//                "Trainee",
//                "EMP011",
//                "15-06-1986",
//                "Male"
//            ),
//            EmployeeEntity(
//                12,
//                "Isabella Harris",
//                "Address 12",
//                "isabella@example.com",
//                7890123456,
//                2,
//                80000,
//                "Creater App",
//                "Employee",
//                "EMP012",
//                "30-10-1997",
//                "Female"
//            ),
//            EmployeeEntity(
//                13,
//                "Daniel Thomas",
//                "Address 13",
//                "daniel@example.com",
//                8901234567,
//                5,
//                60000,
//                "CRM",
//                "Team Leader",
//                "EMP013",
//                "05-03-1990",
//                "Male"
//            ),
//            EmployeeEntity(
//                14,
//                "Mia Clark",
//                "Address 14",
//                "mia@example.com",
//                9012345678,
//                3,
//                70000,
//                "Jambav",
//                "Manager",
//                "EMP014",
//                "18-05-1992",
//                "Female"
//            ),
//            EmployeeEntity(
//                15,
//                "Liam Walker",
//                "Address 15",
//                "liam@example.com",
//                1234567890,
//                5,
//                50000,
//                "Trainer Central",
//                "Admin",
//                "EMP015",
//                "01-01-1990",
//                "Male"
//            ),
//            EmployeeEntity(
//                16,
//                "Sophia Green",
//                "Address 16",
//                "sophia@example.com",
//                9876543210,
//                3,
//                60000,
//                "Site 24*7",
//                "Super Admin",
//                "EMP016",
//                "15-03-1992",
//                "Female"
//            ),
//            EmployeeEntity(
//                17,
//                "Jacob Martinez",
//                "Address 17",
//                "jacob@example.com",
//                8765432109,
//                7,
//                70000,
//                "People",
//                "Trainee",
//                "EMP017",
//                "20-07-1988",
//                "Male"
//            ),
//            EmployeeEntity(
//                18,
//                "Emma Wilson",
//                "Address 18",
//                "emma@example.com",
//                7890123456,
//                4,
//                80000,
//                "Creater App",
//                "Employee",
//                "EMP018",
//                "10-12-1995",
//                "Female"
//            ),
//            EmployeeEntity(
//                19,
//                "Noah Davis",
//                "Address 19",
//                "noah@example.com",
//                8901234567,
//                6,
//                90000,
//                "CRM",
//                "Team Leader",
//                "EMP019",
//                "05-05-1985",
//                "Male"
//            ),
//            EmployeeEntity(
//                20,
//                "Olivia Harris",
//                "Address 20",
//                "olivia@example.com",
//                9012345678,
//                2,
//                70000,
//                "Jambav",
//                "Manager",
//                "EMP020",
//                "30-09-1998",
//                "Female"
//            ),
//            EmployeeEntity(
//                21,
//                "William Moore",
//                "Address 21",
//                "william@example.com",
//                2345678901,
//                5,
//                55000,
//                "Trainer Central",
//                "Admin",
//                "EMP021",
//                "14-02-1991",
//                "Male"
//            ),
//            EmployeeEntity(
//                22,
//                "Ava Taylor",
//                "Address 22",
//                "ava@example.com",
//                3456789012,
//                3,
//                65000,
//                "Site 24*7",
//                "Super Admin",
//                "EMP022",
//                "25-04-1993",
//                "Female"
//            ),
//            EmployeeEntity(
//                23,
//                "James Wilson",
//                "Address 23",
//                "james@example.com",
//                4567890123,
//                7,
//                75000,
//                "People",
//                "Trainee",
//                "EMP023",
//                "08-08-1987",
//                "Male"
//            ),
//            EmployeeEntity(
//                24,
//                "Charlotte Lee",
//                "Address 24",
//                "charlotte@example.com",
//                5678901234,
//                4,
//                85000,
//                "Creater App",
//                "Manager",
//                "EMP024",
//                "20-11-1994",
//                "Female"
//            ),
//            EmployeeEntity(
//                25,
//                "Logan Harris",
//                "Address 25",
//                "logan@example.com",
//                6789012345,
//                6,
//                95000,
//                "CRM",
//                "Employee",
//                "EMP025",
//                "15-06-1986",
//                "Male"
//            ),
//            EmployeeEntity(
//                26,
//                "Amelia Taylor",
//                "Address 26",
//                "amelia@example.com",
//                7890123456,
//                2,
//                80000,
//                "Jambav",
//                "Team Leader",
//                "EMP026",
//                "30-10-1997",
//                "Female"
//            ),
//            EmployeeEntity(
//                27,
//                "Benjamin Clark",
//                "Address 27",
//                "benjamin@example.com",
//                8901234567,
//                5,
//                60000,
//                "Trainer Central",
//                "Admin",
//                "EMP027",
//                "05-03-1990",
//                "Male"
//            ),
//            EmployeeEntity(
//                28,
//                "Mia Anderson",
//                "Address 28",
//                "mia@example.com",
//                9012345678,
//                3,
//                70000,
//                "Site 24*7",
//                "Super Admin",
//                "EMP028",
//                "18-05-1992",
//                "Female"
//            ),
//            EmployeeEntity(
//                29,
//                "Ethan Martin",
//                "Address 29",
//                "ethan@example.com",
//                1234567890,
//                5,
//                50000,
//                "People",
//                "Trainee",
//                "EMP029",
//                "01-01-1990",
//                "Male"
//            ),
//            EmployeeEntity(
//                30,
//                "Sophia Jackson",
//                "Address 30",
//                "sophia@example.com",
//                9876543210,
//                3,
//                60000,
//                "Creater App",
//                "Employee",
//                "EMP030",
//                "15-03-1992",
//                "Female"
//            ),
//            EmployeeEntity(
//                31,
//                "Jackson Brown",
//                "Address 31",
//                "jackson@example.com",
//                8765432109,
//                7,
//                70000,
//                "CRM",
//                "Team Leader",
//                "EMP031",
//                "20-07-1988",
//                "Male"
//            ),
//            EmployeeEntity(
//                32,
//                "Emily Wilson",
//                "Address 32",
//                "emily@example.com",
//                7890123456,
//                4,
//                80000,
//                "Jambav",
//                "Manager",
//                "EMP032",
//                "10-12-1995",
//                "Female"
//            ),
//            EmployeeEntity(
//                33,
//                "Michael Thomas",
//                "Address 33",
//                "michael@example.com",
//                8901234567,
//                6,
//                90000,
//                "Trainer Central",
//                "Admin",
//                "EMP033",
//                "05-05-1985",
//                "Male"
//            ),
//            EmployeeEntity(
//                34,
//                "Olivia Garcia",
//                "Address 34",
//                "olivia@example.com",
//                9012345678,
//                2,
//                70000,
//                "Site 24*7",
//                "Super Admin",
//                "EMP034",
//                "30-09-1998",
//                "Female"
//            ),
//            EmployeeEntity(
//                35,
//                "William Wilson",
//                "Address 35",
//                "william@example.com",
//                2345678901,
//                5,
//                55000,
//                "People",
//                "Trainee",
//                "EMP035",
//                "14-02-1991",
//                "Male"
//            ),
//            EmployeeEntity(
//                36,
//                "Emma Taylor",
//                "Address 36",
//                "emma@example.com",
//                3456789012,
//                3,
//                65000,
//                "Creater App",
//                "Employee",
//                "EMP036",
//                "25-04-1993",
//                "Female"
//            ),
//            EmployeeEntity(
//                37,
//                "James Moore",
//                "Address 37",
//                "james@example.com",
//                4567890123,
//                7,
//                75000,
//                "CRM",
//                "Team Leader",
//                "EMP037",
//                "08-08-1987",
//                "Male"
//            ),
//            EmployeeEntity(
//                38,
//                "Charlotte White",
//                "Address 38",
//                "charlotte@example.com",
//                5678901234,
//                4,
//                85000,
//                "Jambav",
//                "Manager",
//                "EMP038",
//                "20-11-1994",
//                "Female"
//            ),
//            EmployeeEntity(
//                39,
//                "Noah Harris",
//                "Address 39",
//                "noah@example.com",
//                6789012345,
//                6,
//                95000,
//                "Trainer Central",
//                "Admin",
//                "EMP039",
//                "15-06-1986",
//                "Male"
//            ),
//            EmployeeEntity(
//                40,
//                "Ava Clark",
//                "Address 40",
//                "ava@example.com",
//                7890123456,
//                2,
//                80000,
//                "Site 24*7",
//                "Super Admin",
//                "EMP040",
//                "30-10-1997",
//                "Female"
//            ),
//            EmployeeEntity(
//                41,
//                "William Davis",
//                "Address 41",
//                "william@example.com",
//                8901234567,
//                5,
//                60000,
//                "People",
//                "Trainee",
//                "EMP041",
//                "05-03-1990",
//                "Male"
//            ),
//            EmployeeEntity(
//                42,
//                "Sophia Hernandez",
//                "Address 42",
//                "sophia@example.com",
//                9012345678,
//                3,
//                70000,
//                "Creater App",
//                "Employee",
//                "EMP042",
//                "18-05-1992",
//                "Female"
//            ),
//            EmployeeEntity(
//                43,
//                "Michael Brown",
//                "Address 43",
//                "michael@example.com",
//                1234567890,
//                5,
//                50000,
//                "CRM",
//                "Team Leader",
//                "EMP043",
//                "01-01-1990",
//                "Male"
//            ),
//            EmployeeEntity(
//                44,
//                "Emma Garcia",
//                "Address 44",
//                "emma@example.com",
//                9876543210,
//                3,
//                60000,
//                "Jambav",
//                "Manager",
//                "EMP044",
//                "15-03-1992",
//                "Female"
//            ),
//            EmployeeEntity(
//                45,
//                "James Wilson",
//                "Address 45",
//                "james@example.com",
//                8765432109,
//                7,
//                70000,
//                "Trainer Central",
//                "Admin",
//                "EMP045",
//                "20-07-1988",
//                "Male"
//            ),
//            EmployeeEntity(
//                46,
//                "Olivia Johnson",
//                "Address 46",
//                "olivia@example.com",
//                7890123456,
//                4,
//                80000,
//                "Site 24*7",
//                "Super Admin",
//                "EMP046",
//                "10-12-1995",
//                "Female"
//            ),
//            EmployeeEntity(
//                47,
//                "William Smith",
//                "Address 47",
//                "william@example.com",
//                8901234567,
//                6,
//                90000,
//                "People",
//                "Trainee",
//                "EMP047",
//                "05-05-1985",
//                "Male"
//            ),
//            EmployeeEntity(
//                48,
//                "Sophia Martinez",
//                "Address 48",
//                "sophia@example.com",
//                9012345678,
//                2,
//                70000,
//                "Creater App",
//                "Employee",
//                "EMP048",
//                "30-09-1998",
//                "Female"
//            ),
//            EmployeeEntity(
//                49,
//                "Jackson Lee",
//                "Address 49",
//                "jackson@example.com",
//                2345678901,
//                5,
//                55000,
//                "CRM",
//                "Team Leader",
//                "EMP049",
//                "14-02-1991",
//                "Male"
//            ),
//            EmployeeEntity(
//                50,
//                "Emily Hernandez",
//                "Address 50",
//                "emily@example.com",
//                3456789012,
//                3,
//                65000,
//                "Jambav",
//                "Manager",
//                "EMP050",
//                "25-04-1993",
//                "Female"
//            )
//        )
//        for(i in employees.indices){
//            CoroutineScope(Dispatchers.Default).launch {
//                addEmployeeDetails(employees[i])
//            }
//        }
//    }

    suspend fun addEmployeeDetails(employee: EmployeeEntity) {

        empDetailManipulate.addEmployeeDetail(employee)
    }


    fun getAllEmployeeDetails(
        searchingName: String,
        designationValue: String
    ): Flow<List<EmployeeEntity>>? {

        return empDetailManipulate.getAllEmployeeDetails(searchingName,designationValue)
    }


    suspend fun deleteEmployeeDetailById(id: Long) {

        empDetailManipulate.deleteEmployeeDetailById(id)
    }

    suspend fun getEmployeeDetailsByDesignation(search: String, searchingName: String): Flow<List<EmployeeEntity>> {
        return empDetailManipulate.getAllEmployeeDetailsByDesignation(search,searchingName)
    }

    suspend fun getEmployeeDetailById(id: Long): EmployeeEntity {

        return empDetailManipulate.getEmployeeDetailById(id)
    }

    fun getImageId(id: Long): Int {

      return when (id % 5) {
          1L -> {
              (R.drawable.people2)
          }

          2L -> {
              (R.drawable.people7)
          }

          3L -> {
              (R.drawable.people4)
          }

          4L -> {
              (R.drawable.people8)
          }

          else -> {
              (R.drawable.people6)
          }
      }
    }
}