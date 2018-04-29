package com.emmes.spring.mvc.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.emmes.spring.mvc.dao.EmployeeDao;
import com.emmes.spring.mvc.domain.Employee;

public class EmployeeDAOImpl implements EmployeeDao
{

	@Autowired
    private JdbcTemplate jdbcTemplate;
    // JdbcTemplate setter
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Saving a new Employee
    public void saveEmployee(Employee employee)
    {
        String sql = "insert into Employee values(?,?,?,?)";

        jdbcTemplate.update(sql, new Object[]
        { employee.getId(),employee.getName() , employee.getDept(),employee.getAge()});
    }

    // Getting a particular Employee
    public Employee getEmployeeById(int id)
    {
    	 String sql = "select * from Employee where id=?";
         Employee employee = (Employee) jdbcTemplate.queryForObject(sql, new Object[]
         { id }, new RowMapper()
         {
             public Employee mapRow(ResultSet rs, int rowNum) throws SQLException
             {
                 Employee employee = new Employee();
                 employee.setId(rs.getInt(1));
                 employee.setName(rs.getString(2));
                 employee.setDept(rs.getString(3));
                 employee.setAge(rs.getInt(4));
                 return employee;
             }
         });
         return employee;  
    }

    // Getting all the Employees
    public List<Employee> getAllEmployees()
    {
        String sql = "select * from Employee";
        List<Employee> employeeList = jdbcTemplate.query(sql, new ResultSetExtractor<List<Employee>>()
        {
            public List<Employee> extractData(ResultSet rs) throws SQLException, DataAccessException
            {
            	 List<Employee> list=new ArrayList<Employee>();
                while (rs.next())
                {
                	
                    Employee employee = new Employee();
                    employee.setId(rs.getInt(1));
                    employee.setName(rs.getString(2));
                    employee.setDept(rs.getString(3));
                    employee.setAge(rs.getInt(4));
                    list.add(employee);
                }
                return list;
            }

        });
        return employeeList;
    }

    // Updating a particular Employee
    public void updateEmployee(Employee employee)
    {
        String sql = "update Employee set age =?, dept=?,name=? where id=?";
        jdbcTemplate.update(sql, new Object[]
        { employee.getAge(), employee.getDept(), employee.getName(), employee.getId() });
    }

    // Deletion of a particular Employee
    public void deleteEmployee(int id)
    {
        String sql = "delete from employee where id=?";
        jdbcTemplate.update(sql, new Object[]{id});
        
    }
}