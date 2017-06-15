package dao;

import static utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import beans.Department;
import exception.SQLRuntimeException;

public class DepartmentDao {

	public List<Department> getDepartment(Connection connection){

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM departments";

			ps = connection.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			List<Department> departments = new DepartmentDao().toDepartmentList(rs);

			return departments;

		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<Department> toDepartmentList(ResultSet rs) throws SQLException {

		List<Department> ret = new ArrayList<Department>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				Timestamp createdAt = rs.getTimestamp("created_at");
				Timestamp updatedAt = rs.getTimestamp("updated_at");

				Department department = new Department();
				department.setId(id);
				department.setName(name);
				department.setCreatedAt(createdAt);
				department.setUpdatedAt(updatedAt);

				ret.add(department);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	public Department getUserDepartment(Connection connection, int departmentId) {PreparedStatement ps = null;
	try {
		String sql = "SELECT * FROM departments WHERE id = ? ";

		ps = connection.prepareStatement(sql);
		ps.setInt(1, departmentId);

		ResultSet rs = ps.executeQuery();
		List<Department> departments = new DepartmentDao().toDepartmentList(rs);
		Department department = departments.get(0);

		return department;

	} catch (SQLException e) {
		throw new SQLRuntimeException(e);
	} finally {
		close(ps);
	}
	}

}
